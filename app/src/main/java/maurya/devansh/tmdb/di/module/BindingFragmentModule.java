package maurya.devansh.tmdb.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import maurya.devansh.tmdb.di.scope.PerFragment;
import maurya.devansh.tmdb.ui.bookmark.BookmarksFragment;
import maurya.devansh.tmdb.ui.detail.DetailFragment;
import maurya.devansh.tmdb.ui.home.HomeListFragment;
import maurya.devansh.tmdb.ui.search.SearchFragment;

/**
 * Created by devansh on 18/07/21.
 */

@Module
public abstract class BindingFragmentModule {

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    @PerFragment
    public abstract HomeListFragment contributeHomeListFragment();

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    @PerFragment
    public abstract BookmarksFragment contributeBookmarksFragment();

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    @PerFragment
    public abstract SearchFragment contributeSearchFragment();

    @ContributesAndroidInjector(modules = {FragmentModule.class})
    @PerFragment
    public abstract DetailFragment contributeDetailFragment();
}
