package recommendations.infrastructure.repositories;

import org.springframework.stereotype.Repository;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.encrypter.Encrypter;

import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryUserRepository implements IUserRepository {

    private List<User> users;

    public InMemoryUserRepository() {
        users = new LinkedList<>();
        try {
            add(new User("user1@mail.com", "user1", new Encrypter().GetHash("user1", "salt"), "salt", 40));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        for (User user : users) {
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
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

    @Override
    public void addMany(List<User> bufor) {

    }
}
