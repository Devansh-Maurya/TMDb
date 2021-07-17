package maurya.devansh.tmdb.ui.base;

import androidx.annotation.CallSuper;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by devansh on 17/07/21.
 */

public class BaseViewModel extends ViewModel {

    private final CompositeDisposable _compositeDisposable;

    BaseViewModel(CompositeDisposable compositeDisposable) {
        _compositeDisposable = compositeDisposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return _compositeDisposable;
    }

    @Override
    @CallSuper
    protected void onCleared() {
        super.onCleared();
        _compositeDisposable.dispose();
    }
}
