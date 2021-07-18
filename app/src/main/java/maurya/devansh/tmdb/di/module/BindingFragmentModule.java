package maurya.devansh.tmdb.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import maurya.devansh.tmdb.di.scope.PerFragment;
import maurya.devansh.tmdb.ui.home.HomeFragment;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public abstract class BindingFragmentModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    @PerFragment
    public abstract HomeFragment contributeHomeFragment();
}
