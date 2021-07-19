package maurya.devansh.tmdb.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

/**
 * Created by devansh on 19/07/21.
 */

@Entity
public class MovieId {

    @PrimaryKey
    @ColumnInfo(name = "id")
    public final int id;

    public MovieId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieId movieId = (MovieId) o;
        return id == movieId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
