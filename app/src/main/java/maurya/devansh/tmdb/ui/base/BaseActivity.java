package maurya.devansh.tmdb.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

/**
 * Created by devansh on 17/07/21.
 */

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = provideViewBinding();
        setContentView(binding.getRoot());

        setupObservers();
        setupView(savedInstanceState);
    }

    protected abstract VB provideViewBinding();

    protected abstract void setupView(@Nullable Bundle savedInstanceState);

    protected void setupObservers() {
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void toast(@StringRes int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
}
