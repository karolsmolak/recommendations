package recommendations.core.repositories;

import recommendations.core.domain.Movie;

import java.util.List;

public interface IMovieRepository {
    void add(Movie movie);
    Movie findById(Integer id);
    List<Movie> findAll();
    void addMany(List<Movie> movies);
}
