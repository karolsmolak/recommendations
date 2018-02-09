package recommendations.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recommendations.core.domain.Movie;
import recommendations.infrastructure.dto.MovieDto;
import recommendations.infrastructure.services.IRecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    @Autowired
    private IRecommendationService _recommendationService;

    public List<MovieDto> get(@PathVariable String username){
        return _recommendationService.getUserRecommendations(username);
    }

    @Scheduled(fixedRate = 100000)
    public void recalculate(){
        _recommendationService.recalculateRecommendations();
    }

}
