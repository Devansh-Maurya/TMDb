package maurya.devansh.tmdb.ui.home.movie;

import android.view.View;

import androidx.annotation.NonNull;

import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.databinding.ItemMovieBinding;
import maurya.devansh.tmdb.ui.base.BaseViewHolder;

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
        binding.tvTitle.setText(data.title);
    }
}
