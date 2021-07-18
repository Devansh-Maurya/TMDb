package maurya.devansh.tmdb.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by devansh on 18/07/21.
 */

public abstract class BaseViewHolder<D, VB> extends RecyclerView.ViewHolder {

    protected final VB binding;
    private ActionPerformer actionPerformer = null;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = provideViewBinding();
    }

    public BaseViewHolder(@NonNull View itemView, ActionPerformer actionPerformer) {
        this(itemView);
        this.actionPerformer = actionPerformer;
    }

    public abstract void bind(D data);

    protected abstract VB provideViewBinding();

    protected void performAction(Action action) {
        if (actionPerformer != null) {
            actionPerformer.performAction(action);
        }
    }
}
