package maurya.devansh.tmdb.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import javax.inject.Singleton;

import maurya.devansh.tmdb.data.local.db.converter.Converters;
import maurya.devansh.tmdb.data.local.db.dao.MovieDao;
import maurya.devansh.tmdb.data.local.db.dao.MovieRemoteKeyDao;
import maurya.devansh.tmdb.data.model.BookmarkedMovie;
import maurya.devansh.tmdb.data.model.Movie;
import maurya.devansh.tmdb.data.model.MovieDetail;
import maurya.devansh.tmdb.data.model.MovieRemoteKey;
import maurya.devansh.tmdb.data.model.NowPlayingMovie;
import maurya.devansh.tmdb.data.model.TrendingMovie;

/**
 * Created by devansh on 18/07/21.
 */

@Singleton
@Database(
    entities = {Movie.class, BookmarkedMovie.class, TrendingMovie.class, NowPlayingMovie.class, MovieRemoteKey.class, MovieDetail.class},
    version = 1,
    exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class DatabaseService extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract MovieRemoteKeyDao movieRemoteKeyDao();
}
