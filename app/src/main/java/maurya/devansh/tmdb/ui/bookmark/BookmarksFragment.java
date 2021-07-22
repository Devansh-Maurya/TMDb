package maurya.devansh.tmdb.ui.bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import maurya.devansh.tmdb.databinding.FragmentBookmarksBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;

public class BookmarksFragment extends DaggerBaseFragment<BookmarksViewModel, FragmentBookmarksBinding> {

    private MovieAdapter movieAdapter;

    @Override
    protected Pair<ViewModelStoreOwner, Class<BookmarksViewModel>> provideViewModelCreators() {
        return new Pair<>(this, BookmarksViewModel.class);
    }

    @Override
    protected FragmentBookmarksBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentBookmarksBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {
        movieAdapter = new MovieAdapter(null, false);
        movieAdapter.refresh();
        binding().recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void setupObservers() {
        viewModel.getBookmarkedMovies().observe(getViewLifecycleOwner(), movies -> {
            binding().progressBar.setVisibility(View.GONE);
            movieAdapter.submitData(getViewLifecycleOwner().getLifecycle(), movies);
        });
    }
}