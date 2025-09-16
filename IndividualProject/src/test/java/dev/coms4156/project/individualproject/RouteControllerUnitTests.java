package dev.coms4156.project.individualproject;

import dev.coms4156.project.individualproject.controller.RouteController;
import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
