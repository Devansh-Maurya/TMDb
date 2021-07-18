package maurya.devansh.tmdb.data.model;

import androidx.annotation.Keep;

/**
 * Created by devansh on 18/07/21.
 */

@Keep
public class Tab {
    public final int id;
    public final String title;

    public Tab(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
