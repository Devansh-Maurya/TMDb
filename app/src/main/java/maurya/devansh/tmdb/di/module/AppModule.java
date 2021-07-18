package maurya.devansh.tmdb.di.module;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.naman14.spider.SpiderInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import maurya.devansh.tmdb.BuildConfig;
import maurya.devansh.tmdb.data.remote.AuthorizationInterceptor;
import maurya.devansh.tmdb.data.remote.NetworkService;
import maurya.devansh.tmdb.di.qualifier.ApplicationContext;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by devansh on 17/07/21.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideContext(@NonNull Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(SpiderInterceptor.Companion.getInstance(context))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_TMDB_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    public NetworkService provideNetworkService(@NonNull Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
}
