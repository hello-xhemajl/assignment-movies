package helo.mali.movies.features.home.tvshow.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import helo.mali.movies.features.home.tvshow.TvShow;

@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TvShow> tvShows);

    @Delete
    void delete(TvShow tvShow);

    @Query("SELECT * FROM tvshows ORDER BY rank")
    LiveData<List<TvShow>> getAllOrderByRank();

    @Query("SELECT * FROM tvshows ORDER BY title")
    LiveData<List<TvShow>> getAllOrderByTitle();

    @Query("SELECT * FROM tvshows WHERE id=:id")
    LiveData<TvShow> getById(String id);
}
