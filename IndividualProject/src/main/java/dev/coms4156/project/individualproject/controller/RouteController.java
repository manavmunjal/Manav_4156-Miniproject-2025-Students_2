package dev.coms4156.project.individualproject.controller;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class contains all the API routes for the application.
 */
@RestController
public class RouteController {

  private final MockApiService mockApiService;

  public RouteController(MockApiService mockApiService) {
    this.mockApiService = mockApiService;
  }

  @GetMapping({"/", "/index"})
  public String index() {
    return "Welcome to the home page! In order to make an API call direct your browser"
        + "or Postman to an endpoint.";
  }

  /**
   * Returns the details of the specified book.
   *
   * @param id An {@code int} representing the unique identifier of the book to retrieve.
   *
   * @return A {@code ResponseEntity} containing either the matching {@code Book} object with an
   *         HTTP 200 response, or a message indicating that the book was not
   *         found with an HTTP 404 response.
   */
  @GetMapping({"/book/{id}"})
  public ResponseEntity<?> getBook(@PathVariable int id) {
    for (Book book : mockApiService.getBooks()) {
      if (book.getId() == id) {
        return new ResponseEntity<>(book, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
  }

  /**
   * Returns a list of all the books with available copies.
   *
   * @return A {@code ResponseEntity} containing a list of available {@code Book} objects with an
   *         HTTP 200 response if sucessful, or a message indicating an error occurred with an
   *         HTTP 500 response.
   */
  @GetMapping({"/books/available"})
  public ResponseEntity<?> getAvailableBooks() {
    try {
      ArrayList<Book> availableBooks = new ArrayList<>();

      for (Book book : mockApiService.getBooks()) {
        if (book.hasCopies()) {
          availableBooks.add(book);
        }
      }

      return new ResponseEntity<>(availableBooks, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when getting all available books",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }



  /**
   * Adds a copy to the {@code} Book object if it exists.
   *
   * @param bookId An {@code Integer} representing the unique id of the book.
   * @return A {@code ResponseEntity} containing the updated {@code Book} object with an
   *         HTTP 200 response if successful or HTTP 404 if the book is not found,
   *         or a message indicating an error occurred with an HTTP 500 code.
   */
  @PatchMapping({"/book/{bookId}/add"})
  public ResponseEntity<?> addCopy(@PathVariable Integer bookId) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (bookId.equals(book.getId())) {
          book.addCopy();
          return new ResponseEntity<>(book, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Error occurred when adding a copy.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Returns a list of 10 recommended books.
   * Half (5) are the most popular books (by amountOfTimesCheckedOut).
   * Half (5) are randomly selected from the remaining books.
   * The returned list contains unique Books. Order does not matter.
   */
  @GetMapping({"/books/recommendation"})
  public ResponseEntity<?> getRecommendations() {
    try {
      List<Book> allBooks = mockApiService.getBooks();

      if (allBooks == null || allBooks.size() < 10) {
        return new ResponseEntity<>("Not enough books available for recommendations.",
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

      List<Book> booksCopy = new ArrayList<>(allBooks);

      // Sort by popularity (amountOfTimesCheckedOut) descending
      booksCopy.sort((a, b) -> Integer.compare(b.getAmountOfTimesCheckedOut(), a.getAmountOfTimesCheckedOut()));

      List<Book> recommendations = new ArrayList<>();
      Set<Integer> ids = new HashSet<>();

      // Add top 5 most popular
      for (int i = 0; i < 5; i++) {
        Book b = booksCopy.get(i);
        recommendations.add(b);
        ids.add(b.getId());
      }

      // For remaining random books - exclude already selected
      List<Book> remaining = new ArrayList<>();
      for (Book b : booksCopy) {
        if (!ids.contains(b.getId())) {
          remaining.add(b);
        }
      }

      // Not enough remaining to pick random unique books
      if (remaining.size() < 5) {
        return new ResponseEntity<>("Not enough books to form recommendations.",
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

      // Randomly pick 5 unique from remaining
      Random rand = new Random();
      while (recommendations.size() < 10) {
        int idx = rand.nextInt(remaining.size());
        Book candidate = remaining.get(idx);
        if (!ids.contains(candidate.getId())) {
          recommendations.add(candidate);
          ids.add(candidate.getId());
        }
      }

      return new ResponseEntity<>(recommendations, HttpStatus.OK);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Some error occurred when getting recommendations.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Checks out a book given its id via request param 'id'.
   * On success returns the updated Book object.
   */
  @GetMapping({"/checkout"})
  public ResponseEntity<?> checkoutBook(@RequestParam Integer id) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (book.getId() == id) {
          String due = book.checkoutCopy();
          if (due == null) {
            return new ResponseEntity<>("No copy available.", HttpStatus.BAD_REQUEST);
          }
          // Update book in the mock api service
          mockApiService.updateBook(book);
          return new ResponseEntity<>(book, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      System.err.println(e);
      return new ResponseEntity<>("Some error occurred during checkout.",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
