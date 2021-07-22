package maurya.devansh.tmdb.data.model;

import android.text.TextUtils;

import androidx.annotation.Keep;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by devansh on 21/07/21.
 */

@Keep
@Entity(tableName = "movie_detail")
public class MovieDetail {
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

    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    public final int runtime;

    @SerializedName("genres")
    @ColumnInfo(name = "genres")
    public final List<Genre> genres;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    public final String overview;

    @ColumnInfo(name = "id_dummy")
    private boolean isDummy = false;

    public MovieDetail(int id,
                       String posterPath,
                       String releaseDate,
                       String title,
                       String originalLanguage,
                       int runtime,
                       List<Genre> genres,
                       String overview,
                       boolean isDummy
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
        this.runtime = runtime;
        this.genres = genres;
        this.overview = overview;
        this.isDummy = isDummy;
    }

    // Dummy object to be used when no data fetched from network
    @Ignore
    public MovieDetail(int id) {
        this(id, "", "", "", "", 0, new ArrayList<>(), "", true);
    }

    public boolean isDummy() {
        return isDummy;
    }

    public String getInfoString() {
        String infoSeparator = " | ";
        StringBuilder info = new StringBuilder();
        info.append(releaseDate);
        info.append(infoSeparator);

        long runtimeHour = TimeUnit.MINUTES.toHours(runtime);
        if (runtimeHour != 0) {
            info.append(runtimeHour).append(" h ");
        }
        long runtimeRemainingMinutes = runtime - TimeUnit.HOURS.toMinutes(runtimeHour);
        info.append(runtimeRemainingMinutes).append(" min");

        info.append(infoSeparator);
        info.append(originalLanguage.toUpperCase(Locale.ROOT));

        info.append("\n");
        List<String> genreNames = genres.stream()
            .map(genre -> genre.name)
            .collect(Collectors.toList());
        info.append(TextUtils.join(", ", genreNames));

        return info.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDetail that = (MovieDetail) o;
        return id == that.id &&
            runtime == that.runtime &&
            Objects.equals(posterPath, that.posterPath) &&
            Objects.equals(releaseDate, that.releaseDate) &&
            Objects.equals(title, that.title) &&
            Objects.equals(originalLanguage, that.originalLanguage) &&
            Objects.equals(genres, that.genres) &&
            Objects.equals(overview, that.overview);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, posterPath, releaseDate, title, originalLanguage, runtime, genres, overview);
    }
}
