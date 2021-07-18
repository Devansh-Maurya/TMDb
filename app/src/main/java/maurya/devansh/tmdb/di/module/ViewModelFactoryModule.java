package maurya.devansh.tmdb.di.module;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import maurya.devansh.tmdb.utils.viewmodel.ViewModelFactory;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
}
