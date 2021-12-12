package helo.mali.movies.features.home.movie.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import helo.mali.movies.features.home.movie.Movie;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie>  movies);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movies ORDER BY rank")
    LiveData<List<Movie>> getAllOrderByRank();

    @Query("SELECT * FROM movies ORDER BY title")
    LiveData<List<Movie>> getAllOrderByTitle();

    @Query("SELECT * FROM movies WHERE id=:id")
    LiveData<Movie> getById(String id);
}
