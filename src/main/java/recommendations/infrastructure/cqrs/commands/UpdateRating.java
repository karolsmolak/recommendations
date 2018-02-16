package recommendations.infrastructure.cqrs.commands;

import recommendations.infrastructure.cqrs.commands.ICommand;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateRating implements ICommand {
    @NotNull
    private String username;

    @NotNull
    private Integer movieId;

    @Min(1)
    @Max(10)
    private Integer newRating;

    public UpdateRating(String username, Integer movieId, Integer newRating) {
        this.username = username;
        this.movieId = movieId;
        this.newRating = newRating;
    }

    public UpdateRating() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getNewRating() {
        return newRating;
    }

    public void setNewRating(Integer newRating) {
        this.newRating = newRating;
    }
}
