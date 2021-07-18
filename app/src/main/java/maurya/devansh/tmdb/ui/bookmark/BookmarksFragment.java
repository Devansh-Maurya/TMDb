package maurya.devansh.tmdb.ui.bookmark;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;

import org.jetbrains.annotations.NotNull;

import maurya.devansh.tmdb.databinding.FragmentBookmarksBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseFragment;

public class BookmarksFragment extends DaggerBaseFragment<BookmarksViewModel, FragmentBookmarksBinding> {

    @Override
    protected Pair<ViewModelStoreOwner, Class<BookmarksViewModel>> provideViewModelCreators() {
        return new Pair<>(this, BookmarksViewModel.class);
    }

    @Override
    protected FragmentBookmarksBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentBookmarksBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {

    }
}