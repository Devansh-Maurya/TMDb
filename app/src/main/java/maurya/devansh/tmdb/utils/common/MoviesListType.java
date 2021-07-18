package maurya.devansh.tmdb.utils.common;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import maurya.devansh.tmdb.data.model.MoviesList;


/**
 * Created by devansh on 18/07/21.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        MoviesList.TYPE_TRENDING,
        MoviesList.TYPE_NOW_PLAYING,
        MoviesList.TYPE_SEARCH_RESULT,
        MoviesList.TYPE_BOOKMARKED
})
public @interface MoviesListType {
}
