package recommendations.infrastructure.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;

import java.io.FileReader;

public class MoviePopulator {

    IMovieRepository _movieRepository;

    public MoviePopulator(IMovieRepository movieRepository) {
        _movieRepository = movieRepository;
    }

    public void populate() throws Exception {
        CSVParser parser = new CSVParser(new FileReader("src/main/resources/movies.csv"), CSVFormat.EXCEL.withHeader());

        for (CSVRecord record : parser) {
            _movieRepository.add(new Movie(Integer.parseInt(record.get("movieId")), record.get("title"), record.get("genres"), 40));
        }

        parser.close();
    }

}
