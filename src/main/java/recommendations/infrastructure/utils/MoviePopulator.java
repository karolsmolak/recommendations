package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import java.io.FileReader;

@Component
public class MoviePopulator {
    private final Logger logger = Logger.getLogger(this.getClass());

    private IMovieRepository _movieRepository;

    @Autowired
    public MoviePopulator(IMovieRepository movieRepository) {
        _movieRepository = movieRepository;
    }

    @Transactional
    public void populate() throws Exception {
        logger.info("started movie population");

        CSVParser parser = new CSVParser(new FileReader("src/main/resources/movies.csv"), CSVFormat.EXCEL.withHeader());

        for (CSVRecord record : parser) {
            _movieRepository.save(new Movie(Integer.parseInt(record.get("movieId")), record.get("title"), record.get("genres"), 40));
        }

        parser.close();
        logger.info("finished movie population");
    }
}
