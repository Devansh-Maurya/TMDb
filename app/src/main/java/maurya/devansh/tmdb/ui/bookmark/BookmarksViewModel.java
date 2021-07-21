package maurya.devansh.tmdb.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.pagingdatasource.MoviePagingDataSource;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 18/07/21.
 */

public class BookmarksViewModel extends BaseViewModel {

    public final LiveData<PagedList<Movie>> movieListLiveData;

    @Inject
    BookmarksViewModel(
            CompositeDisposable compositeDisposable,
            MovieRepository movieRepository
    ) {
        super(compositeDisposable);

        DataSource.Factory<Integer, Movie> factory = movieRepository.getBookmarkedMovies();
        movieListLiveData = new LivePagedListBuilder<>(factory, MoviePagingDataSource.PAGE_SIZE).build();
    }
}
