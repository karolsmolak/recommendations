package recommendations.core.repositories;

import recommendations.core.domain.User;

import java.util.List;

public interface IUserRepository {
    void add(User user);
    void remove(Integer id);
    User findById(Integer id);
    User findByUsername(String username);
    List<User> findAll();
    User findByEmail(String email);
    void addMany(List<User> bufor);
}
