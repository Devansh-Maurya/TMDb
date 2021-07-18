package maurya.devansh.tmdb.data.remote;

import io.reactivex.Observable;
import maurya.devansh.tmdb.data.model.MoviesList;
import retrofit2.http.GET;

/**
 * Created by devansh on 18/07/21.
 */

// No leading "/" in relative URLs as there is the path "3" after host URL in base API URL
public interface NetworkService {

    @GET("trending/movie/day")
    Observable<MoviesList> getTrendingMovies();
}
