package maurya.devansh.tmdb.di.module;

import android.app.Application;
import android.content.Context;

import com.naman14.spider.SpiderInterceptor;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
                .addInterceptor(SpiderInterceptor.Companion.getInstance(context))
                .build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
