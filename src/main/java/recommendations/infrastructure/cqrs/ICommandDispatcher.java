package recommendations.infrastructure.cqrs;

import org.springframework.http.ResponseEntity;
import recommendations.infrastructure.cqrs.commands.ICommand;

public interface ICommandDispatcher {
    ResponseEntity<?> dispatch(ICommand command) throws Exception;
}
