package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.domain.Rating;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.cqrs.commands.UpdateRating;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.exceptions.UserAlreadyExistsException;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService implements IUserService, UserDetailsService {

    private IUserRepository _userRepository;

    @Value("${numberOfFeatures}")
    private int numberOfFeatures;

    @Autowired
    private ModelMapper _modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsernameDetails(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
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

        password = bCryptPasswordEncoder.encode(password);

        user = new User(email, username, password, numberOfFeatures);
        _userRepository.add(user);
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
