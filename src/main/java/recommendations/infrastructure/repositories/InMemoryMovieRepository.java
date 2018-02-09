package recommendations.infrastructure.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import recommendations.core.domain.Movie;
import recommendations.core.domain.User;
import recommendations.core.repositories.IMovieRepository;
import recommendations.infrastructure.utils.MoviePopulator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryMovieRepository implements IMovieRepository {

    List<Movie> movieList = new LinkedList<>();

    @Override
    public void add(Movie movie) {
        movieList.add(movie);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public Movie findById(Integer id) {
        for (Movie movie : movieList) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    @Override
    public List<Movie> findAll() {
        return movieList;
    }

    @Override
    public Movie findByEmail(String email) {
        return null;
    }
}
