package ch.scs.jumpstart.movierental.exercise.common.controller;

import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
public class MovieController {
  static final String PATH = "/movies";
  private final MovieRepository movieRepository;

  // tag::go-to-test[]
  /*
   Put the cursor on MovieController
   and use action "Go to test" to navigate to a test
   or create a test.
   Or use Ctrl + Shift + T
  */
  public MovieController(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  // end::go-to-test[]

  @PostMapping(PATH)
  public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
    movieRepository.save(movie);
    return ResponseEntity.ok(movie);
  }

  @GetMapping(PATH)
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieRepository.findAll());
  }
}
