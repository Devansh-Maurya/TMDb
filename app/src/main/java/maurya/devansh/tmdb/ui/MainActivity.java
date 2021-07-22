package maurya.devansh.tmdb.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import maurya.devansh.tmdb.R;
import maurya.devansh.tmdb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
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

    @ColorInt
    private int getColorFromAttribute(@AttrRes int res) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(res, typedValue, true);
        return typedValue.data;
    }
}