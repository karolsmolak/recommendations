package recommendations.infrastructure.cqrs.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recommendations.infrastructure.cqrs.commands.CreateUser;
import recommendations.infrastructure.services.IUserService;

import java.net.URI;

@Component
public class CreateUserHandler implements CommandHandler<CreateUser> {

    @Autowired
    IUserService _userService;

    @Override
    public ResponseEntity<?> handle(CreateUser command) {
        try {
            _userService.register(command.getEmail(), command.getUsername(), command.getPassword());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(command.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}

