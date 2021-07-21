package maurya.devansh.tmdb.di.module;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import maurya.devansh.tmdb.di.ViewModelKey;
import maurya.devansh.tmdb.ui.bookmark.BookmarksViewModel;
import maurya.devansh.tmdb.ui.detail.DetailViewModel;
import maurya.devansh.tmdb.ui.home.HomeListViewModel;
import maurya.devansh.tmdb.ui.search.SearchViewModel;

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

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    public abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);
}
