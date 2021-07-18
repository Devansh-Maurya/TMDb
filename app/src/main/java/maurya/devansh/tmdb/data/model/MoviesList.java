package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
public class MoviesList {
    @SerializedName("results") private List<Movie> results = null;

    public static final int TYPE_UNDEFINED = 0;
    public static final int TYPE_TRENDING = 1;
    public static final int TYPE_NOW_PLAYING = 2;
    public static final int TYPE_SEARCH_RESULT = 3;
    public static final int TYPE_BOOKMARKED = 4;

    public List<Movie> getResults() {
        return results;
    }
}
