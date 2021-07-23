package maurya.devansh.tmdb.ui.movie;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import maurya.devansh.tmdb.NavGraphMainDirections;
import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.databinding.ItemMovieBinding;
import maurya.devansh.tmdb.ui.base.Action;
import maurya.devansh.tmdb.ui.base.ActionPerformer;
import maurya.devansh.tmdb.ui.base.BaseViewHolder;
import maurya.devansh.tmdb.utils.common.ApiUtils;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieViewHolder extends BaseViewHolder<Movie, ItemMovieBinding> {

    private final boolean showBookmarkIcon;

    MovieViewHolder(View itemView, ActionPerformer actionPerformer, boolean showBookmarkIcon) {
        super(itemView, actionPerformer);
        this.showBookmarkIcon = showBookmarkIcon;
    }

    @Override
    protected ItemMovieBinding provideViewBinding() {
        return ItemMovieBinding.bind(itemView);
    }

    @Override
    public void bind(@NonNull Movie data) {
        String posterUrl = ApiUtils.getTmdbImageUrl(data.posterPath);
        Glide.with(binding.ivPoster)
            .load(posterUrl)
            .transition(DrawableTransitionOptions.withCrossFade(200))
            .apply(new RequestOptions().error(R.drawable.ic_movie_filled))
            .into(binding.ivPoster);
        binding.tvTitle.setText(data.title);
        binding.tvDate.setText(data.releaseDate);

        binding.buttonBookmark.setVisibility(showBookmarkIcon ? View.VISIBLE : View.GONE);
        binding.buttonBookmark.setOnCheckedChangeListener(null);
        binding.buttonBookmark.setChecked(data.isBookmarked());
        binding.buttonBookmark.setOnCheckedChangeListener(((buttonView, isChecked) ->
            performAction(new Action.MovieBookmarked(data, isChecked))
        ));

        binding.cardView.setOnClickListener(view -> {
            NavDirections action = NavGraphMainDirections.actionOpenDetailScreen(data.id);
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });
    }
}