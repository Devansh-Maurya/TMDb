package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 21/07/21.
 */

@Keep
@Entity(tableName = "movie_remote_keys")
public class MovieRemoteKey {

    @ColumnInfo(name = "next_key")
    public final int nextKey;

    @PrimaryKey
    @ColumnInfo(name = "movie_list_type")
    @MoviesListType public final int movieListType;

    public MovieRemoteKey(int nextKey, int movieListType) {
        this.nextKey = nextKey;
        this.movieListType = movieListType;
    }
}
