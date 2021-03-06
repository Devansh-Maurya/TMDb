package maurya.devansh.tmdb.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.databinding.FragmentSearchBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.movie.MovieAdapter;

public class SearchFragment extends DaggerBaseFragment<SearchViewModel, FragmentSearchBinding> {

    private final MovieAdapter movieAdapter = new MovieAdapter(null, false);

    @Override
    protected Pair<ViewModelStoreOwner, Class<SearchViewModel>> provideViewModelCreators() {
        return new Pair<>(this, SearchViewModel.class);
    }

    @Override
    protected FragmentSearchBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull View view) {
        binding().recyclerView.setAdapter(movieAdapter);
        binding().toolbar.setNavigationOnClickListener(v ->
            NavHostFragment.findNavController(this).navigateUp()
        );
        setupSearchBar();
    }

    private void setupSearchBar() {
        binding().toolbar.inflateMenu(R.menu.menu_search);
        SearchView searchView = (SearchView) binding().toolbar.getMenu().findItem(R.id.actionSearch).getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.getSearchResults(newText);
                if (newText.isEmpty() && movieAdapter.getItemCount() == 0) {
                    binding().layoutEmpty.setVisibility(View.VISIBLE);
                } else {
                    binding().layoutEmpty.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    protected void setupObservers() {
        viewModel.setupSearchObserver(pagingDataLiveData ->
            pagingDataLiveData.observe(getViewLifecycleOwner(), movies -> {
                binding().progressBar.setVisibility(View.GONE);
                movieAdapter.submitData(getViewLifecycleOwner().getLifecycle(), movies);
            }));
    }
}