package maurya.devansh.tmdb.ui.home;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.pagingdatasource.MoviePagingDataSource;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeViewModel extends BaseViewModel {

    public final LiveData<PagedList<Movie>> movieListLiveData;

    @Inject
    HomeViewModel(CompositeDisposable compositeDisposable, MovieRepository movieRepository) {
        super(compositeDisposable);

        MoviePagingDataSource.Factory factory = new MoviePagingDataSource.Factory(movieRepository, compositeDisposable);
        movieListLiveData = new LivePagedListBuilder<>(factory, MoviePagingDataSource.PAGE_SIZE).build();
    }
}
