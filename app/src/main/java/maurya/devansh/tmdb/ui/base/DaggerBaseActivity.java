package maurya.devansh.tmdb.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewbinding.ViewBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by devansh on 22/07/21.
 */

public abstract class DaggerBaseActivity<VM extends BaseViewModel, VB extends ViewBinding> extends BaseActivity<VB> {

    protected VM viewModel;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        Pair<ViewModelStoreOwner, Class<VM>> vmCreators = provideViewModelCreators();
        viewModel = (new ViewModelProvider(vmCreators.first, viewModelFactory))
            .get(vmCreators.second);
        super.onCreate(savedInstanceState);
    }

    protected abstract Pair<ViewModelStoreOwner, Class<VM>> provideViewModelCreators();
}
