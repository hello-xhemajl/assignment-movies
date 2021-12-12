package helo.mali.movies.features.home.tvshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.squareup.picasso.Picasso;


import helo.mali.movies.features.home.tvshow.viewmodel.TvShowViewModel;
import helo.mali.movies.features.home.tvshow.viewmodel.TvShowViewModelFactory;
import helo.mali.movies.R;

public class TvShowDetailFragment extends Fragment {

    public static final String ARGUMENT_TV_SHOW_ID = "tvShowId";

    public static TvShowDetailFragment newInstance(String tvShowId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_TV_SHOW_ID, tvShowId);
        TvShowDetailFragment tvShowDetailFragment
                = new TvShowDetailFragment();
        tvShowDetailFragment.setArguments(arguments);
        return tvShowDetailFragment;
    }

    private TvShowViewModel tvShowViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TvShowViewModelFactory viewModelFactory = new TvShowViewModelFactory();
        tvShowViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvShowViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tvshow_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String tvShowId = getArguments().getString(ARGUMENT_TV_SHOW_ID);
        tvShowViewModel.getTvShow(tvShowId).observe(getViewLifecycleOwner(), tvShow -> {
            ImageView backdropImageView = view.findViewById(R.id.backdrop);
            Picasso.get().load(tvShow.getImage())
                    .into(backdropImageView);

            TextView rankTextView = view.findViewById(R.id.rank_text_view);
            rankTextView.setText(String.valueOf(tvShow.getRank()));

            TextView fullTitleTextView = view.findViewById(R.id.full_title_text_view);
            fullTitleTextView.setText(tvShow.getFullTitle());

            TextView crewTextView = view.findViewById(R.id.crew_text_view);
            crewTextView.setText(tvShow.getCrew());

            TextView ratingTextView = view.findViewById(R.id.rating_text_view);
            ratingTextView.setText(String.valueOf(tvShow.getImDbRating()));

            TextView ratingCountTextView = view.findViewById(R.id.rating_count_text_view);
            ratingCountTextView.setText(String.valueOf(tvShow.getImDbRatingCount()));
        });
    }
}
