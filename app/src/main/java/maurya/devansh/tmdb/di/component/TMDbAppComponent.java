package maurya.devansh.tmdb.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import maurya.devansh.tmdb.TMDbApp;
import maurya.devansh.tmdb.di.module.AppModule;
import maurya.devansh.tmdb.di.module.BindingFragmentModule;
import maurya.devansh.tmdb.di.module.ViewModelFactoryModule;

/**
 * Created by devansh on 17/07/21.
 */

@Component(modules = {
        AppModule.class,
        BindingFragmentModule.class,
        AndroidInjectionModule.class,
        ViewModelFactoryModule.class
})
@Singleton
public interface TMDbAppComponent {

    @Component.Builder
    interface Builder {
        TMDbAppComponent build();

        @BindsInstance
        Builder application(Application application);
    }

    void inject(TMDbApp app);
}
