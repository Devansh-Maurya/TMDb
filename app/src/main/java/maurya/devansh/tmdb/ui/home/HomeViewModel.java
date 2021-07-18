package maurya.devansh.tmdb.ui.home;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 17/07/21.
 */

public class HomeViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    @Inject
    HomeViewModel(CompositeDisposable compositeDisposable, MovieRepository movieRepository) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
    }

    public void getTrendingMovies() {
        compositeDisposable.add(movieRepository.getTrendingMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moviesList -> {
                    Log.d("Trending", "getTrendingMovies: " + moviesList.toString());
                }, throwable -> {
                    Log.e("Trending_error", "getTrendingMovies: " + throwable.toString());
                })
        );

    }
}
