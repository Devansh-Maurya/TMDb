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
import maurya.devansh.tmdb.ui.base.Action;
import maurya.devansh.tmdb.ui.base.ActionPerformer;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;

public class SearchFragment extends DaggerBaseFragment<SearchViewModel, FragmentSearchBinding>
    implements ActionPerformer {

    private final MovieAdapter movieAdapter = new MovieAdapter(this);

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
        binding().recyclerView.setAdapter(movieAdapter);
        setupSearchBar();
    }

    private void setupSearchBar() {
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
                    viewModel.getSearchResults(newText);
                    return false;
                }
            });

            return true;
        });
    }

    @Override
    protected void setupObservers() {
        viewModel.searchResultsLiveData.observe(getViewLifecycleOwner(), movies -> {
            binding().progressBar.setVisibility(View.GONE);
            movieAdapter.submitData(getViewLifecycleOwner().getLifecycle(), movies);
        });
    }

    @Override
    public void performAction(Action action) {

    }
}