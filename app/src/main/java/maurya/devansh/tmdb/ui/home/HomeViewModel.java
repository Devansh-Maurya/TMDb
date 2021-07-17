package maurya.devansh.tmdb.ui.home;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeViewModel extends BaseViewModel {

    @Inject
    HomeViewModel(CompositeDisposable compositeDisposable) {
        super(compositeDisposable);
    }
}
