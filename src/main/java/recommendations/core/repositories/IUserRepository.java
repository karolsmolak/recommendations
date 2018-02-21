package recommendations.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import recommendations.core.domain.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
