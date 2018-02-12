package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recommendations.core.domain.Movie;
import recommendations.core.domain.User;
import recommendations.core.repositories.IMovieRepository;
import recommendations.core.repositories.IUserRepository;
import recommendations.infrastructure.encrypter.IEncrypter;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

@Component
public class UserPopulator {

    @Autowired
    private Logger logger;

    private IUserRepository _userRepository;

    private IMovieRepository _movieRepository;

    private IEncrypter _encrypter;

    @Autowired
    public UserPopulator(IUserRepository userRepository, IMovieRepository movieRepository, IEncrypter encrypter) {
        _userRepository = userRepository;
        _movieRepository = movieRepository;
        _encrypter = encrypter;
    }

    public void populate() throws Exception {
        logger.info("started user population");

        CSVParser parser = new CSVParser(new FileReader("src/main/resources/ratings.csv"), CSVFormat.EXCEL.withHeader());

        List<User> bufor = new LinkedList<>();

        User currentUser = null;
        for (CSVRecord record : parser) {

            Movie movie = _movieRepository.findById(Integer.valueOf(record.get("movieId")));

            if (currentUser != null) {
                if(!currentUser.getUsername().equals(record.get("userId"))) {
                    bufor.add(currentUser);
                    currentUser = null;
                }
            }

            if (currentUser == null) {

                String pass = "supersecretpassword" + record.get("userId");
                String salt = _encrypter.getSalt();

                currentUser = new User(Integer.valueOf(record.get("userId")),
                        "user" + record.get("userId") + "@mail", record.get("userId"),
                        _encrypter.GetHash(pass, salt), salt, 40);
            }

            currentUser.updateRating(movie,
                    (int)Math.round(Double.parseDouble(record.get("rating")) * 2));
        }

        parser.close();

        logger.info("finished reading user data from file");
        logger.info("started inserting users into repository");

        _userRepository.addMany(bufor);

        logger.info("finished user population");
    }

}
