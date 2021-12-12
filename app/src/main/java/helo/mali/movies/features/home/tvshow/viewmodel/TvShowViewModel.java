package helo.mali.movies.features.home.tvshow.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import helo.mali.movies.features.home.tvshow.TvShow;
import helo.mali.movies.features.home.tvshow.repository.TvShowRepository;

public class TvShowViewModel extends ViewModel {
    public static final int ORDER_BY_TITLE = 1;
    public static final int ORDER_BY_RANK= 2;

    private TvShowRepository tvShowRepository;

    public TvShowViewModel(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public LiveData<List<TvShow>> getTvShows() {
        return Transformations.switchMap(getOrder(), order -> {
            if(order == ORDER_BY_TITLE) {
                return tvShowRepository.getTvShowsOrderedByTitle();
            } else
                return tvShowRepository.getTvShowsOrderedByRank();
        });
    }

    public LiveData<TvShow> getTvShow(String tvShowId) {
        return tvShowRepository.getTvShow(tvShowId);
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
