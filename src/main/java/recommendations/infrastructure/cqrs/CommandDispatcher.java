package recommendations.infrastructure.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import recommendations.infrastructure.cqrs.commands.ICommand;
import recommendations.infrastructure.cqrs.handlers.CommandHandler;

@Component
public class CommandDispatcher implements ICommandDispatcher {
    public interface HandlersProvider{
        CommandHandler<ICommand> getHandler(ICommand command);
    }

    @Autowired
    private HandlersProvider _handlersProvider;

    @Override
    public ResponseEntity<?> dispatch(ICommand command) {
        CommandHandler<ICommand> handler = _handlersProvider.getHandler(command);
        return handler.handle(command);
    }
}
