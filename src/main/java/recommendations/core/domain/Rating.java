package recommendations.core.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Rating {
    @AttributeOverrides({
            @AttributeOverride(
                    name = "userId",
                    column = @Column(name = "user_id")),
            @AttributeOverride(
                    name = "movieId",
                    column = @Column(name = "movie_id"))
    })
    @EmbeddedId
    RatingPK id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    private Integer rating;

    public Rating() {
    }

    public Rating(User user, Movie movie, Integer rating) {
        this.id = new RatingPK(user.getId(), movie.getId());
        this.movie = movie;
        this.user = user;
        this.rating = rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public Integer getRating() {
        return rating;
    }
}

@Embeddable
class RatingPK implements Serializable {
    private Integer userId;
    private Integer movieId;

    public RatingPK(Integer userId, Integer movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public RatingPK() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingPK ratingPK = (RatingPK) o;
        return Objects.equals(userId, userId) &&
                Objects.equals(movieId, movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}
