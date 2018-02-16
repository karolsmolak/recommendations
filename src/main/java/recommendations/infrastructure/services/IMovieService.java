package recommendations.infrastructure.services;

import recommendations.core.domain.Movie;
import recommendations.infrastructure.dto.MovieDto;

import java.util.List;

public interface IMovieService {
    Movie getDetails(Integer id);
    MovieDto get(Integer id);
    List<Movie> getAllDetails();
    List<MovieDto> findMoviesWithSimilarTitle(String title);
    List<MovieDto> getAll();
}