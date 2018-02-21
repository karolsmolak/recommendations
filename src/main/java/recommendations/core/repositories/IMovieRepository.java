package recommendations.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import recommendations.core.domain.Movie;

public interface IMovieRepository extends JpaRepository<Movie, Integer> {
}
