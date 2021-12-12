package helo.mali.movies.features.home.movie.repository.network;

import java.util.List;

import helo.mali.movies.features.home.movie.Movie;

public class MovieListNetworkResponse {
    private List<Movie> items;
    private String errorMessage;

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
