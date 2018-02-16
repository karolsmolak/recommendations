package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;
import recommendations.infrastructure.dto.MovieDto;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class MovieService implements IMovieService {

    private IMovieRepository _movieRepository;

    @Autowired
    private ModelMapper _modelMapper;

    @Autowired
    public MovieService(IMovieRepository movieRepository) {
        _movieRepository = movieRepository;
    }

    @Override
    public MovieDto get(Integer id) {
        Movie movie = _movieRepository.findById(id);

        if (movie == null) {
            return null;
        }

        return _modelMapper.map(movie, MovieDto.class);

    }

    @Override
    public Movie getDetails(Integer id) {
        return _movieRepository.findById(id);
    }

    @Override
    public List<Movie> getAllDetails() {
        return _movieRepository.findAll();
    }

    @Override
    public List<MovieDto> findMoviesWithSimilarTitle(String title) {
        return null;
    }

    @Override
    public List<MovieDto> getAll() {
        List<Movie> movies = getAllDetails();
        List<MovieDto> result = new LinkedList<>();
        for (Movie movie : movies) {
            result.add(_modelMapper.map(movie, MovieDto.class));
        }
        return result;
    }
}
