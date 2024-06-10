package ch.scs.jumpstart.movierental.exercise.common.entity;

import jakarta.persistence.*;

@Entity
public class Rental {
  @SuppressWarnings("unused")
  @Id
  @GeneratedValue
  private int id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Movie movie;

  private int daysRented;

  @SuppressWarnings("unused")
  public Rental() {}

  public Rental(Movie movie, int daysRented) {
    this.movie = movie;
    this.daysRented = daysRented;
  }

  // tag::go-to-usage[]
  /*
   Put the cursor on getDaysRented
   and use the action: Go to Declarations or Usages
   or press Ctrl+B or Ctrl+left_click
  */
  public int getDaysRented() {
    return daysRented;
  }

  // end::go-to-usage[]

  public Movie getMovie() {
    return movie;
  }
}
