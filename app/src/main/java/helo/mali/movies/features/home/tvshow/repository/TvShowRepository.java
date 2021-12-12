package helo.mali.movies.features.home.tvshow.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

import java.util.List;

import helo.mali.movies.Application;
import helo.mali.movies.database.AppExecutors;
import helo.mali.movies.features.home.tvshow.TvShow;
import helo.mali.movies.features.home.tvshow.repository.database.TvShowDao;
import helo.mali.movies.features.home.tvshow.repository.network.TvShowNetwork;
import helo.mali.movies.network.ResponseListener;

public class TvShowRepository {

    private TvShowNetwork tvShowNetwork;
    private TvShowDao tvShowDao;

    public TvShowRepository(TvShowNetwork tvShowNetwork, TvShowDao tvShowDao) {
        this.tvShowNetwork = tvShowNetwork;
        this.tvShowDao = tvShowDao;
    }

    public LiveData<List<TvShow>> getTvShowsOrderedByTitle() {

        if (isConnectedOrConnecting()) {
            // If connected, request tv shows from network and update database
            requestTvShowsAndUpdateDatabase();
        }

        // In any case return the tv shows in database(single source of truth)
        return tvShowDao.getAllOrderByTitle();
    }

    public LiveData<List<TvShow>> getTvShowsOrderedByRank() {

        if (isConnectedOrConnecting()) {
            requestTvShowsAndUpdateDatabase();
        }

        return tvShowDao.getAllOrderByRank();
    }

    public LiveData<TvShow> getTvShow(String tvShowId) {

        if (isConnectedOrConnecting()) {
            requestTvShowsAndUpdateDatabase();
        }

        return tvShowDao.getById(tvShowId);
    }

    private void requestTvShowsAndUpdateDatabase() {
        tvShowNetwork.requestTvShows(new ResponseListener<List<TvShow>>() {
            @Override
            public void onResponse(List<TvShow> response) {
                updateTvShowsInDatabase(response);
            }

            @Override
            public void onErrorResponse() {
            }
        });
    }

    private void updateTvShowsInDatabase(List<TvShow> tvShows) {
        AppExecutors.getInstance().diskIO().execute(() ->
                tvShowDao.insertAll(tvShows)
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
