package recommendations.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import org.springframework.stereotype.Repository;
import recommendations.infrastructure.utils.UserPopulator;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements IUserRepository {

    private static List<User> users = new LinkedList<>();

    public InMemoryUserRepository() {
        add(new User("user1@mail", "user1", "secret", 40));
        add(new User("user2@mail.com", "user2", "secret",40));
        add(new User("user3@mail.com", "user3", "secret",  40));
        add(new User("user4@mail.com", "user3", "secret",  40));
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(Integer id) {
        User user = findById(id);
        users.remove(user);
    }

    @Override
    public User findById(Integer id) {
        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findByEmail(String email) {
        for(User user : users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }
}
