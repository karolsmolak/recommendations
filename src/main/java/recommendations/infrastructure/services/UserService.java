package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.domain.Rating;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.cqrs.commands.UpdateRating;
import recommendations.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendations.infrastructure.encrypter.IEncrypter;
import recommendations.infrastructure.exceptions.UserAlreadyExistsException;

import java.util.List;

@Service
@Transactional
public class UserService implements IUserService {

    private IUserRepository _userRepository;

    @Value("${numberOfFeatures}")
    private int numberOfFeatures;

    @Autowired
    private ModelMapper _modelMapper;

    @Autowired
    private IEncrypter _encrypter;

    @Autowired
    private IMovieService _movieService;

    @Autowired
    public UserService(IUserRepository userRepository){
        this._userRepository = userRepository;
    }

    @Override
    public List<User> getAllWithRatings() {
        return _userRepository.findAll();
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = _userRepository.findByUsername(username);

        if(user == null){
            return null;
        }

        return _modelMapper.map(user, UserDto.class);
    }

    public User getByUsernameDetails(String username) {
        return _userRepository.findByUsername(username);
    }

    @Override
    public void register(String email, String username, String password) throws Exception {
        User user = _userRepository.findByEmail(email);

        if(user != null){
            throw new UserAlreadyExistsException(email);
        }

        String salt = _encrypter.getSalt();
        password = _encrypter.GetHash(password, salt);

        user = new User(email, username, password, salt, numberOfFeatures);
        _userRepository.add(user);
    }

    @Override
    public void login(String username, String password) throws Exception {
        User user = getByUsernameDetails(username);

        if (user == null) {
            throw new Exception("Invalid credentials");
        }

        String hash = _encrypter.GetHash(password, user.getSalt());

        if (!user.getPassword().equals(hash)) {
            throw new Exception("Invalid credentials");
        }

    }

    @Override
    public List<Movie> getUnseenMovies(User user) {
        List<Movie> allMovies = _movieService.getAllDetails();
        for (Rating seenMovie : user.getUserRatings()) {
            allMovies.remove(seenMovie.getMovie());
        }
        return allMovies;
    }

    @Override
    public void updateUserRating(UpdateRating command) throws Exception {
        User user = getByUsernameDetails(command.getUsername());
        Movie movie = _movieService.getDetails(command.getMovieId());

        if (movie == null) {
            throw new Exception();
        }

        user.updateRating(movie, command.getNewRating()) ;
    }
}
