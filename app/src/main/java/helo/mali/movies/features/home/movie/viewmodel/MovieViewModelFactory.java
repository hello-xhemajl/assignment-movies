package helo.mali.movies.features.home.movie.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import helo.mali.movies.Application;
import helo.mali.movies.database.AppDatabase;
import helo.mali.movies.features.home.movie.repository.MovieRepository;
import helo.mali.movies.features.home.movie.repository.database.MovieDao;
import helo.mali.movies.features.home.movie.repository.network.MovieNetwork;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
    private MovieRepository movieRepository;

    public MovieViewModelFactory() {
        MovieNetwork movieNetwork = new MovieNetwork();
        MovieDao movieDao = AppDatabase.getInstance(Application.getInstance()).movieDao();

        this.movieRepository = new MovieRepository(movieNetwork, movieDao);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(movieRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
