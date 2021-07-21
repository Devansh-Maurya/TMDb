package maurya.devansh.tmdb.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Single;
import maurya.devansh.tmdb.data.model.MovieRemoteKey;
import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 21/07/21.
 */

@Dao
public interface MovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertKey(MovieRemoteKey movieRemoteKey);

    @Query("SELECT * FROM movie_remote_keys WHERE movie_list_type = :movieListType")
    Single<MovieRemoteKey> getRemoteKey(@MoviesListType int movieListType);

    @Query("DELETE FROM movie_remote_keys WHERE movie_list_type = :movieListType")
    void deleteKey(@MoviesListType int movieListType);
}
