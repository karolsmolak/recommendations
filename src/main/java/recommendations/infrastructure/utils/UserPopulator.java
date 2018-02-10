package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recommendations.core.domain.Movie;
import recommendations.core.domain.User;
import recommendations.core.repositories.IMovieRepository;
import recommendations.core.repositories.IUserRepository;

import java.io.FileReader;

@Component
public class UserPopulator {

    private IUserRepository _userRepository;

    private IMovieRepository _movieRepository;

    @Autowired
    public UserPopulator(IUserRepository userRepository, IMovieRepository movieRepository) {
        _userRepository = userRepository;
        _movieRepository = movieRepository;
    }

    public void populate() throws Exception {
        CSVParser parser = new CSVParser(new FileReader("src/main/resources/ratings.csv"), CSVFormat.EXCEL.withHeader());

        User currentUser = null;
        for (CSVRecord record : parser) {

            Movie movie = _movieRepository.findById(Long.parseLong(record.get("movieId")));

            if (currentUser != null) {
                if(!currentUser.getUsername().equals(record.get("userId"))) {
                    _userRepository.add(currentUser);
                    currentUser = null;
                }
            }

            if (currentUser == null) {
                currentUser = new User("user" + record.get("userId") + "@mail", record.get("userId"), "user" + record.get("userId"), 40);
            }

            currentUser.updateRating(movie,
                    (int)Math.round(Double.parseDouble(record.get("rating")) * 2));
        }

        parser.close();
    }

}
