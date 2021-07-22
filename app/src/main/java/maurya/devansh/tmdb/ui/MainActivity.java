package maurya.devansh.tmdb.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.databinding.ActivityMainBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseActivity;

public class MainActivity extends DaggerBaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected Pair<ViewModelStoreOwner, Class<MainViewModel>> provideViewModelCreators() {
        return new Pair<>(this, MainViewModel.class);
    }

    @Override
    protected ActivityMainBinding provideViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupView(@Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(this, R.id.navHost);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        navController.addOnDestinationChangedListener(((controller, destination, arguments) -> {
            if (destination.getId() == R.id.detailFragment) {
                getWindow().setStatusBarColor(getColorFromAttribute(android.R.attr.colorBackground));
                binding.bottomNavigation.setVisibility(View.GONE);
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        getWindow().setStatusBarColor(getColorFromAttribute(R.attr.colorPrimaryVariant));
                        binding.bottomNavigation.setVisibility(View.VISIBLE);
                    }
                }, getResources().getInteger(android.R.integer.config_mediumAnimTime));
            }
        }));
    }

    @Override
    protected void setupObservers() {
        viewModel.getBookmarkMovieLiveData().observe(this, movie -> {
            if (movie.isBookmarked()) {
                toast("Bookmarked " + movie.title);
            } else {
                toast("Bookmark removed");
            }
        });
    }

    @ColorInt
    private int getColorFromAttribute(@AttrRes int res) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(res, typedValue, true);
        return typedValue.data;
    }
}