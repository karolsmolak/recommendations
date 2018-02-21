package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.domain.User;
import recommendations.core.repositories.IMovieRepository;
import recommendations.core.repositories.IUserRepository;

import java.io.FileReader;

@Component
public class UserPopulator {
    private IUserRepository _userRepository;
    private IMovieRepository _movieRepository;
    private final Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserPopulator(IUserRepository userRepository, IMovieRepository movieRepository) {
        _userRepository = userRepository;
        _movieRepository = movieRepository;
    }

    @Transactional
    public void populate() throws Exception {
        logger.info("started user population");
        CSVParser parser = new CSVParser(new FileReader("src/main/resources/ratings.csv"), CSVFormat.EXCEL.withHeader());
        User currentUser = null;

        for (CSVRecord record : parser) {
            Movie movie = _movieRepository.findOne(Integer.valueOf(record.get("movieId")));
            String userId = record.get("userId");

            if (currentUser != null) {
                if(!currentUser.getUsername().equals(userId)) {
                    _userRepository.save(currentUser);
                    currentUser = null;
                }
            }

            if (currentUser == null) {
                String password = "supersecretpassword" + userId;
                password = bCryptPasswordEncoder.encode(password);
                currentUser = new User("user" + userId + "@mail", userId, password, 40)
                        .withCustomId(Integer.valueOf(userId));
            }

            currentUser.updateRating(movie, (int)Math.round(Double.parseDouble(record.get("rating")) * 2));
        }

        parser.close();
        logger.info("finished user population");
    }

}
