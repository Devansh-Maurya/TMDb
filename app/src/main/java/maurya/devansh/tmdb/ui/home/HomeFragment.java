package maurya.devansh.tmdb.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.model.Tab;
import maurya.devansh.tmdb.databinding.FragmentHomeBinding;
import maurya.devansh.tmdb.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    @Override
    protected FragmentHomeBinding provideViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentHomeBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupView(@NonNull @NotNull View view) {
        List<Tab> tabs = Arrays.asList(
            new Tab(MoviesList.TYPE_TRENDING, "Trending"),
            new Tab(MoviesList.TYPE_NOW_PLAYING, "Now Playing")
        );
        binding().viewPager.setAdapter(new HomeViewPagerAdapter(this, tabs));
        new TabLayoutMediator(binding().tabLayout, binding().viewPager, (tab, position) ->
            tab.setText(tabs.get(position).title)
        ).attach();
    }
}