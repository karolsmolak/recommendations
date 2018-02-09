package recommendations.infrastructure.command;

public class UpdateRating implements ICommand {
    private String username;
    private Integer movieId;
    private Integer newRating;

}
