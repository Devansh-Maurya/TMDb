package maurya.devansh.tmdb.ui.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import maurya.devansh.tmdb.data.model.Tab;

/**
 * Created by devansh on 18/07/21.
 */

public class HomeViewPagerAdapter extends FragmentStateAdapter {

    private final List<Tab> tabs;

    public HomeViewPagerAdapter(Fragment fragment, List<Tab> tabs) {
        super(fragment);
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HomeListFragment.newInstance(tabs.get(position).id);
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }
}
