package recommendations.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recommendations.infrastructure.command.MovieFilter;
import recommendations.infrastructure.dto.MovieDto;
import recommendations.infrastructure.services.IMovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    IMovieService _movieService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MovieDto> get(@PathVariable Integer id) throws Exception {
        MovieDto result = _movieService.get(id);

        if(result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<MovieDto> getFiltered(@RequestBody MovieFilter filter) {
        return null;
    }

}
