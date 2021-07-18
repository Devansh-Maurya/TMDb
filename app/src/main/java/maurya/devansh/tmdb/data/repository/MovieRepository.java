package maurya.devansh.tmdb.data.repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import maurya.devansh.tmdb.data.model.MoviesList;
import maurya.devansh.tmdb.data.remote.NetworkService;

/**
 * Created by devansh on 18/07/21.
 */

public class MovieRepository {

    private final NetworkService networkService;

    @Inject
    MovieRepository(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Observable<MoviesList> getTrendingMovies() {
        return networkService.getTrendingMovies();
    }
}
