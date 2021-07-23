package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Created by devansh on 19/07/21.
 */

@Keep
@Entity(tableName = "movie_id")
public class MovieId {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public final int id;

    @ColumnInfo(name = "page")
    public final int page;

    @ColumnInfo(name = "list_position")
    public final int listPosition;

    public MovieId(int id, int page, int listPosition) {
        this.id = id;
        this.page = page;
        this.listPosition = listPosition;
    }

    public MovieId(int id) {
        this(id, -1, -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieId movieId = (MovieId) o;
        return id == movieId.id &&
            page == movieId.page &&
            listPosition == movieId.listPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, page, listPosition);
    }
}
