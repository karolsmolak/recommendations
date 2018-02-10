package recommendations.infrastructure.services;

import recommendations.core.domain.User;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.exceptions.UserAlreadyExistsException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IUserService {

    void register(String email, String username, String password) throws UserAlreadyExistsException;
    UserDto getByUsername(String username);
    List<User> getAllWithRatings();
    User getByUsernameDetailes(String username);
}
