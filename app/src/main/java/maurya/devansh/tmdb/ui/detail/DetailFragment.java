package maurya.devansh.tmdb.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;

import maurya.devansh.tmdb.databinding.FragmentDetailBinding;
import maurya.devansh.tmdb.ui.base.Action;
import maurya.devansh.tmdb.ui.base.ActionPerformer;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;

public class DetailFragment extends DaggerBaseFragment<DetailViewModel, FragmentDetailBinding>
    implements ActionPerformer {

    private static final String TYPE = "type";

    private final MovieAdapter movieAdapter = new MovieAdapter(this);

    @Override
    protected Pair<ViewModelStoreOwner, Class<DetailViewModel>> provideViewModelCreators() {
        return new Pair<>(this, DetailViewModel.class);
    }

    @Override
    protected FragmentDetailBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentDetailBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull View view) {
        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
//        binding().recyclerView.setAdapter(movieAdapter);
        Glide.with(binding().ivPoster)
            .load(args.getImageUrl())
            .into(binding().ivPoster);
//        DetailFragmentDirections.actionOpenDetailScreen()
    }

    @Override
    protected void setupObservers() {
        if (getArguments() != null) {
            int movieListType = getArguments().getInt(TYPE);

//            viewModel.getMovies(movieListType).observe(getViewLifecycleOwner(), movies -> {
////                binding().progressBar.setVisibility(View.GONE);
//                movieAdapter.submitData(getViewLifecycleOwner().getLifecycle(), movies);
//            });
        }
    }

    @Override
    public void performAction(Action action) {
        if (action instanceof Action.MovieBookmarked) {
            Action.MovieBookmarked movieBookmarked = (Action.MovieBookmarked) action;
            viewModel.bookmarkMovie(movieBookmarked.movie, movieBookmarked.isBookmarked);
            toast(movieBookmarked.movie.title + movieBookmarked.isBookmarked);
        }
    }
}