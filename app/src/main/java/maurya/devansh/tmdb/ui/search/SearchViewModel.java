package maurya.devansh.tmdb.ui.search;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 21/07/21.
 */

public class SearchViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    @Inject
    SearchViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public LiveData<PagingData<Movie>> searchMovies(String query) {
        return movieRepository.searchMovies(query);
    }
}