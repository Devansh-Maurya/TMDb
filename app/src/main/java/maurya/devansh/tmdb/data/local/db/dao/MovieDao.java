package maurya.devansh.tmdb.data.local.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import io.reactivex.Completable;
import maurya.devansh.tmdb.data.model.BookmarkedMovie;
import maurya.devansh.tmdb.data.model.Movie;

/**
 * Created by devansh on 18/07/21.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bookmarkMovie(BookmarkedMovie movieId, Movie movie);

    @Delete
    Completable deleteBookmarkedMovie(BookmarkedMovie movieId);

    // TODO: 18/07/21 Add order query
    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (SELECT bookmarked_movie.id FROM bookmarked_movie)")
    DataSource.Factory<Integer, Movie> getBookmarkedMovies();
}
