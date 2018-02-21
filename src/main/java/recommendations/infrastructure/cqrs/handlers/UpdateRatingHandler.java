package recommendations.infrastructure.cqrs.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recommendations.infrastructure.cqrs.commands.UpdateRating;
import recommendations.infrastructure.services.IUserService;

import java.net.URI;

@Component
public class UpdateRatingHandler implements CommandHandler<UpdateRating> {
    @Autowired
    IUserService _userService;

    @Override
    public ResponseEntity<?> handle(UpdateRating command) {
        try {
            _userService.updateUserRating(command);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(command.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}
