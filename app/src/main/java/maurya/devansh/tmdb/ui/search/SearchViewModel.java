package maurya.devansh.tmdb.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 21/07/21.
 */

public class SearchViewModel extends BaseViewModel {

    /**
     * Time delay to fetch suggestions while user is typing the search keywords.
     */
    private static final long TYPING_DELAY = 300;

    private final MovieRepository movieRepository;
    private final PublishSubject<String> searchPublishSubject = PublishSubject.create();

    @Inject
    SearchViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public void setupSearchObserver(Consumer<LiveData<PagingData<Movie>>> consumer) {
        compositeDisposable.add(searchPublishSubject
            .debounce(TYPING_DELAY, TimeUnit.MILLISECONDS)
            .filter(query -> !query.isEmpty())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(pagingData -> {
                LiveData<PagingData<Movie>> pagingDataLiveData = movieRepository.searchMovies(pagingData);
                // TODO: 21/07/21 Check if this works. If yes, how? Issue with rotating device
                PagingLiveData.cachedIn(pagingDataLiveData, ViewModelKt.getViewModelScope(this));
                consumer.accept(pagingDataLiveData);
            }, throwable -> {})
        );
    }

    public void getSearchResults(String query) {
        searchPublishSubject.onNext(query);
    }
}