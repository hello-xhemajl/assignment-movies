package helo.mali.movies.features.home.tvshow.repository.network;

import java.util.List;

import helo.mali.movies.features.home.tvshow.TvShow;

public class TvShowListNetworkResponse {
    private List<TvShow> items;
    private String errorMessage;

    public List<TvShow> getItems() {
        return items;
    }

    public void setItems(List<TvShow> items) {
        this.items = items;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
