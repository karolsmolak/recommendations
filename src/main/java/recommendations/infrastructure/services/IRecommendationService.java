package recommendations.infrastructure.services;

import recommendations.infrastructure.dto.MovieDto;

import java.util.List;

public interface IRecommendationService {
    void recalculateRecommendations();
    List<MovieDto> getUserRecommendations(String username);
}
