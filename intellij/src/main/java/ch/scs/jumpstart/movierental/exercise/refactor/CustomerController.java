package ch.scs.jumpstart.movierental.exercise.refactor;

import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;

import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Rental;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import ch.scs.jumpstart.movierental.exercise.refactor.rentalstatement.RentalStatement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@SuppressWarnings("DuplicatedCode")
@Component
public class CustomerController {

  // tag::type-hierarchy[]
  /*
   Put the cursor on CustomerRepository
   and use the action "Type Hierarchy"
   or press Ctrl + H
  */
  private final CustomerRepository customerRepository;
  // end::type-hierarchy[]
  // tag::go-to-declaration[]
  /*
   Put the cursor on MovieRepository and use command "Go to declaration"
   Or press Ctrl+B
   Or Ctrl + left_click on MovieRepository
  */
  private final MovieRepository movieRepository;

  // end::go-to-declaration[]

  public CustomerController(
      CustomerRepository customerRepository, MovieRepository movieRepository) {
    this.customerRepository = customerRepository;
    this.movieRepository = movieRepository;
  }

  public ResponseEntity<Customer> addCustomer(Customer customer) {
    customerRepository.save(customer);
    return ResponseEntity.ok(customer);
  }

  /*
  If you come here with the action "Go to"
  you can use the action "Back" to go one step back.
  or you can use Ctrl + Shift + <-
  (or with Shift + Alt + <-, depending on your kee binding.)
  */
  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok(customerRepository.findAll());
  }

  public ResponseEntity<Customer> addRental(String customerName, AddRental addRental) {
    // tag::variable-inspection[]
    /*
     Hover over the variable to see its type.
     Or use the action "Hover Info"
     Or "Type Info" Ctrl + Shift + P
    */
    var customerOptional = customerRepository.findById(customerName);
    // end::variable-inspection[]
    if (customerOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var movie = movieRepository.findById(addRental.getMovieTitle());
    if (movie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    // tag::parameter-inspection[]
    /*
     Use the action "Parameter Info" on the first parameter movie.get()
     Shortcut: Ctrl + P
    */
    var rental = new Rental(movie.get(), addRental.getDaysRented());
    // end::parameter-inspection[]
    var customer = customerOptional.get();
    customer.addRental(rental);
    customerRepository.save(customer);

    return ResponseEntity.ok(customer);
  }

  // tag::compare-with-clipboard-target[]
  /*
   Select this method
   and use action "Compare with clipboard"
  */
  public ResponseEntity<String> getInvoice(String customerName) {
    var customerOptional = customerRepository.findById(customerName);
    if (customerOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var customer = customerOptional.get();
    // tag::show-context-info[]
    /*
    Put the cursor on ResponseEntity
    and use action "Show context info"
    or press Ctrl + Q
     */
    return ResponseEntity.ok(customer.statement());
    // end::show-context-info[]
  }

  // end::compare-with-clipboard-target[]

  public ResponseEntity<RentalStatement> getJsonInvoice(String customerName) {
    // TODO
    // tag::file-structure[]
    /*
     Use action "File structure"
     or press Ctrl+F12
     You can also search in the popup to navigate fast.
     (you can search in most of the jetbrains windows)

     It does not matter where you do that,
     asciidoc code inclusion needs some code to include.
    */
    return ResponseEntity.internalServerError().build();
    // end::file-structure[]
  }
}
