package maurya.devansh.tmdb.data.model;

import androidx.room.Entity;

/**
 * Created by devansh on 19/07/21.
 */

@Entity(tableName = "trending_movie")
public class TrendingMovie extends MovieId {

    public TrendingMovie(int id, int page, int listPosition) {
        super(id, page, listPosition);
    }
}