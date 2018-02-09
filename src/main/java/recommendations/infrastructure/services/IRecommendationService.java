package recommendations.infrastructure.services;

import org.springframework.stereotype.Service;
import recommendations.core.domain.Movie;
import recommendations.infrastructure.dto.MovieDto;

import java.util.List;

public interface IRecommendationService {
    void recalculateRecommendations();
    List<MovieDto> getUserRecommendations(String username);
}
