package recommendations.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recommendations.infrastructure.dto.MovieDto;
import recommendations.infrastructure.services.IMovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    IMovieService _movieService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDto> get(@PathVariable Integer id) {
        MovieDto result = _movieService.get(id);

        if(result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<MovieDto> get() {
        return _movieService.getAll();
    }

    @GetMapping(value = "/find/{title}")
    public List<MovieDto> find(@PathVariable String title) {
        return _movieService.findMoviesWithSimilarTitle(title);
    }

}
