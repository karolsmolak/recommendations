package recommendations.infrastructure.repositories;

import org.springframework.stereotype.Repository;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class InMemoryMovieRepository implements IMovieRepository {

    private List<Movie> movieList = new LinkedList<>();

    public InMemoryMovieRepository() {
        add(new Movie(1, "movie1", "Comedy", 40));
    }

    @Override
    public void add(Movie movie) {
        movieList.add(movie);
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
    public void addMany(List<Movie> movies) {

    }
}
