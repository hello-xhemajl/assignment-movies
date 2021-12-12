package helo.mali.movies.features.home.tvshow;


import static helo.mali.movies.features.home.movie.viewmodel.MovieViewModel.ORDER_BY_RANK;
import static helo.mali.movies.features.home.tvshow.viewmodel.TvShowViewModel.ORDER_BY_TITLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import helo.mali.movies.features.home.movie.viewmodel.MovieViewModel;
import helo.mali.movies.features.home.tvshow.viewmodel.TvShowViewModelFactory;
import helo.mali.movies.features.home.tvshow.viewmodel.TvShowViewModel;
import helo.mali.movies.R;

public class TvShowFragment extends Fragment implements TvShowAdapter.TvShowAdapterOnClickHandler {

    private TvShowViewModel tvShowViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TvShowViewModelFactory viewModelFactory = new TvShowViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView tvShowRecyclerView = view.findViewById(R.id.tvshow_recycler_view);
        tvShowRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TvShowAdapter adapter = new TvShowAdapter(this);
        tvShowRecyclerView.setAdapter(adapter);

        tvShowViewModel.getTvShows().observe(getViewLifecycleOwner(), tvShows -> {
            adapter.setTvShows(tvShows);
        });

    }

    @Override
    public void onClick(String tvShowId) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder,
                        TvShowDetailFragment.newInstance(tvShowId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.tvshow, menu);
        MenuItem menuItem = menu.findItem(R.id.order_spinner);

        Spinner orderSpinner = (Spinner) menuItem.getActionView();

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.order_by_rank));
        options.add(getString(R.string.order_by_title));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, options);
        orderSpinner.setAdapter(spinnerAdapter);

        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (selection.equals(getString(R.string.order_by_rank))) {
                    tvShowViewModel.onOrderByChange(ORDER_BY_RANK);
                } else {
                    tvShowViewModel.onOrderByChange(MovieViewModel.ORDER_BY_TITLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
