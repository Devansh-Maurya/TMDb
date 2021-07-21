package maurya.devansh.tmdb.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.data.model.MovieDetail;
import maurya.devansh.tmdb.data.repository.MovieRepository;
import maurya.devansh.tmdb.ui.base.BaseViewModel;

/**
 * Created by devansh on 21/07/21.
 */

public class DetailViewModel extends BaseViewModel {

    private final MovieRepository movieRepository;

    public MediatorLiveData<MovieDetail> movieDetailLiveData = new MediatorLiveData<>();

    @Inject
    DetailViewModel(
        CompositeDisposable compositeDisposable,
        MovieRepository movieRepository
    ) {
        super(compositeDisposable);
        this.movieRepository = movieRepository;
        this.movieRepository.setCompositeDisposable(compositeDisposable);
    }

    public void getMovieDetail(int movieId) {
        LiveData<MovieDetail> liveData = movieRepository.getMovieDetail(movieId);
        movieDetailLiveData.addSource(liveData, movieDetailLiveData::setValue);
    }
}