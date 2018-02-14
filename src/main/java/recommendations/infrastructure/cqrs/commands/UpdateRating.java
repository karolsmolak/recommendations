package recommendations.infrastructure.cqrs.commands;

import recommendations.infrastructure.cqrs.commands.ICommand;

public class UpdateRating implements ICommand {

    private String username;
    private Integer movieId;
    private Integer newRating;

}
