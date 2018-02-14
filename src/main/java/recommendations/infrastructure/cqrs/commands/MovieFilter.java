package recommendations.infrastructure.cqrs.commands;

import recommendations.infrastructure.cqrs.commands.ICommand;

public class MovieFilter implements ICommand {

    private String genre;
}
