package maurya.devansh.tmdb.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import maurya.devansh.tmdb.databinding.FragmentHomeListBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;

public class HomeListFragment extends DaggerBaseFragment<HomeViewModel, FragmentHomeListBinding> {

    private static final String TYPE = "type";

    private final MovieAdapter movieAdapter = new MovieAdapter();

    public HomeListFragment() {
        // Required empty public constructor
    }

    public static HomeListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(TYPE, type);

        HomeListFragment fragment = new HomeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Pair<ViewModelStoreOwner, Class<HomeViewModel>> provideViewModelCreators() {
        return new Pair<>(this, HomeViewModel.class);
    }

    @Override
    protected FragmentHomeListBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeListBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull View view) {
        FragmentHomeListBinding binding = binding();
        binding.recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void setupObservers() {
        viewModel.movieListLiveData.observe(getViewLifecycleOwner(), movieAdapter::submitList);
    }
}