package maurya.devansh.tmdb.di.module;

import dagger.Module;
import dagger.Provides;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.di.qualifier.MoviesListTypeQualifier;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public class MoviesListTypeModule {

    @Provides
    @MoviesListTypeQualifier(MoviesList.TYPE_TRENDING)
    int provideTrendingMoviesListType() {
        return MoviesList.TYPE_TRENDING;
    }

    @Provides
    @MoviesListTypeQualifier(MoviesList.TYPE_NOW_PLAYING)
    int provideNowPlayingMoviesListType() {
        return MoviesList.TYPE_NOW_PLAYING;
    }

    @Provides
    @MoviesListTypeQualifier(MoviesList.TYPE_SEARCH_RESULT)
    int provideSearchResultMoviesListType() {
        return MoviesList.TYPE_SEARCH_RESULT;
    }

    @Provides
    @MoviesListTypeQualifier(MoviesList.TYPE_BOOKMARKED)
    int provideBookmarkedMoviesListType() {
        return MoviesList.TYPE_BOOKMARKED;
    }
}