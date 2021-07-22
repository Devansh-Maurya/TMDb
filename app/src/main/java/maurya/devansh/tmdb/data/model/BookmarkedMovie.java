package maurya.devansh.tmdb.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * Created by devansh on 19/07/21.
 */

@Entity(tableName = "bookmarked_movie")
public class BookmarkedMovie extends MovieId {

    @ColumnInfo(name = "bookmark_timestamp")
    public final long bookmarkTimestamp;

    public BookmarkedMovie(int id, long bookmarkTimestamp) {
        super(id);
        this.bookmarkTimestamp = bookmarkTimestamp;
    }
}