package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
public class MoviesList {
    @SerializedName("page") public int page = -1;
    @SerializedName("results") public List<Movie> results = null;
    // TODO: 18/07/21 Fix here
    public static final int TYPE_UNDEFINED = 0;
    public static final int TYPE_TRENDING = 1;
    public static final int TYPE_NOW_PLAYING = 2;
    public static final int TYPE_SEARCH_RESULT = 3;
    public static final int TYPE_BOOKMARKED = 4;

    private void setPage(int page) {
        this.page = page;
    }

    private void setResults(List<Movie> results) {
        this.results = results;
    }
}
