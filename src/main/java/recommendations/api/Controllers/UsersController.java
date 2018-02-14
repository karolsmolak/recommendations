package recommendations.api.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import recommendations.infrastructure.cqrs.commands.CreateUser;
import recommendations.infrastructure.cqrs.ICommandDispatcher;
import recommendations.infrastructure.cqrs.commands.UpdateRating;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private ICommandDispatcher _commandDispatcher;

    @Autowired
    private IUserService _userService;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> get(@PathVariable String username){
        UserDto result = _userService.getByUsername(username);

        if(result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid @RequestBody CreateUser createUser, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return _commandDispatcher.dispatch(createUser);
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public ResponseEntity<?> rate(@PathVariable String username, UpdateRating updateRating) {
        return ResponseEntity.ok().body(null);
    }
}
