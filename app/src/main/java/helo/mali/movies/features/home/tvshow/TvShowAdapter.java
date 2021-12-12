package helo.mali.movies.features.home.tvshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import helo.mali.movies.R;


public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder> {

    private List<TvShow> tvShows;

    private final TvShowAdapterOnClickHandler clickHandler;

    public interface TvShowAdapterOnClickHandler {
        void onClick(String tvShowId);
    }

    public TvShowAdapter(TvShowAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tvshow, parent, false);

        return new TvShowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        TvShow tvShow = tvShows.get(position);
        String title = tvShow.getTitle();
        String posterPath = tvShow.getImage();

        //holder.titleTextView.setText(title);
        Picasso.get().load(posterPath)
                .into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return tvShows == null ? 0 : tvShows.size();
    }

    public void setTvShows(List<TvShow> tvShows) {
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }

    public class TvShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        ImageView posterImageView;

        public TvShowViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            posterImageView = itemView.findViewById(R.id.poster_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String tvShowId = tvShows.get(adapterPosition).getId();
            clickHandler.onClick(tvShowId);
        }
    }
}
