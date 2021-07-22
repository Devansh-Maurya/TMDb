package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
@Entity
public class Movie {
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    public final int id;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    public final String posterPath;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    public final String releaseDate;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    public final String title;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    public final String originalLanguage;

    @ColumnInfo(name = "bookmark_id")
    public final int bookmarkId;

    @Ignore
    private boolean isBookmarked;

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    public Movie(int id,
                 String posterPath,
                 String releaseDate,
                 String title,
                 String originalLanguage,
                 int bookmarkId
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.bookmarkId = bookmarkId;
    }

    @Ignore
    public Movie(int id,
                 String posterPath,
                 String releaseDate,
                 String title,
                 String originalLanguage
    ) {
        this(id, posterPath, releaseDate, title, originalLanguage, 0);
    }

    @Ignore
    public Movie() {
        this(-1, "", "", "", "");
        isBookmarked = false;
    }

    public void setBookmarked(boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public boolean isBookmarked() {
        return isBookmarked || bookmarkId != 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
            bookmarkId == movie.bookmarkId &&
            Objects.equals(posterPath, movie.posterPath) &&
            Objects.equals(releaseDate, movie.releaseDate) &&
            Objects.equals(title, movie.title) &&
            Objects.equals(originalLanguage, movie.originalLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posterPath, releaseDate, title, originalLanguage, bookmarkId);
    }
}
