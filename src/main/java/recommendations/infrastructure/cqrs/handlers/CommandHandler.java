package recommendations.infrastructure.cqrs.handlers;

import org.springframework.http.ResponseEntity;

public interface CommandHandler<C> {
    ResponseEntity<?> handle(C command);
}
