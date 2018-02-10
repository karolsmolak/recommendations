package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendations.infrastructure.exceptions.UserAlreadyExistsException;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserRepository _userRepository;

    private ModelMapper _modelMapper;

    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper modelMapper){
        this._userRepository = userRepository;
        this._modelMapper = modelMapper;
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

        UserDto mappedUser = _modelMapper.map(user, UserDto.class);
        return mappedUser;
    }

    public User getByUsernameDetailes(String username) {
        return _userRepository.findByUsername(username);
    }

    @Override
    public void register(String email, String username, String password) throws UserAlreadyExistsException {
        User user = _userRepository.findByEmail(email);

        if(user != null){
            throw new UserAlreadyExistsException(email);
        }

        user = new User(email, username, password, 40);
        _userRepository.add(user);
    }

}
