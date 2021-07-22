package maurya.devansh.tmdb.data.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Movie implements Parcelable {
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    public int id;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    public String title;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    public String originalLanguage;

    @Ignore
    private int isBookmarked;

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
                 String originalLanguage
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.originalLanguage = originalLanguage;
    }

    @Ignore
    public Movie() {
        this(-1, "", "", "", "");
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.title);
        dest.writeString(this.originalLanguage);
        dest.writeInt(this.isBookmarked);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.posterPath = source.readString();
        this.releaseDate = source.readString();
        this.title = source.readString();
        this.originalLanguage = source.readString();
        this.isBookmarked = source.readInt();
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.title = in.readString();
        this.originalLanguage = in.readString();
        this.isBookmarked = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
