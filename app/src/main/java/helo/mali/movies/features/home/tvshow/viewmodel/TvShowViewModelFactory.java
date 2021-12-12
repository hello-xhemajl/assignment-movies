package helo.mali.movies.features.home.tvshow.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import helo.mali.movies.Application;
import helo.mali.movies.database.AppDatabase;
import helo.mali.movies.features.home.tvshow.repository.TvShowRepository;
import helo.mali.movies.features.home.tvshow.repository.database.TvShowDao;
import helo.mali.movies.features.home.tvshow.repository.network.TvShowNetwork;

public class TvShowViewModelFactory implements ViewModelProvider.Factory {
    private TvShowRepository tvShowRepository;

    public TvShowViewModelFactory() {
        TvShowNetwork tvShowNetwork = new TvShowNetwork();
        TvShowDao tvShowDao = AppDatabase.getInstance(Application.getInstance()).tvShowDao();

        this.tvShowRepository = new TvShowRepository(tvShowNetwork, tvShowDao);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            return (T) new TvShowViewModel(tvShowRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
