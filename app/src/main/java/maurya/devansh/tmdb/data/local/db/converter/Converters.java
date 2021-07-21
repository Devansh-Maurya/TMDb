package maurya.devansh.tmdb.data.local.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import maurya.devansh.tmdb.data.model.Genre;

/**
 * Created by devansh on 21/07/21.
 */

public class Converters {

    @TypeConverter
    public static List<Genre> fromGenreJson(String value) {
        Type listType = new TypeToken<List<Genre>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromGenreList(List<Genre> list) {
        return new Gson().toJson(list);
    }
}