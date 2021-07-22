package maurya.devansh.tmdb.di.module;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import maurya.devansh.tmdb.di.ViewModelKey;
import maurya.devansh.tmdb.ui.MainViewModel;

/**
 * Created by devansh on 22/07/21.
 */

@Module
public interface ActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);
}
