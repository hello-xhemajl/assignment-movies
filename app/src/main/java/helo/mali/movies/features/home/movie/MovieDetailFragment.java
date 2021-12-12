package helo.mali.movies.features.home.movie;

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


import helo.mali.movies.features.home.movie.viewmodel.MovieViewModel;
import helo.mali.movies.features.home.movie.viewmodel.MovieViewModelFactory;
import helo.mali.movies.R;

public class MovieDetailFragment extends Fragment {

    public static final String ARGUMENT_MOVIE_ID = "movieId";

    public static MovieDetailFragment newInstance(String movieId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_MOVIE_ID, movieId);
        MovieDetailFragment movieDetailFragment
                = new MovieDetailFragment();
        movieDetailFragment.setArguments(arguments);
        return movieDetailFragment;
    }

    private MovieViewModel movieViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieViewModelFactory viewModelFactory = new MovieViewModelFactory();
        movieViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String movieId = getArguments().getString(ARGUMENT_MOVIE_ID);
        movieViewModel.getMovie(movieId).observe(getViewLifecycleOwner(), movie -> {
            ImageView backdropImageView = view.findViewById(R.id.backdrop);
            Picasso.get().load(movie.getImage())
                    .into(backdropImageView);

            TextView rankTextView = view.findViewById(R.id.rank_text_view);
            rankTextView.setText(String.valueOf(movie.getRank()));

            TextView fullTitleTextView = view.findViewById(R.id.full_title_text_view);
            fullTitleTextView.setText(movie.getFullTitle());

            TextView crewTextView = view.findViewById(R.id.crew_text_view);
            crewTextView.setText(movie.getCrew());

            TextView ratingTextView = view.findViewById(R.id.rating_text_view);
            ratingTextView.setText(String.valueOf(movie.getImDbRating()));

            TextView ratingCountTextView = view.findViewById(R.id.rating_count_text_view);
            ratingCountTextView.setText(String.valueOf(movie.getImDbRatingCount()));
        });
    }
}
