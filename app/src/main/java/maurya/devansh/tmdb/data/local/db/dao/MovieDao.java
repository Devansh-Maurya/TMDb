package maurya.devansh.tmdb.data.local.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import maurya.devansh.tmdb.data.model.Movie;

/**
 * Created by devansh on 18/07/21.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertBookmarkedMovie(Movie movie);

    @Delete
    Completable deleteBookmarkedMovie(Movie movie);

    // TODO: 18/07/21 Add order query
    @Query("SELECT * FROM movie WHERE is_bookmarked = 1")
    DataSource.Factory<Integer, Movie> getBookmarkedMovies();

}
