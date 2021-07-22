package maurya.devansh.tmdb.ui.detail;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MovieDetail;
import maurya.devansh.tmdb.databinding.FragmentDetailBinding;
import maurya.devansh.tmdb.ui.MainViewModel;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;
import maurya.devansh.tmdb.utils.common.ApiUtils;

public class DetailFragment extends DaggerBaseFragment<DetailViewModel, FragmentDetailBinding> {

    private MainViewModel mainViewModel;

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
        mainViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel.class);

        DetailFragmentArgs args = DetailFragmentArgs.fromBundle(getArguments());
        viewModel.getMovieDetail(args.getMovieId());
    }

    @Override
    protected void setupObservers() {
        viewModel.movieDetailLiveData.observe(getViewLifecycleOwner(), movieDetail -> {
            if (movieDetail != null) {
                if (movieDetail.isDummy()) {
                    toast(R.string.something_went_wrong);
                } else {
                    updateUi(movieDetail);
                }
            }
        });
    }

    private void updateUi(MovieDetail data) {
        binding().groupLayout.setVisibility(View.VISIBLE);

        String posterUrl = ApiUtils.getTmdbImageUrl(data.posterPath);
        Glide.with(binding().ivPoster)
            .load(posterUrl)
            .into(binding().ivPoster);
        binding().tvTitle.setText(data.title);
        binding().tvInfo.setText(data.getInfoString());
        binding().tvOverview.setText(data.overview);
        binding().ivBookmark.setChecked(data.isBookmarked());

        binding().ivBack.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
        binding().ivShare.setOnClickListener(v -> shareMovie(data));
        binding().ivBookmark.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            Movie movie = new Movie(data.id, data.posterPath, data.releaseDate, data.title, data.originalLanguage, data.id);
            mainViewModel.bookmarkMovie(movie, isChecked);
        }));
    }

    private void shareMovie(MovieDetail data) {
        viewModel.getShareableViewImageUri(binding().ivPoster).observe(getViewLifecycleOwner(), path -> {
            String shareText = String.format(
                "Checkout *%s* and other movies at the TMDb app! Click the link below:\n%s",
                data.title, "tmdbapp://movie/detail/" + data.id
            );
            new ShareCompat.IntentBuilder(binding().getRoot().getContext())
                .setText(shareText)
                .setType("image/*")
                .setStream(Uri.parse(path))
                .startChooser();
        });
    }
}