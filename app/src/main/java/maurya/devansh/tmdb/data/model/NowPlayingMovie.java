package maurya.devansh.tmdb.data.model;

import androidx.room.Entity;

/**
 * Created by devansh on 19/07/21.
 */

@Entity(tableName = "now_playing_movie")
public class NowPlayingMovie extends MovieId {

    public NowPlayingMovie(int id, int page, int listPosition) {
        super(id, page, listPosition);
    }
}