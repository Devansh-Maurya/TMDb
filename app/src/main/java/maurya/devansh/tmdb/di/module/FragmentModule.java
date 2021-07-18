package maurya.devansh.tmdb.di.module;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import maurya.devansh.tmdb.di.ViewModelKey;
import maurya.devansh.tmdb.ui.bookmark.BookmarksViewModel;
import maurya.devansh.tmdb.ui.home.HomeListViewModel;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public abstract class FragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeListViewModel.class)
    public abstract ViewModel bindHomeListViewModel(HomeListViewModel homeListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel.class)
    public abstract ViewModel bindBookmarksViewModel(BookmarksViewModel bookmarksViewModel);
}
