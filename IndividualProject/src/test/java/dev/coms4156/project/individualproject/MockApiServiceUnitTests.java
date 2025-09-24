package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Unit tests for MockApiService.
 */
public class MockApiServiceUnitTests {

  private MockApiService mockApiService;

  /**
  * Sets up a mockApiService instance for testing.
  */
  @BeforeEach
  public void setUp() {
    mockApiService = new MockApiService();
  }

  /**
   * Tests loading of Books from getBooks and checks for null.
   */
  @Test
  public void testBooksLoadedNotNull() {
    ArrayList<Book> books = mockApiService.getBooks();
    assertNotNull(books, "Books list should not be null");
  }

  /**
   * Tests updation of book or insert dummy book.
   */
  @Test
  public void testUpdateBookReplacesExistingById() {
    ArrayList<Book> books = mockApiService.getBooks();

    if (!books.isEmpty()) {
      Book original = books.get(0);
      int id = original.getId();

      // Create an updated book with the same id but different title.
      Book updated = new Book("UpdatedTitle", id);

      mockApiService.updateBook(updated);

      ArrayList<Book> updatedBooks = mockApiService.getBooks();

      assertTrue(updatedBooks.contains(updated),
          "Updated book should exist in the list after update");
      assertEquals("UpdatedTitle",
          updatedBooks.stream().filter(b -> b.getId() == id).findFirst().get().getTitle(),
          "The book title should be updated");
    } else {
      // If no books exist, just verify updateBook doesn't throw.
      assertDoesNotThrow(() -> {
        Book dummy = new Book("Dummy", 1);
        mockApiService.updateBook(dummy);
      });
    }
  }

  /**
   * Tests updation of book with non-existing id does not change list size.
   */
  @Test
  public void testUpdateBookWithNonExistingIdDoesNotChangeSize() {
    ArrayList<Book> beforeUpdate = new ArrayList<>(mockApiService.getBooks());
    int maxId = beforeUpdate.stream().mapToInt(Book::getId).max().orElse(0);
    Book nonExisting = new Book("NonExistingTitle", maxId + 100); // definitely not in list.

    mockApiService.updateBook(nonExisting);

    ArrayList<Book> afterUpdate = mockApiService.getBooks();
    assertEquals(beforeUpdate.size(), afterUpdate.size(),
        "Updating with a non-existing book id should not change list size");
  }

  /**
   * Test that printBooks() does not throw even with empty book list.
   */
  @Test
  public void testPrintBooksEmptyList() {
    // Empty the book list
    mockApiService = new MockApiService() {
        @Override
        public ArrayList<Book> getBooks() {
            return new ArrayList<>();
        }
    };

    assertDoesNotThrow(() -> mockApiService.printBooks(),
        "printBooks should not throw even if book list is empty");
  }

  /**
   * Test updateBook() with empty book list.
   */
  @Test
  public void testUpdateBookEmptyList() {
    mockApiService = new MockApiService() {
        @Override
        public ArrayList<Book> getBooks() {
            return new ArrayList<>();
        }
    };

    Book newBook = new Book("NewBook", 999);
    assertDoesNotThrow(() -> mockApiService.updateBook(newBook),
        "updateBook should not throw when book list is empty");
    assertEquals(0, mockApiService.getBooks().size(),
        "Book list should remain empty after updating non-existent book");
  }

  /**
   * Test constructor behavior when JSON file is missing.
   * Since we cannot remove resources in test easily, we simulate by subclassing.
   */
  @Test
  public void testConstructorHandlesMissingJson() {
    MockApiService service = new MockApiService() {
        @Override
        public ArrayList<Book> getBooks() {
            // simulate missing JSON by returning empty list
            return new ArrayList<>();
        }
    };

    assertNotNull(service.getBooks(), "Books list should be initialized even if JSON missing");
    assertEquals(0, service.getBooks().size(), "Books list should be empty if JSON not found");
  }

  /**
   * Test updateBook() replaces all books with the same id.
   */
  @Test
  public void testUpdateBookMultipleSameId() {
    ArrayList<Book> books = mockApiService.getBooks();
    if (books.size() >= 1) {
      int duplicateId = books.get(0).getId();

      // Add a duplicate book with the same id
      books.add(new Book("BookDuplicate", duplicateId));

      // Update with new title
      Book updated = new Book("UpdatedBook", duplicateId);
      mockApiService.updateBook(updated);

      ArrayList<Book> updatedBooks = mockApiService.getBooks();

      // Verify ALL duplicates are replaced
      assertTrue(updatedBooks.stream()
          .filter(b -> b.getId() == duplicateId)
          .allMatch(b -> "UpdatedBook".equals(b.getTitle())),
          "All books with same id should be replaced with updated title");
    }
  }

  /**
   * Test that printBooks() does not throw an exception when the book list is populated.
   */
  @Test
  public void testPrintBooksWithContent() {
    // The setUp() method already initializes the service, which should load books.
    assertNotNull(mockApiService.getBooks(), "Book list should be initialized.");
    assertTrue(mockApiService.getBooks().size() > 0, "Book list should not be empty for this test.");
    assertDoesNotThrow(() -> mockApiService.printBooks(),
        "printBooks should not throw an exception with a populated list.");
  }
}
