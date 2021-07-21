package maurya.devansh.tmdb.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.Glide;

import maurya.devansh.tmdb.data.model.MovieDetail;
import maurya.devansh.tmdb.databinding.FragmentDetailBinding;
import maurya.devansh.tmdb.ui.base.Action;
import maurya.devansh.tmdb.ui.base.ActionPerformer;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.ui.home.movie.MovieAdapter;
import maurya.devansh.tmdb.utils.common.ApiUtils;

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
        viewModel.getMovieDetail(args.getMovieId());
    }

    @Override
    protected void setupObservers() {
        viewModel.movieDetailLiveData.observe(getViewLifecycleOwner(), movieDetail -> {
            if (movieDetail != null) {
                updateUi(movieDetail);
            }
        });
    }

    private void updateUi(MovieDetail data) {
        String posterUrl = ApiUtils.getTmdbImageUrl(data.posterPath);
        Glide.with(binding().ivPoster)
            .load(posterUrl)
            .into(binding().ivPoster);
        binding().tvTitle.setText(data.title);
    }

    @Override
    public void performAction(Action action) {
        if (action instanceof Action.MovieBookmarked) {
            Action.MovieBookmarked movieBookmarked = (Action.MovieBookmarked) action;
//            viewModel.bookmarkMovie(movieBookmarked.movie, movieBookmarked.isBookmarked);
            toast(movieBookmarked.movie.title + movieBookmarked.isBookmarked);
        }
    }
}