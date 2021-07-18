package maurya.devansh.tmdb.ui.bookmark;

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
 * Created by devansh on 18/07/21.
 */

public class BookmarksViewModel extends BaseViewModel {

    public final LiveData<PagedList<Movie>> movieListLiveData;

    // FIXME: 18/07/21 Correct type
    @Inject
    BookmarksViewModel(
            CompositeDisposable compositeDisposable,
            MovieRepository movieRepository,
            @MoviesListTypeQualifier(MoviesList.TYPE_NOW_PLAYING) int moviesListType
    ) {
        super(compositeDisposable);

        MoviePagingDataSource.Factory factory =
                new MoviePagingDataSource.Factory(movieRepository, compositeDisposable, moviesListType);
        movieListLiveData = new LivePagedListBuilder<>(factory, MoviePagingDataSource.PAGE_SIZE).build();
    }
}
