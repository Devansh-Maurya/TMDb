package maurya.devansh.tmdb.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by devansh on 17/07/21.
 */

public abstract class DaggerBaseFragment<VM extends BaseViewModel, VB extends ViewBinding> extends BaseFragment<VB> {

    protected VM viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = provideViewModel();
    }

    protected abstract VM provideViewModel();
}
