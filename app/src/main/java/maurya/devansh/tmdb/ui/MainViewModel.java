package maurya.devansh.tmdb.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 22/07/21.
 */

public class MainViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;
    private final MutableLiveData<Movie> bookmarkMovieLiveData = new MutableLiveData<>();

    @Inject
    MainViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public void bookmarkMovie(Movie movie, boolean isBookmarked) {
        compositeDisposable.add(movieRepository.bookmarkMovie(movie, isBookmarked)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(() -> bookmarkMovieLiveData.postValue(movie), throwable -> {})
        );
    }

    public LiveData<Movie> getBookmarkMovieLiveData() {
        return bookmarkMovieLiveData;
    }
}
