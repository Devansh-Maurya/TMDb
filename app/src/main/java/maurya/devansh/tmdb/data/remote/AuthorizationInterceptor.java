package maurya.devansh.tmdb.data.remote;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import maurya.devansh.tmdb.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by devansh on 17/07/21.
 */

public class AuthorizationInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer: " + BuildConfig.TMDB_API_READ_ACCESS_TOKEN)
                .build();
        return chain.proceed(newRequest);
    }
}
