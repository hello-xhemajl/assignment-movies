package helo.mali.movies.features.home.movie.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

import java.util.List;

import helo.mali.movies.Application;
import helo.mali.movies.database.AppExecutors;
import helo.mali.movies.features.home.movie.Movie;
import helo.mali.movies.features.home.movie.repository.database.MovieDao;
import helo.mali.movies.features.home.movie.repository.network.MovieNetwork;
import helo.mali.movies.network.ResponseListener;

public class MovieRepository {

    private MovieNetwork movieNetwork;
    private MovieDao movieDao;

    public MovieRepository(MovieNetwork movieNetwork, MovieDao movieDao) {
        this.movieNetwork = movieNetwork;
        this.movieDao = movieDao;
    }

    public LiveData<List<Movie>> getMoviesOrderedByTitle() {

        if (isConnectedOrConnecting()) {
            // If connected, request movies from network and update database
            requestMoviesAndUpdateDatabase();
        }

        // In any case return the movies in database(single source of truth)
        return movieDao.getAllOrderByTitle();
    }

    public LiveData<List<Movie>> getMoviesOrderedByRank() {

        if (isConnectedOrConnecting()) {
            requestMoviesAndUpdateDatabase();
        }

        return movieDao.getAllOrderByRank();
    }

    public LiveData<Movie> getMovie(String movieId) {

        if (isConnectedOrConnecting()) {
            requestMoviesAndUpdateDatabase();
        }

        return movieDao.getById(movieId);
    }

    private void requestMoviesAndUpdateDatabase() {
        movieNetwork.requestMovies(new ResponseListener<List<Movie>>() {
            @Override
            public void onResponse(List<Movie> response) {
                updateMoviesInDatabase(response);
            }

            @Override
            public void onErrorResponse() {
            }
        });
    }

    private void updateMoviesInDatabase(List<Movie> movies) {
        AppExecutors.getInstance().diskIO().execute(() ->
                movieDao.insertAll(movies)
        );
    }

    public boolean isConnectedOrConnecting() {
        ConnectivityManager cm =
                (ConnectivityManager) Application.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
