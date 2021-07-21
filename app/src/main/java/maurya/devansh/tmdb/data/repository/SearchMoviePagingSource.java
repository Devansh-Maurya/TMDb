package maurya.devansh.tmdb.data.repository;

import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.local.db.dao.MovieDao;
import maurya.devansh.tmdb.data.local.db.dao.MovieRemoteKeyDao;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MoviesList;
import retrofit2.HttpException;

/**
 * Created by devansh on 21/07/21.
 */

public class SearchMoviePagingSource extends RxPagingSource<Integer, Movie> {

    private final MovieRepository movieRepository;
    private final String query;

    private final MovieRemoteKeyDao remoteKeyDao;
    private final MovieDao movieDao;

    public SearchMoviePagingSource(
        MovieRepository movieRepository,
        String query
    ) {
        this.movieRepository = movieRepository;
        this.query = query;

        remoteKeyDao = movieRepository.remoteKeyDao;
        movieDao = movieRepository.movieDao;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int page;
        if (loadParams.getKey() != null && loadParams.getKey() != MoviesList.INVALID_PAGE) {
            page = loadParams.getKey();
        } else {
            page = MoviesList.STARTING_PAGE;
        }

        return movieRepository.networkService.searchMovies(query, page)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map((Function<? super MoviesList, LoadResult<Integer, Movie>>) moviesList -> {
                Integer nextKey = moviesList.page != moviesList.totalPages
                    ? (moviesList.page + 1) : null;
                Integer prevKey = moviesList.page != MoviesList.STARTING_PAGE
                    ? moviesList.page - 1 : null;
                return new LoadResult.Page<>(moviesList.results, prevKey, nextKey);
            })
            .onErrorResumeNext(e -> {
                if (e instanceof IOException || e instanceof HttpException) {
                    return Single.just(new LoadResult.Error<>(e));
                }
                return Single.error(e);
            });
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        if (pagingState.getAnchorPosition() != null) {
            int anchorPosition = pagingState.getAnchorPosition();
            LoadResult.Page<Integer, Movie> closestPage = pagingState.closestPageToPosition(anchorPosition);
            if (closestPage != null) {
                if (closestPage.getPrevKey() != null) {
                    return closestPage.getPrevKey() + 1;
                } else if (closestPage.getNextKey() != null) {
                    return closestPage.getNextKey() - 1;
                }
            }
        }
        return null;
    }
}
