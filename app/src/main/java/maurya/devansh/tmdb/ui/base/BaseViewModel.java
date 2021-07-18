package maurya.devansh.tmdb.ui.base;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by devansh on 17/07/21.
 */

public abstract class BaseViewModel extends ViewModel {

    protected final CompositeDisposable compositeDisposable;

    protected BaseViewModel(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    @CallSuper
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
