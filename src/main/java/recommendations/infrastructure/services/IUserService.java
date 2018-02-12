package recommendations.infrastructure.services;

import recommendations.core.domain.User;
import recommendations.infrastructure.dto.UserDto;

import java.util.List;

public interface IUserService {

    void register(String email, String username, String password) throws Exception;
    UserDto getByUsername(String username);
    List<User> getAllWithRatings();
    User getByUsernameDetails(String username);
    void login(String username, String password) throws Exception;
}
