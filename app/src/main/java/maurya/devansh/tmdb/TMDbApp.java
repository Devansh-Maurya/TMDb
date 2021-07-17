package maurya.devansh.tmdb;

import android.app.Application;

import com.melegy.redscreenofdeath.RedScreenOfDeath;

import maurya.devansh.tmdb.di.component.DaggerTMDbAppComponent;

/**
 * Created by devansh on 17/07/21.
 */

public class TMDbApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseDaggerComponent();
        RedScreenOfDeath.init(this);
    }

    private void initialiseDaggerComponent() {
        DaggerTMDbAppComponent.builder().application(this).build();
    }
}
