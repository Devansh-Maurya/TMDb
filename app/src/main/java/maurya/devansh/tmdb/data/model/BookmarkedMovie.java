package maurya.devansh.tmdb.data.model;

import androidx.room.Entity;

/**
 * Created by devansh on 19/07/21.
 */

@Entity(tableName = "bookmarked_movie")
public class BookmarkedMovie extends MovieId {

    public BookmarkedMovie(int id) {
        super(id);
    }
}