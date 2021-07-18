package maurya.devansh.tmdb.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.model.Movie;
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

        MoviePagingDataSourceFactory factory = new MoviePagingDataSourceFactory(movieRepository, compositeDisposable);
        movieListLiveData = new LivePagedListBuilder<>(factory, MoviePagingDataSource.PAGE_SIZE).build();
    }

    private static class MoviePagingDataSourceFactory extends DataSource.Factory<Integer, Movie> {

        private final MovieRepository movieRepository;
        private final CompositeDisposable compositeDisposable;

        MoviePagingDataSourceFactory(MovieRepository movieRepository, CompositeDisposable compositeDisposable) {
            this.movieRepository = movieRepository;
            this.compositeDisposable = compositeDisposable;
        }

        @NonNull
        @Override
        public DataSource<Integer, Movie> create() {
            return new MoviePagingDataSource(movieRepository, compositeDisposable);
        }
    }

    private static class MoviePagingDataSource extends PageKeyedDataSource<Integer, Movie> {

        public static final int STARTING_PAGE = 1;
        public static final int PAGE_SIZE = 20;

        private final MovieRepository movieRepository;
        private final CompositeDisposable compositeDisposable;

        MoviePagingDataSource(MovieRepository movieRepository, CompositeDisposable compositeDisposable) {
            this.movieRepository = movieRepository;
            this.compositeDisposable = compositeDisposable;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
            getData(STARTING_PAGE, movies -> callback.onResult(movies, null, STARTING_PAGE + 1));
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
            getData(params.key, movies -> callback.onResult(movies, params.key + 1));
        }

        private void getData(final int page, final Consumer<List<Movie>> callback) {
            compositeDisposable.add(movieRepository.getTrendingMovies(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(moviesList -> {
                        if (moviesList != null && !moviesList.results.isEmpty()) {
                            callback.accept(moviesList.results);
                        }
                    }, throwable -> {
                        Log.e("Trending_error", "getTrendingMovies: " + throwable.toString());
                    })
            );
        }
    }
}
