package maurya.devansh.tmdb.ui.home.movie;

import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

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

    MovieViewHolder(View itemView, ActionPerformer actionPerformer) {
        super(itemView, actionPerformer);
    }

    @Override
    protected ItemMovieBinding provideViewBinding() {
        return ItemMovieBinding.bind(itemView);
    }

    @Override
    public void bind(@NonNull Movie data) {
        Glide.with(binding.ivPoster)
                .load(ApiUtils.getTmdbImageUrl(data.getPosterPath()))
                .transition(DrawableTransitionOptions.withCrossFade(200))
                .into(binding.ivPoster);
        binding.tvTitle.setText(data.getTitle());
        binding.tvDate.setText(data.getReleaseDate());

        binding.buttonBookmark.setOnCheckedChangeListener(((buttonView, isChecked) ->
                performAction(new Action.MovieBookmarked(data, isChecked))
        ));
        binding.buttonBookmark.setChecked(data.bookmarked());
    }
}