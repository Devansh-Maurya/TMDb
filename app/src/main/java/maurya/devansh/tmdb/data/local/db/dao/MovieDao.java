package maurya.devansh.tmdb.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingSource;
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
import maurya.devansh.tmdb.data.model.MovieDetail;
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
    protected abstract void deleteAllTrendingMovies();

    @Query("DELETE FROM now_playing_movie")
    protected abstract void deleteAllNowPlayingMovies();

    public void deleteMovies(@MoviesListType int type) {
        switch (type) {
            case MoviesList.TYPE_TRENDING:
                deleteAllTrendingMovies();
                break;
            case MoviesList.TYPE_NOW_PLAYING:
                deleteAllNowPlayingMovies();
                break;
            case MoviesList.TYPE_BOOKMARKED:
//                return getBookmarkedMovies();
                break;
            case MoviesList.TYPE_SEARCH_RESULT:
            default:
                throw new IllegalArgumentException("Invalid movie list type for DB query: " + type);
        }
    }

    // TODO: 18/07/21 Add order query
    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT bookmarked_movie.id FROM bookmarked_movie)")
    public abstract PagingSource<Integer, Movie> getBookmarkedMovies();

    @Transaction
    @Query("SELECT movie.* FROM movie, trending_movie AS tm WHERE movie.id == tm.id ORDER BY tm.page, tm.list_position")
    public abstract PagingSource<Integer, Movie> getTrendingMovies();

    @Transaction
    @Query("SELECT movie.* FROM movie, now_playing_movie AS np WHERE movie.id == np.id ORDER BY np.page, np.list_position")
    public abstract PagingSource<Integer, Movie> getNowPlayingMovies();

    public PagingSource<Integer, Movie> getMovies(@MoviesListType int type) {
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
    protected abstract void insertTrendingMovieIds(List<TrendingMovie> trendingMovies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertNowPlayingMovies(List<NowPlayingMovie> nowPlayingMovies);

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovieDetail(MovieDetail movieDetail);

    @Query("SELECT * FROM movie_detail WHERE id == :movieId")
    public abstract LiveData<MovieDetail> getMovieDetail(int movieId);
}
