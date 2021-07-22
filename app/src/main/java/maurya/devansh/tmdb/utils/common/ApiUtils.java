package maurya.devansh.tmdb.utils.common;

import androidx.annotation.NonNull;

/**
 * Created by devansh on 18/07/21.
 */

public class ApiUtils {

    /**
     * This may change in future. Advisable will be to use /configuration API
     * Source: https://developers.themoviedb.org/3/getting-started/images
     * */
    @NonNull
    public static String getTmdbImageUrl(String imagePath) {
        return "https://image.tmdb.org/t/p/w500" + imagePath;
    }
}
