package maurya.devansh.tmdb.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 18/07/21.
 */

public class BookmarksViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    @Inject
    BookmarksViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public LiveData<PagingData<Movie>> getBookmarkedMovies() {
        LiveData<PagingData<Movie>> pagingDataLiveData = movieRepository.getBookmarkedMovies();
        PagingLiveData.cachedIn(pagingDataLiveData, ViewModelKt.getViewModelScope(this));
        return pagingDataLiveData;
    }
}
