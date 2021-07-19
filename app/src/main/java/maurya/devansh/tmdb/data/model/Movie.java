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

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    public final String overview;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    public final String releaseDate;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    public final String title;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    public final String originalLanguage;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    public final String backdropPath;

    @Ignore
    public int isBookmarked;

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
                 String overview,
                 String releaseDate,
                 String title,
                 String originalLanguage,
                 String backdropPath
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public Movie(int id,
                 String posterPath,
                 String overview,
                 String releaseDate,
                 String title,
                 String originalLanguage,
                 String backdropPath,
                 int isBookmarked) {
        this(id, posterPath, overview, releaseDate, title, originalLanguage, backdropPath);
        this.isBookmarked = isBookmarked;
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
                posterPath.equals(movie.posterPath) &&
                overview.equals(movie.overview) &&
                releaseDate.equals(movie.releaseDate) &&
                title.equals(movie.title) &&
                originalLanguage.equals(movie.originalLanguage) &&
                backdropPath.equals(movie.backdropPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posterPath, overview, releaseDate, id, title, originalLanguage, backdropPath);
    }
}
