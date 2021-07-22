package maurya.devansh.tmdb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import maurya.devansh.tmdb.databinding.FragmentHomeListBinding;
import maurya.devansh.tmdb.ui.MainViewModel;
import maurya.devansh.tmdb.ui.base.Action;
import maurya.devansh.tmdb.ui.base.ActionPerformer;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;

public class HomeListFragment extends DaggerBaseFragment<HomeListViewModel, FragmentHomeListBinding>
    implements ActionPerformer {

    private static final String TYPE = "type";

    private MainViewModel mainViewModel;
    private final MovieAdapter movieAdapter = new MovieAdapter(this, true);

    public static HomeListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Pair<ViewModelStoreOwner, Class<HomeListViewModel>> provideViewModelCreators() {
        return new Pair<>(this, HomeListViewModel.class);
    }

    @Override
    protected FragmentHomeListBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeListBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull View view) {
        mainViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel.class);
        binding().recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void setupObservers() {
        if (getArguments() != null) {
            int movieListType = getArguments().getInt(TYPE);
            viewModel.getMovies(movieListType).observe(getViewLifecycleOwner(), movies -> {
                binding().progressBar.setVisibility(View.GONE);
                movieAdapter.submitData(getViewLifecycleOwner().getLifecycle(), movies);
            });
        }
    }

    @Override
    public void performAction(Action action) {
        if (action instanceof Action.MovieBookmarked) {
            mainViewModel.bookmarkMovie(((Action.MovieBookmarked) action));
        }
    }
}