package maurya.devansh.tmdb.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by devansh on 17/07/21.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
