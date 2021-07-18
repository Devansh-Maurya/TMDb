package maurya.devansh.tmdb.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import maurya.devansh.tmdb.databinding.FragmentHomeBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;

public class HomeFragment extends DaggerBaseFragment<HomeViewModel, FragmentHomeBinding> {

    @Override
    protected Pair<ViewModelStoreOwner, Class<HomeViewModel>> provideViewModelCreators() {
        return new Pair<>(this, HomeViewModel.class);
    }

    @Override
    protected FragmentHomeBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {
        viewModel.getTrendingMovies();
    }
}