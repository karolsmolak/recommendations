package recommendations.api.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recommendations.infrastructure.command.MovieFilter;
import recommendations.infrastructure.dto.MovieDto;
import recommendations.infrastructure.dto.UserDto;
import recommendations.infrastructure.exceptions.UserNotFoundException;
import recommendations.infrastructure.services.IMovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    IMovieService _movieService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MovieDto get(@PathVariable Integer id){
        MovieDto result = null;
        result = _movieService.get(id);

        if(result == null){
           // throw new Exception("sad");
        }

        return result;
    }

    @GetMapping
    public List<MovieDto> getFiltered(@RequestBody MovieFilter filter) {
        return null;
    }


}
