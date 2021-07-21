package maurya.devansh.tmdb.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.databinding.FragmentSearchBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;

public class SearchFragment extends DaggerBaseFragment<SearchViewModel, FragmentSearchBinding> {

    @Override
    protected Pair<ViewModelStoreOwner, Class<SearchViewModel>> provideViewModelCreators() {
        return new Pair<>(this, SearchViewModel.class);
    }

    @Override
    protected FragmentSearchBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {
        setHasOptionsMenu(true);
        binding().toolbar.inflateMenu(R.menu.menu_search);
        binding().toolbar.setOnMenuItemClickListener(menuItem -> {
            final SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // perform query here
                    toast(query);

                    // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                    // see https://code.google.com/p/android/issues/detail?id=24599
                    searchView.clearFocus();

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    toast(newText);
                    return false;
                }
            });

            return true;
        });
    }
}