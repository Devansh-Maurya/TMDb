package maurya.devansh.tmdb.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LoadType;
import androidx.paging.PagedList;
import androidx.paging.PagingSource;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
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
import maurya.devansh.tmdb.data.model.TrendingMovie;
import maurya.devansh.tmdb.data.remote.NetworkService;
import maurya.devansh.tmdb.utils.common.MoviesListType;
import maurya.devansh.tmdb.utils.network.PagingRequestHelper;
import retrofit2.HttpException;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieRepository {

    private final NetworkService networkService;
    public final DatabaseService databaseService;
    private final CompositeDisposable compositeDisposable;

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
        this.compositeDisposable = compositeDisposable;

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

    public LiveData<PagedList<Movie>> getMoviesList(@MoviesListType int type, int page) {
        compositeDisposable.add(getMoviesListFromNetwork(type, page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(moviesList -> movieDao.deleteMovies(type), throwable -> {
            })
        );
        PagedList.Config config = new PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(20)
            .build();

//        return new LivePagedListBuilder<>(databaseService.movieDao().getMovies(type), config)
//            .setBoundaryCallback(
//                new MoviesListBoundaryCallback(
//                    this, MoviesList.TYPE_TRENDING, compositeDisposable
//                )
//            )
//            .build();
        return null;
    }

    private Single<MoviesList> getTrendingMoviesFromNetwork(int page) {
        return networkService.getTrendingMovies(page);
    }

    private Single<MoviesList> getNowPlayingMoviesFromNetwork(int page) {
        return networkService.getNowPlayingMovies(page, "IN");
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

    public void insertTrendingMovies(List<Movie> movies, List<TrendingMovie> trendingMovies) {
        databaseService.movieDao().insertTrendingMovies(movies, trendingMovies);
    }

    public void insertNowPlayingMovies(List<Movie> movies, List<TrendingMovie> trendingMovies) {
        databaseService.movieDao().insertTrendingMovies(movies, trendingMovies);
    }

    public void insertMovies(@MoviesListType int movieListType, MoviesList moviesList) {
        List<Movie> movies;
        List<TrendingMovie> movieIds;

        switch (movieListType) {
            case MoviesList.TYPE_TRENDING:
                movies = moviesList.results;
                movieIds = movies.stream()
                    .map(movie -> new TrendingMovie(movie.id))
                    .collect(Collectors.toList());
                insertTrendingMovies(movies, movieIds);
                break;
            case MoviesList.TYPE_NOW_PLAYING:
                movies = moviesList.results;
                movieIds = movies.stream()
                    .map(movie -> new TrendingMovie(movie.id))
                    .collect(Collectors.toList());
                insertNowPlayingMovies(movies, movieIds);
                break;
        }
    }


    private class MoviesListBoundaryCallback extends PagedList.BoundaryCallback<Movie> {

        private final MovieRepository movieRepository;
        private final int movieListType;
        private final CompositeDisposable compositeDisposable;

        private final PagingRequestHelper helper = new PagingRequestHelper(Executors.newSingleThreadExecutor());
        private int pageNumber = 1;

        public MoviesListBoundaryCallback(
            MovieRepository movieRepository,
            @MoviesListType int movieListType,
            CompositeDisposable compositeDisposable
        ) {
            this.movieRepository = movieRepository;
            this.movieListType = movieListType;
            this.compositeDisposable = compositeDisposable;
        }

        @Override
        public void onZeroItemsLoaded() {
            getMoviesList(PagingRequestHelper.RequestType.INITIAL);
        }

        @Override
        public void onItemAtEndLoaded(@NonNull @NotNull Movie itemAtEnd) {
            getMoviesList(PagingRequestHelper.RequestType.AFTER);
        }

        private void getMoviesList(@NonNull PagingRequestHelper.RequestType requestType) {
            helper.runIfNotRunning(requestType, callback ->
                compositeDisposable.add(
                    movieRepository.getMoviesListFromNetwork(movieListType, pageNumber)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(moviesList -> {
                            insertMovies(moviesList);
                            callback.recordSuccess();
                        }, callback::recordFailure)
                ));
        }

        public void insertMovies(MoviesList moviesList) {
            List<Movie> movies;
            List<TrendingMovie> movieIds;

            switch (movieListType) {
                case MoviesList.TYPE_TRENDING:
                    movies = moviesList.results;
                    movieIds = movies.stream()
                        .map(movie -> new TrendingMovie(movie.id))
                        .collect(Collectors.toList());
                    movieRepository.insertTrendingMovies(movies, movieIds);
                    break;
                case MoviesList.TYPE_NOW_PLAYING:
                    movies = moviesList.results;
                    movieIds = movies.stream()
                        .map(movie -> new TrendingMovie(movie.id))
                        .collect(Collectors.toList());
                    movieRepository.insertNowPlayingMovies(movies, movieIds);
                    break;
            }
            pageNumber++;
        }
    }


    private static class MoviesRemoteMediator extends RxRemoteMediator<Integer, Movie> {

        private final MovieRepository movieRepository;
        @MoviesListType private final int movieListType;
        private final CompositeDisposable compositeDisposable;

        private final MovieRemoteKeyDao remoteKeyDao;
        private final MovieDao movieDao;

        public MoviesRemoteMediator(
            MovieRepository movieRepository,
            @MoviesListType int movieListType,
            CompositeDisposable compositeDisposable
        ) {
            this.movieRepository = movieRepository;
            this.movieListType = movieListType;
            this.compositeDisposable = compositeDisposable;

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
                    remoteKeySingle = Single.just(new MovieRemoteKey(movieListType, 1));
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
                    if (loadType != LoadType.REFRESH && remoteKey.nextKey == -1) {
                        return Single.just(new MediatorResult.Success(true));
                    }

                    return movieRepository.getMoviesListFromNetwork(movieListType, remoteKey.nextKey)
                        .map(moviesList -> {
                            int newKey = moviesList.results != null ? moviesList.page : -1;


                            movieRepository.databaseService.runInTransaction(() -> {
                                if (loadType == LoadType.REFRESH) {
                                    movieDao.deleteMovies(movieListType);
                                    remoteKeyDao.deleteKey(movieListType);
                                }

                                remoteKeyDao.insertKey(new MovieRemoteKey(movieListType, newKey));
                                movieRepository.insertMovies(movieListType, moviesList);
                            });

                            return new MediatorResult.Success(newKey == -1);
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
