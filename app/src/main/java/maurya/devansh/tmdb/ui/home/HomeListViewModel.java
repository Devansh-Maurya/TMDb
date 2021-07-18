package maurya.devansh.tmdb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.pagingdatasource.MoviePagingDataSource;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.di.qualifier.MoviesListTypeQualifier;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeListViewModel extends BaseViewModel {

    public final LiveData<PagedList<Movie>> movieListLiveData;

    // FIXME: 18/07/21 How to pass other movie list type? Ask someone
    @Inject
    HomeListViewModel(
            CompositeDisposable compositeDisposable,
            MovieRepository movieRepository,
            @MoviesListTypeQualifier(MoviesList.TYPE_TRENDING) int moviesListType
    ) {
        super(compositeDisposable);

        MoviePagingDataSource.Factory factory =
                new MoviePagingDataSource.Factory(movieRepository, compositeDisposable, moviesListType);
        movieListLiveData = new LivePagedListBuilder<>(factory, MoviePagingDataSource.PAGE_SIZE).build();
    }
}
