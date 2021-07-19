package maurya.devansh.tmdb.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import javax.inject.Singleton;

import maurya.devansh.tmdb.data.local.db.dao.MovieDao;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MovieId;

/**
 * Created by devansh on 18/07/21.
 */

@Singleton
@Database(entities = {Movie.class, MovieId.class}, version = 1, exportSchema = false)
public abstract class DatabaseService extends RoomDatabase {

    public abstract MovieDao movieDao();
}
