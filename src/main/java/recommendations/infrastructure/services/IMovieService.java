package recommendations.infrastructure.services;

import recommendations.core.domain.Movie;
import recommendations.infrastructure.dto.MovieDto;

import java.util.List;

public interface IMovieService {
    MovieDto get(Long id);
    List<Movie> getAllDetails();
}
