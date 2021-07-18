package maurya.devansh.tmdb.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

/**
 * Created by devansh on 17/07/21.
 */

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    @Nullable
    private VB binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = provideViewBinding(inflater, container);
        setupObservers();
        return binding != null ? binding.getRoot() : null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected abstract VB provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract void setupView(@NonNull View view);

    protected void setupObservers() {
    }

    @NonNull
    protected VB getBinding() {
        if (binding == null)
            throw new IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.");
        return binding;
    }

    protected void toast(@NonNull String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(@StringRes int resId) {
        Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
