package maurya.devansh.tmdb.ui.base;

import maurya.devansh.tmdb.data.model.Movie;

/**
 * Created by devansh on 18/07/21.
 */

public class Action {

    public static class MovieBookmarked extends Action {
        public final Movie movie;
        public final boolean isBookmarked;

        public MovieBookmarked(Movie movie, boolean isBookmarked) {
            this.movie = movie;
            this.isBookmarked = isBookmarked;
        }
    }
}
