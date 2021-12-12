package helo.mali.movies.features.home.movie.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import helo.mali.movies.features.home.movie.Movie;
import helo.mali.movies.features.home.movie.repository.MovieRepository;

public class MovieViewModel extends ViewModel {
    public static final int ORDER_BY_TITLE = 1;
    public static final int ORDER_BY_RANK= 2;

    private MovieRepository movieRepository;

    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getMovies() {
        return Transformations.switchMap(getOrder(), order -> {
            if(order == ORDER_BY_TITLE) {
                return movieRepository.getMoviesOrderedByTitle();
            } else
                return movieRepository.getMoviesOrderedByRank();
        });
    }

    public LiveData<Movie> getMovie(String movieId) {
        return movieRepository.getMovie(movieId);
    }

    private MutableLiveData<Integer> order;

    private LiveData<Integer> getOrder() {
        if(order == null) {
            order = new MutableLiveData<>();
            // Default is order by rank
            order.setValue(ORDER_BY_RANK);
        }

        return  order;
    }

    public void onOrderByChange(int orderBy) {
        order.setValue(orderBy);
    }
}
