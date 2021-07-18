package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private int id = -1;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath = "";

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview = "";

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private String releaseDate = "";

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String title = "";

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private String originalLanguage = "";

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath = "";

    @ColumnInfo(name = "is_bookmarked")
    private int isBookmarked = 0;

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
                 String backdropPath,
                 int isBookmarked) {
        this.id = id;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.isBookmarked = isBookmarked;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked ? 1 : 0;
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
