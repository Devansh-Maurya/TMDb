package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
public class MoviesList {

    public static final int TYPE_UNDEFINED = 0;
    public static final int TYPE_TRENDING = 1;
    public static final int TYPE_NOW_PLAYING = 2;
    public static final int TYPE_SEARCH_RESULT = 3;
    public static final int TYPE_BOOKMARKED = 4;

    public static final int PAGE_SIZE = 20;
    public static final int STARTING_PAGE = 1;
    public static final int INVALID_PAGE = -1;

    @SerializedName("results") public final List<Movie> results;
    @SerializedName("page") public final int page;
    @SerializedName("total_pages") public final int totalPages;

    public MoviesList() {
        this.results = null;
        this.page = INVALID_PAGE;
        this.totalPages = INVALID_PAGE;
    }
}
