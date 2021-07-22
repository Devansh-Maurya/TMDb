package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by devansh on 21/07/21.
 */

@Keep
public class Genre {
    @SerializedName("id")
    @ColumnInfo(name = "id")
    public final int id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    public final String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Ignore
    public Genre() {
        this(-1, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id == genre.id &&
            Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
