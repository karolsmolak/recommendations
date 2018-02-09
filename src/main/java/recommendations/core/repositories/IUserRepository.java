package recommendations.core.repositories;

import recommendations.core.domain.User;

import java.util.Collection;
import java.util.List;

public interface IUserRepository {

    void add(User user);
    void update(User user);
    void remove(Integer id);

    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll();
    User findByEmail(String email);

}
