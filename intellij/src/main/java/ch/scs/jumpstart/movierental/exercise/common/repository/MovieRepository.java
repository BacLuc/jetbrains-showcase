package ch.scs.jumpstart.movierental.exercise.common.repository;

import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, String> {
    @Override
    // tag::method-hierarchy[]
    /*
    Put the cursor on the findAll method
    and use the action "Method Hierarchy"
    or press Ctrl + Shift + H
     */
    @NonNull List<Movie> findAll();
    // end::method-hierarchy[]
}
