package recommendations.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import recommendations.core.domain.Movie;

import java.util.List;

public interface IMovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findByTitleIgnoreCaseStartingWithOrderByTitle(String title);
}
