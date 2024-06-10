package ch.scs.jumpstart.movierental.exercise.refactor;

import static ch.scs.jumpstart.movierental.solution.refactor.SolutionCustomerController.AddRental;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import ch.scs.jumpstart.movierental.exercise.common.CustomerBuilder;
import ch.scs.jumpstart.movierental.exercise.common.entity.Customer;
import ch.scs.jumpstart.movierental.exercise.common.entity.Movie;
import ch.scs.jumpstart.movierental.exercise.common.entity.PriceCode;
import ch.scs.jumpstart.movierental.exercise.common.repository.CustomerRepository;
import ch.scs.jumpstart.movierental.exercise.common.repository.MovieRepository;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class CustomerControllerTest {
  // tag::introduce-constant[]
  /*
   Select the "1" in the expression below
   and use the action "Introduce Constant".
   Or use Ctrl + Alt + C
   It proposes an appropriate constant name
   and uses the correct type.
   It also proposes to replace the other instance of the same expression in the class.
   (You can choose to do so with the checkbox below).
   By pressing Ctrl + Alt + C again before applying the constant name, you can open the
   advanced popup with more options.
  */
  private static final Movie MOVIE_1 = new Movie("1", PriceCode.CHILDREN);
  // end::introduce-constant[]
  private static final Movie MOVIE_2 = new Movie("2", PriceCode.NEW_RELEASE);
  private static final Movie MOVIE_3 = new Movie("3", PriceCode.REGULAR);

  private static final String CUSTOMER_NAME_1 = "1";
  private static final Customer CUSTOMER_1 =
      CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 1).build();
  private static final Customer CUSTOMER_2 = new Customer("2");

  private CustomerController controller;
  private CustomerRepository customerRepository;
  private MovieRepository movieRepository;

  @BeforeEach
  public void setup() {
    customerRepository = mock(CustomerRepository.class);
    movieRepository = mock(MovieRepository.class);
    controller = new CustomerController(customerRepository, movieRepository);
  }

  @Test
  public void return_added_customer_when_add_customer() {
    assertThat(controller.addCustomer(CUSTOMER_1), is(ok(CUSTOMER_1)));
    verify(customerRepository).save(CUSTOMER_1);
  }

  @Test
  public void return_empty_list_of_customers_when_no_customers_in_db() {
    when(customerRepository.findAll()).thenReturn(Collections.emptyList());

    assertThat(controller.getCustomers(), is(ok(Collections.emptyList())));
  }

  @Test
  public void return_customers_retrieved_from_repository() {
    var customers = List.of(CUSTOMER_1, CUSTOMER_2);
    when(customerRepository.findAll()).thenReturn(customers);

    assertThat(
        // tag::go-back-and-forth[]
        /*
        Put you cursor on getInvoice
        and use action "Go to"
        or use Ctrl + B
        or use Ctrl + left_click

        If you came back here with the "Back" action,
        you can go forward again with the "Forward" action.
        Or with Ctrl + Alt + ->
        (or with Shift + Alt + ->, depending on your kee binding.)
         */
        controller.getCustomers(),
        // end::go-back-and-forth[]
        is(ok(customers)));
  }

  @Test
  public void return_not_found_if_customer_for_addRental_cannot_be_found() {
    // TODO
  }

  @Test
  public void return_not_found_if_movie_for_addRental_cannot_be_found() {
    when(customerRepository.findById(CUSTOMER_1.getName())).thenReturn(Optional.of(CUSTOMER_1));
    when(movieRepository.findById(notNull())).thenReturn(Optional.empty());

    assertThat(
        controller.addRental(CUSTOMER_1.getName(), new AddRental("", 1)), is(notFound().build()));
  }

  @Test
  public void return_customer_where_rental_was_added_after_successful_addRental() {
    when(customerRepository.findById(CUSTOMER_1.getName())).thenReturn(Optional.of(CUSTOMER_1));
    when(movieRepository.findById(MOVIE_2.getTitle())).thenReturn(Optional.of(MOVIE_2));

    // tag::complete-statement[]
    /*
     Delete the semicolon and 2 braces of the statement below.
     Then use the action "Complete current statement"
     or press Ctrl + Shift + Enter.
     It will guess how many of which braces you need,
     and if you need a semicolon at the end.
    */
    assertThat(
        controller.addRental(CUSTOMER_1.getName(), new AddRental(MOVIE_2.getTitle(), 1)),
        is(ok(CUSTOMER_1)));
    // end::complete-statement[]
    verify(customerRepository).save(CUSTOMER_1);
  }

  @Test
  public void return_not_found_if_customer_for_getInvoice_cannot_be_found() {
    // TODO
    // tag::extract-method[]
    /*
     Select the expression below
     and use the action "Extract Method"
     or press Ctrl + ALt + M.
     With Ctrl + Alt + M again you can open the menu with the advanced options.
    */
    controller.getInvoice("Customer2");
    // end::extract-method[]
    controller.getInvoice(CUSTOMER_NAME_1);
  }

  // tag::extract-parameter[]
  /*
   Put the cursor in the "Customer2" string
   and use the action "Introduce Parameter"
   or press Ctrl + Alt + P
   It then asks you if you want to replace the other expressions
   which could be replaced.
  */
  @SuppressWarnings("unused")
  private ResponseEntity<String> getCustomer2() {
    return controller.getInvoice("Customer2");
  }

  // end::extract-parameter[]

  @Test
  public void return_correct_invoice_for_children_movie() {
    var customer = CustomerBuilder.builder(CUSTOMER_NAME_1).withRental(MOVIE_1, 0).build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    assertThat(
        // tag::introduce-field[]
        /*
         Select the controller.getInvoice(..) expression below without the comma.
        Then use the action "Introduce Field"
        or press Ctrl + Alt + F
        It proposes an appropriate field name
        and uses the correct type.
        It also proposes to replace the other instance of the same expression in the class.
        (You can choose to do so with the checkbox below).
        By pressing Ctrl + Alt + F again before applying the field name, you can open the
        advanced popup with more options.
        */
        controller.getInvoice(CUSTOMER_NAME_1),
        // end::introduce-field[]
        is(ok("Rental Record for 1\n" + "\t1\t1.5\n" + "Amount owed is 1.5\n")));
  }

  @Test
  public void return_correct_invoice_for_new_release_movie() {
    // TODO
  }

  @Test
  public void return_correct_invoice_for_new_regular_movie() {
    // TODO
  }

  @Test
  public void return_correct_invoice_for_multiple_movies() {
    var customer =
        CustomerBuilder.builder(CUSTOMER_NAME_1)
            .withRental(MOVIE_2, 1)
            .withRental(MOVIE_1, 1)
            .withRental(MOVIE_3, 1)
            .build();
    when(customerRepository.findById(CUSTOMER_NAME_1)).thenReturn(Optional.of(customer));

    controller.getInvoice(CUSTOMER_NAME_1);

    assertThat(
        // tag::introduce-variable[]
        /*
         Select the controller.getInvoice(..) expression below without the comma.
         Then use the action "Introduce Variable"
         or press Ctrl + Alt + V
         It proposes an appropriate variable name
         and uses the correct type.
         It also proposes to replace the other instance of the same expression above.
         The small ⚙️ right of the variable name allows to modify the extraction,
         or you can use Alt + Shift + O to open the popup.
        */
        controller.getInvoice(CUSTOMER_NAME_1),
        // end::introduce-variable[]
        is(
            ok(
                "Rental Record for 1\n"
                    + "\t2\t3.0\n"
                    + "\t1\t1.5\n"
                    + "\t3\t2.0\n"
                    + "Amount owed is 6.5\n")));
  }

  @Test
  public void return_not_found_if_customer_for_getJsonInvoice_cannot_be_found() {
    // TODO
  }

  @Test
  public void return_json_invoice() {
    // TODO

    // tag::context-action[]
    // is only here that the AsciiDoc code inclusion works
    //noinspection unused
    var x = 1;
    /*
     Select the lines containing the RentalStatementBuilder chain
     below which are commented with line comments (//),
     and use the action "Show Context Actions"
     Or press Alt + Enter.
     Right of the lightbulb you will see two slashes "//".
     Press the "//" icon to toggle the comment.

     Then, you will see that it does not compile because the import is missing.
     Put the cursor on "RentalStatementBuilder"
     and use the action "Show Context Actions" again.
     Or press Alt + Enter.
     The popup then proposes to fix this with an import.

    */
    // is only here that the AsciiDoc code inclusion works
    //noinspection unused
    var y = 1;
    //    RentalStatementBuilder.builder(CUSTOMER_NAME_1)
    //            .withStatementMovie(MOVIE_3.getTitle(), 5f)
    //            .build()

    // end::context-action[]
  }

  @SuppressWarnings("unused")
  private static final class TestClass<T> extends LinkedList<T> {
    private int number;
    private String string;
    // tag::generate[]
    /*
     Put the cursor on the line after the dateTime field
     and use the action "Generate"
     or press Alt + Insert
     Like this you can generate most of the boilerplate code you need.
    */
    private OffsetDateTime datetime;

    // end::generate[]
  }
}
