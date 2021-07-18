package maurya.devansh.tmdb.di.module;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import maurya.devansh.tmdb.di.ViewModelKey;
import maurya.devansh.tmdb.ui.home.HomeViewModel;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public abstract class FragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);
}
