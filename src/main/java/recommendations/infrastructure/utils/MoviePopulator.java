package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

@Component
public class MoviePopulator {

    @Autowired
    private Logger logger;

    private IMovieRepository _movieRepository;

    @Autowired
    public MoviePopulator(IMovieRepository movieRepository) {
        _movieRepository = movieRepository;
    }

    public void populate() throws Exception {
        logger.info("started movie population");

        CSVParser parser = new CSVParser(new FileReader("src/main/resources/movies.csv"), CSVFormat.EXCEL.withHeader());

        List<Movie> bufor = new LinkedList<>();

        for (CSVRecord record : parser) {
            bufor.add(new Movie(Integer.parseInt(record.get("movieId")), record.get("title"), record.get("genres"), 40));
        }

        parser.close();

        logger.info("finished reading movie data from file");
        logger.info("started inserting movies into repository");

        _movieRepository.addMany(bufor);

        logger.info("finished movie population");
    }

}
