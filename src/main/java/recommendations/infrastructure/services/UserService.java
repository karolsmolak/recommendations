package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendations.infrastructure.encrypter.IEncrypter;
import recommendations.infrastructure.exceptions.UserAlreadyExistsException;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserRepository _userRepository;

    private ModelMapper _modelMapper;

    private IEncrypter _encrypter;

    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper modelMapper, IEncrypter encrypter){
        this._userRepository = userRepository;
        this._modelMapper = modelMapper;
        this._encrypter = encrypter;
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

        user = new User(email, username, password, salt,40);
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

}
