package maurya.devansh.tmdb.data.remote;

import io.reactivex.Single;
import maurya.devansh.tmdb.data.model.MoviesList;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by devansh on 18/07/21.
 */

// No leading "/" in relative URLs as there is the path "3" after host URL in base API URL
public interface NetworkService {

    @GET("trending/movie/day")
    Single<MoviesList> getTrendingMovies(@Query("page") int page);

    @GET("movie/now_playing")
    Single<MoviesList> getNowPlayingMovies(@Query("page") int page, @Query("region") String region);
}
