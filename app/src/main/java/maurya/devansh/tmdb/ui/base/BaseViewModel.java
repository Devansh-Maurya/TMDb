package maurya.devansh.tmdb.ui.base;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by devansh on 17/07/21.
 */

public class BaseViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable;

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
