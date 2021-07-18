package maurya.devansh.tmdb.ui.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by devansh on 18/07/21.
 */

public abstract class BaseViewHolder<D, VB> extends RecyclerView.ViewHolder {

    protected final VB binding;

    public BaseViewHolder(View itemView) {
        super(itemView);
        binding = provideViewBinding();
    }

    public abstract void bind(D data);

    protected abstract VB provideViewBinding();
}
