package maurya.devansh.tmdb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;
import maurya.devansh.tmdb.utils.common.MoviesListType;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeListViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    public LiveData<PagingData<Movie>> movieListLiveData = null;

    @Inject
    HomeListViewModel(
            CompositeDisposable compositeDisposable,
            MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public LiveData<PagingData<Movie>> getMovies(@MoviesListType int movieListType) {
        if (movieListLiveData == null) {
            movieListLiveData = movieRepository.getMoviesList(movieListType);
        }
        return movieListLiveData;
    }

    public void bookmarkMovie(Movie movie, boolean isBookmarked) {
        compositeDisposable.add(movieRepository.bookmarkMovie(movie, isBookmarked)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        );
    }
}
