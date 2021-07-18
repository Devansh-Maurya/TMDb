package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
public class Movie {
    @SerializedName("poster_path") private String posterPath = "";
    @SerializedName("overview") private String overview = "";
    @SerializedName("release_date") private String releaseDate = "";
    @SerializedName("id") private int id = -1;
    @SerializedName("title") private String title = "";
    @SerializedName("original_language") private String originalLanguage = "";
    @SerializedName("backdrop_path") private String backdropPath = "";

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
