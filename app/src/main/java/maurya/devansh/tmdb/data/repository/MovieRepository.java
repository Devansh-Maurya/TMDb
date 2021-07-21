package maurya.devansh.tmdb.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LoadType;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.local.db.DatabaseService;
import maurya.devansh.tmdb.data.local.db.dao.MovieDao;
import maurya.devansh.tmdb.data.local.db.dao.MovieRemoteKeyDao;
import maurya.devansh.tmdb.data.model.BookmarkedMovie;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MovieRemoteKey;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.model.NowPlayingMovie;
import maurya.devansh.tmdb.data.model.TrendingMovie;
import maurya.devansh.tmdb.data.remote.NetworkService;
import maurya.devansh.tmdb.utils.common.MoviesListType;
import retrofit2.HttpException;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieRepository {

    private final NetworkService networkService;
    public final DatabaseService databaseService;

    public final MovieDao movieDao;
    public final MovieRemoteKeyDao remoteKeyDao;

    @Inject
    MovieRepository(
        NetworkService networkService,
        DatabaseService databaseService,
        CompositeDisposable compositeDisposable
    ) {
        this.networkService = networkService;
        this.databaseService = databaseService;

        movieDao = databaseService.movieDao();
        remoteKeyDao = databaseService.movieRemoteKeyDao();
    }

    public Single<MoviesList> getMoviesListFromNetwork(@MoviesListType int type, int page) {
        switch (type) {
            case MoviesList.TYPE_TRENDING:
                return getTrendingMoviesFromNetwork(page);
            case MoviesList.TYPE_NOW_PLAYING:
                return getNowPlayingMoviesFromNetwork(page);
            case MoviesList.TYPE_SEARCH_RESULT:
                return getTrendingMoviesFromNetwork(page);
            default:
                throw new IllegalArgumentException("Invalid Single movie list type: " + type);
        }
    }

    public LiveData<PagingData<Movie>> getMoviesList(@MoviesListType int type) {
        PagingConfig config = new PagingConfig(MoviesList.PAGE_SIZE, MoviesList.PAGE_SIZE, false);
        MoviesRemoteMediator remoteMediator = new MoviesRemoteMediator(this, type);
        return PagingLiveData.getLiveData(
            new Pager<>(config, MoviesList.STARTING_PAGE, remoteMediator, () -> movieDao.getMovies(type))
        );
    }

    private Single<MoviesList> getTrendingMoviesFromNetwork(int page) {
        return networkService.getTrendingMovies(page);
    }

    private Single<MoviesList> getNowPlayingMoviesFromNetwork(int page) {
        return networkService.getNowPlayingMovies(page, "US");
    }

    public PagingSource<Integer, Movie> getBookmarkedMovies() {
        return databaseService.movieDao().getBookmarkedMovies();
    }

    public Completable bookmarkMovie(@NonNull Movie movie, boolean isBookmarked) {
        BookmarkedMovie movieId = new BookmarkedMovie(movie.id);
        movie.setBookmarked(isBookmarked);
        if (isBookmarked) {
            return Completable.fromRunnable(() -> databaseService.movieDao().bookmarkMovie(movieId, movie));
        } else {
            return databaseService.movieDao().deleteBookmarkedMovie(movieId);
        }
    }

    private void insertTrendingMovies(List<Movie> movies, List<TrendingMovie> trendingMovies) {
        databaseService.movieDao().insertTrendingMovies(movies, trendingMovies);
    }

    private void insertNowPlayingMovies(List<Movie> movies, List<NowPlayingMovie> trendingMovies) {
        databaseService.movieDao().insertNowPlayingMovies(movies, trendingMovies);
    }

    public void insertMovies(@MoviesListType int movieListType, MoviesList moviesList) {
        List<Movie> movies;

        switch (movieListType) {
            case MoviesList.TYPE_TRENDING:
                movies = moviesList.results;
                List<TrendingMovie> trendingMovies = movies.stream()
                    .map(movie -> new TrendingMovie(movie.id))
                    .collect(Collectors.toList());
                insertTrendingMovies(movies, trendingMovies);
                break;
            case MoviesList.TYPE_NOW_PLAYING:
                movies = moviesList.results;
                List<NowPlayingMovie> nowPlayingMovies = movies.stream()
                    .map(movie -> new NowPlayingMovie(movie.id))
                    .collect(Collectors.toList());
                insertNowPlayingMovies(movies, nowPlayingMovies);
                break;
        }
    }


    private static class MoviesRemoteMediator extends RxRemoteMediator<Integer, Movie> {

        private final MovieRepository movieRepository;
        @MoviesListType private final int movieListType;

        private final MovieRemoteKeyDao remoteKeyDao;
        private final MovieDao movieDao;

        public MoviesRemoteMediator(
            MovieRepository movieRepository,
            @MoviesListType int movieListType
        ) {
            this.movieRepository = movieRepository;
            this.movieListType = movieListType;

            remoteKeyDao = movieRepository.remoteKeyDao;
            movieDao = movieRepository.movieDao;
        }

        @NonNull
        @Override
        public Single<MediatorResult> loadSingle(
            @NonNull LoadType loadType,
            @NonNull PagingState<Integer, Movie> pagingState
        ) {
            Single<MovieRemoteKey> remoteKeySingle = null;
            switch (loadType) {
                case REFRESH:
                    remoteKeySingle = Single.just(new MovieRemoteKey(movieListType, MoviesList.STARTING_PAGE));
                    break;
                case PREPEND:
                    // Never need to prepend, Immediately return, reporting end of pagination.
                    // pagination.
                    return Single.just(new MediatorResult.Success(true));
                case APPEND:
                    // Query remoteKeyDao for the next RemoteKey.
                    remoteKeySingle = movieRepository.remoteKeyDao.getRemoteKey(movieListType);
                    break;
            }

            return remoteKeySingle.subscribeOn(Schedulers.io())
                .flatMap((Function<MovieRemoteKey, Single<MediatorResult>>) remoteKey -> {
                    if (loadType != LoadType.REFRESH && remoteKey.nextKey == MoviesList.INAVLID_PAGE) {
                        return Single.just(new MediatorResult.Success(true));
                    }

                    return movieRepository.getMoviesListFromNetwork(movieListType, remoteKey.nextKey)
                        .map(moviesList -> {
                            int newKey = moviesList.results != null
                                ? (moviesList.page + 1) : MoviesList.INAVLID_PAGE;

                            movieRepository.databaseService.runInTransaction(() -> {
                                if (loadType == LoadType.REFRESH) {
                                    movieDao.deleteMovies(movieListType);
                                    remoteKeyDao.deleteKey(movieListType);
                                }

                                remoteKeyDao.insertKey(new MovieRemoteKey(movieListType, newKey));
                                movieRepository.insertMovies(movieListType, moviesList);
                            });

                            return new MediatorResult.Success(newKey == MoviesList.INAVLID_PAGE);
                        });
                })
                .onErrorResumeNext(e -> {
                    if (e instanceof IOException || e instanceof HttpException) {
                        return Single.just(new MediatorResult.Error(e));
                    }
                    return Single.error(e);
                });
        }
    }
}