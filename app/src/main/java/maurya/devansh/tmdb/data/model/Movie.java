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

    @ColumnInfo(name = "timestamp")
    public final long timeStamp;

    @Ignore
    public int isBookmarked;

    public static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            if (oldItem == null) return false;
            return oldItem.equals(newItem);
        }
    };

    public Movie(int id,
                 String posterPath,
                 String releaseDate,
                 String title,
                 String originalLanguage,
                 long timeStamp
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.timeStamp = timeStamp;
    }

    @Ignore
    public Movie() {
        this(-1, "", "", "", "", System.currentTimeMillis());
        isBookmarked = 0;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked ? 1 : 0;
    }

    public boolean bookmarked() {
        return isBookmarked == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                posterPath != null && posterPath.equals(movie.posterPath) &&
                releaseDate != null && releaseDate.equals(movie.releaseDate) &&
                title != null && title.equals(movie.title) &&
                originalLanguage != null && originalLanguage.equals(movie.originalLanguage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posterPath, releaseDate, id, title, originalLanguage);
    }
}
