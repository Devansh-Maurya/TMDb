package maurya.devansh.tmdb.ui.home.movie;

import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.databinding.ItemMovieBinding;
import maurya.devansh.tmdb.ui.base.BaseViewHolder;
import maurya.devansh.tmdb.utils.common.ApiUtils;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieViewHolder extends BaseViewHolder<Movie, ItemMovieBinding> {

    MovieViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected ItemMovieBinding provideViewBinding() {
        return ItemMovieBinding.bind(itemView);
    }

    @Override
    public void bind(@NonNull Movie data) {
        Glide.with(binding.ivPoster)
                .load(ApiUtils.getTmdbImageUrl(data.posterPath))
                .into(binding.ivPoster);
        binding.tvTitle.setText(data.title);
        binding.tvDate.setText(data.releaseDate);
    }
}
