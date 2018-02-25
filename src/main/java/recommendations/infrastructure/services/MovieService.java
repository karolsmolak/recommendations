package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;
import recommendations.infrastructure.dto.MovieDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieService implements IMovieService {
    @Autowired
    private ModelMapper _modelMapper;

    private IMovieRepository _movieRepository;

    @Autowired
    public MovieService(IMovieRepository movieRepository) {
        _movieRepository = movieRepository;
    }

    @Override
    public MovieDto get(Integer id) {
        Movie movie = _movieRepository.findOne(id);
        if (movie == null) {
            return null;
        }
        return _modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public Movie getDetails(Integer id) {
        return _movieRepository.findOne(id);
    }

    @Override
    public List<Movie> getAllDetails() {
        return _movieRepository.findAll();
    }

    @Override
    public List<MovieDto> findMoviesWithSimilarTitle(String title) {
        return _movieRepository.findByTitleIgnoreCaseStartingWithOrderByTitle(title).stream().map((Movie movie) -> _modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAll() {
        return getAllDetails().stream().map((Movie movie) -> _modelMapper.map(movie, MovieDto.class)).collect(Collectors.toList());
    }
}
