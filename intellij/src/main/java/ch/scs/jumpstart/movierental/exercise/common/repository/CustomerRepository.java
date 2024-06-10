package ch.scs.jumpstart.movierental.exercise.common.repository;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CustomerRepository extends JpaRepository<Customer, String> {
  @Override

  // tag::call-hierarchy[]
  /*
  Put the cursor on findById
  and use action "Call Hierarchy".
  Or use Ctrl + alt + H
   */
  @NonNull
  Optional<Customer> findById(@NonNull String string);
  // end::call-hierarchy[]
}
