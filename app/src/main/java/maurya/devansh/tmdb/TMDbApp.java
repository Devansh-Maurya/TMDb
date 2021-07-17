package maurya.devansh.tmdb;

import android.app.Application;

import maurya.devansh.tmdb.di.component.DaggerTMDbAppComponent;

/**
 * Created by devansh on 17/07/21.
 */

public class TMDbApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initialiseDaggerComponent();
    }

    private void initialiseDaggerComponent() {
        DaggerTMDbAppComponent.builder().application(this).build();
    }
}
