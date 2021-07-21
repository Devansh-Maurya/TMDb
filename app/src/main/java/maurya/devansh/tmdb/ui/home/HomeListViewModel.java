package maurya.devansh.tmdb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.di.qualifier.MoviesListTypeQualifier;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeListViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    public final LiveData<PagingData<Movie>> movieListLiveData;

    // FIXME: 18/07/21 How to pass other movie list type? Ask someone
    @Inject
    HomeListViewModel(
            CompositeDisposable compositeDisposable,
            MovieRepository movieRepository,
            @MoviesListTypeQualifier(MoviesList.TYPE_TRENDING) int moviesListType
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
        movieListLiveData = movieRepository.getMoviesList(moviesListType);
    }

    public void bookmarkMovie(Movie movie, boolean isBookmarked) {
        compositeDisposable.add(movieRepository.bookmarkMovie(movie, isBookmarked)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe()
        );
    }
}
