package recommendations.infrastructure.services;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.domain.Rating;
import recommendations.core.domain.User;
import recommendations.infrastructure.dto.MovieDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.min;

@Service
@Transactional
public class RecommendationService implements IRecommendationService {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${numberOfFeatures}")
    private int numberOfFeatures;

    @Value("${numberOfIterations}")
    private int numberOfIterations;

    @Value("${lrate}")
    private double lrate;

    @Value("${numberOfRecommendationsPresented}")
    private int numberOfRecommendationsPresented;

    @Autowired
    IUserService _userService;

    @Autowired
    private ModelMapper _modelMapper;

    @Override
    public void recalculateRecommendations() {
        logger.info("recalculating recommendations...");
        List<User> users = _userService.getAllWithRatings();
        for (int i = 0 ; i < numberOfIterations ; i++) {
            double sum1 = 0;
            double sum2 = 0;
            for (int feature = 0 ; feature < numberOfFeatures ; feature++) {
                for (User user : users) {
                    for (Rating rating : user.getUserRatings()) {
                        sum1 += (rating.getRating() - predictRating(user, rating.getMovie())) * (rating.getRating() - predictRating(user, rating.getMovie()));
                        sum2 += rating.getRating() * rating.getRating();
                        train(user, rating.getMovie(), rating.getRating(), feature);
                    }
                }
            }
            logger.info("Current stress: " + Math.sqrt(sum1 / sum2));
        }
        logger.info("recommendations recalculated");
    }

    @Override
    public List<MovieDto> getUserRecommendations(String username) {
        User user = _userService.getByUsernameDetails(username);
        List<Movie> movies = _userService.getUnseenMovies(user);
        List<Rating> topRecommendations = new ArrayList<>();
        for (Movie movie : movies) {
            int predictedRating = (int)predictRating(user, movie);
            addMovieToTopRecommendations(topRecommendations, movie, predictedRating);
        }
        return topRecommendations.stream().map((Rating rating) -> _modelMapper.map(rating.getMovie(), MovieDto.class)).collect(Collectors.toList());
    }

    private void train(User user, Movie movie, double rating, int feature) {
        double error = lrate * (rating - predictRating(user, movie));
        double userFeature = user.getFeature(feature);
        user.setFeature(feature ,user.getFeature(feature) + error * movie.getFeature(feature));
        movie.setFeature(feature, movie.getFeature(feature) + error * userFeature);
    }

    private double predictRating(User user, Movie movie) {
        double rating = 0;
        for (int i = 0 ; i < numberOfFeatures ; i++) {
            rating = min(10, rating + user.getFeature(i) * movie.getFeature(i));
        }
        return rating;
    }

    private void addMovieToTopRecommendations(List<Rating> topRecommendations, Movie movie, int movieRating) {
        int higherRanked = getHigherRanked(topRecommendations, movieRating);
        if (higherRanked < numberOfRecommendationsPresented) {
            topRecommendations.add(higherRanked, new Rating(movie, movieRating));
        }
        if (topRecommendations.size() > numberOfRecommendationsPresented) {
            topRecommendations.remove(numberOfRecommendationsPresented);
        }
    }

    private int getHigherRanked(List<Rating> ratings, int newRating) {
        int higherRanked = 0;
        for (Rating rating : ratings) {
            if (rating.getRating() >= newRating) {
                higherRanked++;
            }
        }
        return higherRanked;
    }
}
