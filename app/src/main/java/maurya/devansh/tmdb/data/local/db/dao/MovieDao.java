package maurya.devansh.tmdb.data.local.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.Completable;
import maurya.devansh.tmdb.data.model.BookmarkedMovie;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.model.NowPlayingMovie;
import maurya.devansh.tmdb.data.model.TrendingMovie;
import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 18/07/21.
 */

@Dao
public abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void bookmarkMovie(BookmarkedMovie movieId, Movie movie);

    @Delete
    public abstract Completable deleteBookmarkedMovie(BookmarkedMovie movieId);

    @Query("DELETE FROM trending_movie")
    protected abstract Completable deleteAllTrendingMovies();

    @Query("DELETE FROM now_playing_movie")
    protected abstract Completable deleteAllNowPlayingMovies();

    public Completable deleteMovies(@MoviesListType int type) {
        switch (type) {
            case MoviesList.TYPE_TRENDING:
                return deleteAllTrendingMovies();
            case MoviesList.TYPE_NOW_PLAYING:
                return deleteAllNowPlayingMovies();
            case MoviesList.TYPE_BOOKMARKED:
//                return getBookmarkedMovies();
            case MoviesList.TYPE_SEARCH_RESULT:
            default:
                throw new IllegalArgumentException("Invalid movie list type for DB query: " + type);
        }
    }

    // TODO: 18/07/21 Add order query
    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT bookmarked_movie.id FROM bookmarked_movie) ORDER BY timestamp")
    public abstract DataSource.Factory<Integer, Movie> getBookmarkedMovies();

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT trending_movie.id FROM trending_movie)")
    public abstract DataSource.Factory<Integer, Movie> getTrendingMovies();

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT trending_movie.id FROM trending_movie)")
    public abstract DataSource.Factory<Integer, Movie> getNowPlayingMovies();

    public DataSource.Factory<Integer, Movie> getMovies(@MoviesListType int type) {
        switch (type) {
            case MoviesList.TYPE_TRENDING:
                return getTrendingMovies();
            case MoviesList.TYPE_NOW_PLAYING:
                return getNowPlayingMovies();
            case MoviesList.TYPE_BOOKMARKED:
                return getBookmarkedMovies();
            case MoviesList.TYPE_SEARCH_RESULT:
            default:
                throw new IllegalArgumentException("Invalid movie list type for DB query: " + type);
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovieItems(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTrendingMovieIds(List<TrendingMovie> trendingMovies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertNowPlayingMovies(List<NowPlayingMovie> nowPlayingMovies);

    @Transaction
    public void insertTrendingMovies(List<Movie> movies, List<TrendingMovie> trendingMovies) {
        insertMovieItems(movies);
        insertTrendingMovieIds(trendingMovies);
    }

    @Transaction
    public void insertNowPlayingMovies(List<Movie> movies, List<NowPlayingMovie> trendingMovies) {
        insertMovieItems(movies);
        insertNowPlayingMovies(trendingMovies);
    }

}
