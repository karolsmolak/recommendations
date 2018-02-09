package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recommendations.core.domain.User;
import recommendations.core.repositories.IUserRepository;

import java.io.FileReader;

@Component
public class UserPopulator {

    IUserRepository _userRepository;

    public UserPopulator(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    public void populate() throws Exception {
        CSVParser parser = new CSVParser(new FileReader("src/main/resources/ratings.csv"), CSVFormat.EXCEL.withHeader());

        User currentUser = null;
        for (CSVRecord record : parser) {
            if (currentUser != null && Integer.parseInt(record.get("userId")) != currentUser.getId()) {
                _userRepository.add(currentUser);
            }

            if (currentUser == null) {
                currentUser = new User("", "", "", 40);
            }

            currentUser.updateRating(Integer.parseInt(record.get("movieId")),
                    (int)Math.round(Double.parseDouble(record.get("rating")) * 2));
        }

        parser.close();
    }

}
