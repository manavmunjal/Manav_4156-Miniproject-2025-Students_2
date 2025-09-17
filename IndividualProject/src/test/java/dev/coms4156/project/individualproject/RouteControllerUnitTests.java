package dev.coms4156.project.individualproject;

import dev.coms4156.project.individualproject.controller.RouteController;
import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;

/**
* Unit tests for RouteController methods.
*/
public class RouteControllerUnitTests {

  private RouteController routeController;
  private MockApiService mockApiService;
  /**
   * Sets up a RouteController instance for testing.
   */

  @BeforeEach
  public void setUp() {
    // Create a mock service with one book
    mockApiService = new MockApiService() {
        @Override
        public ArrayList<Book> getBooks() {
            ArrayList<Book> books = new ArrayList<>();
            books.add(new Book("Manav Book", 1));
            return books;
        }
    };
    routeController = new RouteController(mockApiService);
  }


  /**
   * Tests the index() method for welcome message.
   */

  @Test
  public void testIndex() {
    String result = routeController.index();
    Assertions.assertNotNull(result);
    Assertions.assertTrue(result.contains("Welcome to the home page"));
  }


  /**
   * Tests getBook() for a found book.
   */

  @Test
  public void testGetBookFound() {
    ResponseEntity<?> response = routeController.getBook(1);
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertTrue(response.getBody() instanceof Book);
    Assertions.assertEquals(1, ((Book) response.getBody()).getId());
  }


  /**
   * Tests getBook() for a not found book.
   */

  @Test
  public void testGetBookNotFound() {
    ResponseEntity<?> response = routeController.getBook(2);
    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    Assertions.assertEquals("Book not found.", response.getBody());
  }

  /**
   * Tests getRecommendations() returns 10 unique books using the real mock fixture.
   */
  @Test
  public void testGetRecommendations() {
    MockApiService service = new MockApiService();
    RouteController rc = new RouteController(service);

    ResponseEntity<?> response = rc.getRecommendations();
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertTrue(response.getBody() instanceof List<?>);

    @SuppressWarnings("unchecked")
    List<Book> recs = (List<Book>) response.getBody();
    Assertions.assertEquals(10, recs.size(), "10 recommendations returned");

    Set<Integer> ids = new HashSet<>();
    for (Book b : recs) {
      ids.add(b.getId());
    }
    Assertions.assertEquals(10, ids.size(), "All 10 recommendations are unique");
  }

  /**
   * Tests checkoutBook decreases copiesAvailable and persists the change; when copies are
   * exhausted the endpoint returns 400.
   */
  @Test
  public void testCheckoutBookBehavior() {
    MockApiService service = new MockApiService();
    RouteController rc = new RouteController(service);

    // I chose id 4 for testing as it has 3 copies available
    int id = 4;

    // find initial copies
    Book before = null;
    for (Book b : service.getBooks()) {
      if (b.getId() == id) {
        before = b;
        break;
      }
    }
    Assertions.assertNotNull(before, "Book is not null with id: " + id);
    int initialCopies = before.getCopiesAvailable();

    // perform one checkout
    ResponseEntity<?> resp = rc.checkoutBook(id);
    Assertions.assertEquals(HttpStatus.OK, resp.getStatusCode());
    Assertions.assertTrue(resp.getBody() instanceof Book);
    Book after = (Book) resp.getBody();
    Assertions.assertEquals(initialCopies - 1, after.getCopiesAvailable());

    // ensure the service list reflects the change
    Book postCheckoutBook = null;
    for (Book b : service.getBooks()) {
      if (b.getId() == id) {
        postCheckoutBook = b;
        break;
      }
    }
    Assertions.assertNotNull(postCheckoutBook);
    Assertions.assertEquals(after.getCopiesAvailable(), postCheckoutBook.getCopiesAvailable());

    // checkout copies and expect BAD_REQUEST afterwards
    while (postCheckoutBook.getCopiesAvailable() > 0) {
      rc.checkoutBook(id);
      for (Book b : service.getBooks()) {
        if (b.getId() == id) {
          postCheckoutBook = b;
          break;
        }
      }
    }

    ResponseEntity<?> bad = rc.checkoutBook(id);
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, bad.getStatusCode());
  }

  /**
   * Tests getRecommendations() for less than 10 books.
   */
  @Test
  public void testRecommendationsInsufficientBooks() {
    // service with fewer than 10 books
    mockApiService = new MockApiService() {
      @Override
      public ArrayList<Book> getBooks() {
        ArrayList<Book> list = new ArrayList<>();
        list.add(new Book("A", 301));
        list.add(new Book("B", 302));
        list.add(new Book("C", 303));
        return list;
      }
    };

    RouteController rc = new RouteController(mockApiService);
    ResponseEntity<?> resp = rc.getRecommendations();
    Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
    Assertions.assertEquals("Not enough books available for recommendations.", resp.getBody());
  }

  /**
   * Tests checkoutBook() for a book with id not present in list.
   */
  @Test
  public void testCheckoutNotFound() {
    MockApiService realService = new MockApiService();
    RouteController rc = new RouteController(realService);
    ResponseEntity<?> resp = rc.checkoutBook(99999);
    Assertions.assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
  }
}
