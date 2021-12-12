package helo.mali.movies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import helo.mali.movies.features.home.movie.Movie;
import helo.mali.movies.features.home.movie.repository.database.MovieDao;
import helo.mali.movies.features.home.tvshow.TvShow;
import helo.mali.movies.features.home.tvshow.repository.database.TvShowDao;


@Database(entities = {Movie.class, TvShow.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return sInstance;
    }

    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();
}