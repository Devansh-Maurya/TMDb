package maurya.devansh.tmdb;

import android.app.Application;

import com.melegy.redscreenofdeath.RedScreenOfDeath;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import maurya.devansh.tmdb.di.component.DaggerTMDbAppComponent;
import maurya.devansh.tmdb.di.component.TMDbAppComponent;

/**
 * Created by devansh on 17/07/21.
 */

public class TMDbApp extends Application implements HasAndroidInjector {

    @Inject
    public DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseDaggerComponent();
        RedScreenOfDeath.init(this);
    }

    private void initialiseDaggerComponent() {
        TMDbAppComponent appComponent = DaggerTMDbAppComponent.builder().application(this).build();
        appComponent.inject(this);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
