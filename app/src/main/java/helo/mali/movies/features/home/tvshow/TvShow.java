package helo.mali.movies.features.home.tvshow;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tvshows")
public class TvShow {
    @PrimaryKey
    @NonNull
    private String id;
    private int rank;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private String crew;
    private double imDbRating;
    private int imDbRatingCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCrew() {
        return crew;
    }

    public void setCrew(String crew) {
        this.crew = crew;
    }

    public double getImDbRating() {
        return imDbRating;
    }

    public void setImDbRating(double imDbRating) {
        this.imDbRating = imDbRating;
    }

    public int getImDbRatingCount() {
        return imDbRatingCount;
    }

    public void setImDbRatingCount(int imDbRatingCount) {
        this.imDbRatingCount = imDbRatingCount;
    }
}