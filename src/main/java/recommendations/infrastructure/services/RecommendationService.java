package recommendations.infrastructure.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import recommendations.core.domain.Movie;
import recommendations.core.domain.Rating;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.dto.MovieDto;

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

    @Override
    public void recalculateRecommendations() {

        List<User> users = _userService.getAllWithRatings();

        for (int i = 0 ; i < numberOfIterations ; i++) {
            for (int feature = 0 ; feature < numberOfFeatures ; feature++){
                for (User user : users) {
                    for (Rating rating : user.getUserRatings()) {
                        train(user, rating.getMovie(), rating.getRating());
                    }
                }
            }
        }

    }

    private double train(User user, Movie movie, double rating, int feature) {
        double error = lrate * (rating - predictRating(user, movie));

        double userFeature = user.getFeature(feature);
        user.setFeature(user.getFeature(feature) + error * movie.getFeature(feature));
        movie.setFeature(movie.getFeature(feature) + error * userFeature);
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
        List<Movie> movies = _userService.getUnseenMovies(user);

        List<Rating> topTen;

        for (Movie movie : movies) {
            int predictedRating = predictRating(user, movie);

            int higherRanked = 0;
            for (Rating rating : topTen) {
                if (rating.getRating() >= predictedRating) {
                    higherRanked++;
                }
            }

            if (higherRanked != 10) {
                topTen.add(new Rating(movie, predictedRating));
            }

        }



    }
}
