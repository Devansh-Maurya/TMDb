package maurya.devansh.tmdb.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import maurya.devansh.tmdb.di.scope.PerActivity;
import maurya.devansh.tmdb.ui.MainActivity;

/**
 * Created by devansh on 22/07/21.
 */

@Module
public interface BindingActivityModule {

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    @PerActivity
    MainActivity contributeMainActivity();
}

