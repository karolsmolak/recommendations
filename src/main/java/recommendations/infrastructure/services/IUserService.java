package recommendations.infrastructure.services;

import recommendations.core.domain.Movie;
import recommendations.core.domain.User;
import recommendations.infrastructure.cqrs.commands.UpdateRating;
import recommendations.infrastructure.dto.UserDto;

import java.util.List;

public interface IUserService {

    void register(String email, String username, String password) throws Exception;
    UserDto getByUsername(String username);
    List<User> getAllWithRatings();
    User getByUsernameDetails(String username);
    void login(String username, String password) throws Exception;

    List<Movie> getUnseenMovies(User user);

    void updateUserRating(UpdateRating command) throws Exception;
}
