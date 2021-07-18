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
    @SerializedName("poster_path") public String posterPath = "";
    @SerializedName("overview") public String overview = "";
    @SerializedName("release_date") public String releaseDate = "";
    @SerializedName("id") public int id = -1;
    @SerializedName("title") public String title = "";
    @SerializedName("original_language") public String originalLanguage = "";
    @SerializedName("backdrop_path") public String backdropPath = "";

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

    private void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    private void setOverview(String overview) {
        this.overview = overview;
    }

    private void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    private void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
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
