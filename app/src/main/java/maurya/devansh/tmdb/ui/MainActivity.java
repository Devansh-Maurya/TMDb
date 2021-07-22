package maurya.devansh.tmdb.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.databinding.ActivityMainBinding;
import maurya.devansh.tmdb.ui.base.DaggerBaseActivity;

public class MainActivity extends DaggerBaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.navHost);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        navController.addOnDestinationChangedListener(((controller, destination, arguments) -> {
            if (destination.getId() == R.id.detailFragment) {
                getWindow().setStatusBarColor(getColorFromAttribute(android.R.attr.colorBackground));
                binding.bottomNavigation.setVisibility(View.GONE);
            } else {
                getWindow().setStatusBarColor(getColorFromAttribute(R.attr.colorPrimaryVariant));
                binding.bottomNavigation.setVisibility(View.VISIBLE);
            }
        }));
    }

    @Override
    protected Pair<ViewModelStoreOwner, Class<MainViewModel>> provideViewModelCreators() {
        return new Pair<>(this, MainViewModel.class);
    }

    @Override
    protected ActivityMainBinding provideViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

    }

    @ColorInt
    private int getColorFromAttribute(@AttrRes int res) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(res, typedValue, true);
        return typedValue.data;
    }
}