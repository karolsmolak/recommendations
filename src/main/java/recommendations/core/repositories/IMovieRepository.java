package recommendations.core.repositories;

import recommendations.core.domain.Movie;
import recommendations.core.domain.User;

import java.util.Collection;
import java.util.List;

public interface IMovieRepository {

    void add(Movie movie);
    void update(User user);
    void remove(Integer id);

    Movie findById(Long id);
    List<Movie> findAll();
    Movie findByEmail(String email);

}
