package maurya.devansh.tmdb.di.module;

import android.app.Application;
import android.content.Context;

import com.naman14.spider.SpiderInterceptor;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.BuildConfig;
import maurya.devansh.tmdb.data.remote.AuthorizationInterceptor;
import maurya.devansh.tmdb.di.qualifier.ApplicationContext;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devansh on 17/07/21.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideContext(@NotNull Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
        return new OkHttpClient().newBuilder()
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(SpiderInterceptor.Companion.getInstance(context))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_TMDB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
