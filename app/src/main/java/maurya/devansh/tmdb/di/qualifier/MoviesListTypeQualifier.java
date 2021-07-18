package maurya.devansh.tmdb.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 18/07/21.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface MoviesListTypeQualifier {
    @MoviesListType int value();
}
