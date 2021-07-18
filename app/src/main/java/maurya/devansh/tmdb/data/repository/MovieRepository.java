package maurya.devansh.tmdb.data.repository;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;
import maurya.devansh.tmdb.data.local.db.DatabaseService;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.remote.NetworkService;
import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieRepository {

    private final NetworkService networkService;
    private final DatabaseService databaseService;

    @Inject
    MovieRepository(NetworkService networkService, DatabaseService databaseService) {
        this.networkService = networkService;
        this.databaseService = databaseService;
    }

    public Single<MoviesList> getMoviesList(@MoviesListType int type, int page) {
        switch (type) {
            case MoviesList.TYPE_TRENDING:
                return getTrendingMovies(page);
            case MoviesList.TYPE_NOW_PLAYING:
                return getTrendingMovies(page);
            case MoviesList.TYPE_BOOKMARKED:
                return getTrendingMovies(page);
            case MoviesList.TYPE_SEARCH_RESULT:
                return getTrendingMovies(page);
            default:
                throw new IllegalArgumentException("Invalid movie list type: " + type);
        }
    }

    private Single<MoviesList> getTrendingMovies(int page) {
        return networkService.getTrendingMovies(page);
    }

    private Single<MoviesList> getNowPlayingMovies(int page) {
        return networkService.getNowPlayingMovies(page, "IN");
    }

    public Single<Long> bookmarkMovie(@NonNull Movie movie, boolean isBookmarked) {
        movie.setBookmarked(isBookmarked);
        return databaseService.movieDao().insertBookmarkedMovie(movie);
    }
}
