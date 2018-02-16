package recommendations.infrastructure.repositories;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Primary
public class UserRepository implements IUserRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.merge(user);
    }

    @Override
    public void addMany(List<User> users) {
        for (User user : users) {
            entityManager.merge(user);
        }
    }

    @Override
    public void remove(Integer id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User findById(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        try {
            return (User) entityManager.createQuery("from User where username = :username")
                    .setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return (List<User>)entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User findByEmail(String email) {
        try {
            return (User) entityManager.createQuery("from User where email = :email")
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
