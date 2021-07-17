package maurya.devansh.tmdb.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import maurya.devansh.tmdb.di.module.AppModule;

/**
 * Created by devansh on 17/07/21.
 */

@Component(modules = {AppModule.class})
@Singleton
public interface TMDbAppComponent {

    @Component.Builder
    interface Builder {
        TMDbAppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
