package maurya.devansh.tmdb.ui.home.movie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.ui.base.ActionPerformer;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieAdapter extends PagingDataAdapter<Movie, MovieViewHolder> {

    private final ActionPerformer actionPerformer;
    private final boolean showBookmarkIcon;

    public MovieAdapter(ActionPerformer actionPerformer, boolean showBookmarkIcon) {
        super(Movie.DIFF_CALLBACK);
        this.actionPerformer = actionPerformer;
        this.showBookmarkIcon = showBookmarkIcon;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false),
            actionPerformer,
            showBookmarkIcon
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie data = getItem(position);
        if (data != null) {
            holder.bind(data);
        }
    }
}
