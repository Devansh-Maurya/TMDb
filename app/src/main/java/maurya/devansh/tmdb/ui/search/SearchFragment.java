package maurya.devansh.tmdb.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import maurya.devansh.tmdb.databinding.FragmentSearchBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;

public class SearchFragment extends DaggerBaseFragment<SearchViewModel, FragmentSearchBinding> {

    @Override
    protected Pair<ViewModelStoreOwner, Class<SearchViewModel>> provideViewModelCreators() {
        return new Pair<>(this, SearchViewModel.class);
    }

    @Override
    protected FragmentSearchBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {
    }
}