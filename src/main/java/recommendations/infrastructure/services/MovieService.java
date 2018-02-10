package recommendations.infrastructure.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recommendations.core.domain.Movie;
import recommendations.core.repositories.IMovieRepository;
import recommendations.infrastructure.dto.MovieDto;

import java.util.List;

@Service
public class MovieService implements IMovieService {

    private IMovieRepository _movieRepository;

    private ModelMapper _modelMapper;

    @Autowired
    public MovieService(IMovieRepository movieRepository, ModelMapper modelMapper) {
        _movieRepository = movieRepository;
        _modelMapper = modelMapper;
    }

    @Override
    public MovieDto get(Long id) {
        Movie movie = _movieRepository.findById(id);

        if(movie == null){
            return null;
        }

        MovieDto mappedMovie = _modelMapper.map(movie, MovieDto.class);
        return mappedMovie;

    }

    @Override
    public List<Movie> getAllDetails() {
        return _movieRepository.findAll();
    }
}
