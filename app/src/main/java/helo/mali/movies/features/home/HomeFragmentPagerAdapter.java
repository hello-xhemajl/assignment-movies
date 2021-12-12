package helo.mali.movies.features.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import helo.mali.movies.features.home.movie.MovieFragment;
import helo.mali.movies.features.home.tvshow.TvShowFragment;


public class HomeFragmentPagerAdapter extends FragmentStateAdapter {

    public HomeFragmentPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new MovieFragment() : new TvShowFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}