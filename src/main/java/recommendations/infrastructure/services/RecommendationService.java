package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import recommendations.core.domain.Movie;
import recommendations.core.domain.Rating;
import recommendations.core.domain.User;
import recommendations.infrastructure.dto.MovieDto;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Double.min;

@Service
public class RecommendationService implements IRecommendationService {

    @Value("${numberOfFeatures}")
    private int numberOfFeatures;

    @Value("${numberOfIterations}")
    private int numberOfIterations;

    @Value("${lrate}")
    private double lrate;

    @Autowired
    IUserService _userService;

    @Autowired
    IMovieService _movieService;

    @Autowired
    private ModelMapper _modelMapper;

    @Override
    public void recalculateRecommendations() {

        List<User> users = _userService.getAllWithRatings();

        for (int i = 0 ; i < numberOfIterations ; i++) {
            for (int feature = 0 ; feature < numberOfFeatures ; feature++){
                for (User user : users) {
                    for (Rating rating : user.getUserRatings()) {
                        train(user, rating.getMovie(), rating.getRating(), feature);
                    }
                }
            }
        }

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

    @Override
    public List<MovieDto> getUserRecommendations(String username) {

        User user = _userService.getByUsernameDetails(username);
        List<Movie> movies = _movieService.getAllDetails();

        List<Rating> topTen = new ArrayList<>();

        for (Movie movie : movies) {
            int predictedRating = (int) predictRating(user, movie);

            int higherRanked = 0;
            for (Rating rating : topTen) {
                if (rating.getRating() >= predictedRating) {
                    higherRanked++;
                }
            }

            if (higherRanked != 10) {
                topTen.add(higherRanked, new Rating(movie, predictedRating));
            }

            if (topTen.size() > 10) {
                topTen.remove(10);
            }
        }

        List<MovieDto> result = new LinkedList<>();

        for (Rating rating : topTen) {
            result.add(_modelMapper.map(rating.getMovie(), MovieDto.class));
        }

        return result;

    }
}
