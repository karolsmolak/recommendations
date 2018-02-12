package recommendations.api.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import recommendations.infrastructure.command.CreateUser;
import recommendations.infrastructure.command.UpdateRating;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.exceptions.UserNotFoundException;
import recommendations.infrastructure.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {

    private IUserService _userService;

    @Autowired
    public UsersController(IUserService userService){
        _userService = userService;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public UserDto get(@PathVariable String username){
        UserDto result = _userService.getByUsername(username);

        if(result == null){
            throw new UserNotFoundException(username);
        }

        return result;
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody CreateUser createUser, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
           throw new ValidationException();
        }

        _userService.register(createUser.getEmail(), createUser.getUsername(), createUser.getPassword());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/")
                .buildAndExpand(createUser.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    public ResponseEntity<?> rate(@PathVariable String username, UpdateRating updateRating) {
        return null;
    }
}
