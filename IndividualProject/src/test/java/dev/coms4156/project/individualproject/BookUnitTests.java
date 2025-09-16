package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.coms4156.project.individualproject.model.Book;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * This class contains the unit tests for the Book class.
 */
@SpringBootTest

public class BookUnitTests {

  public static Book book;

  /**
   * Sets up a Book instance for testing.
   */
  @BeforeAll
  public static void setUpBookForTesting() {
    book = new Book("Life of Manav", 0);
    book.setAuthors(new ArrayList<>(java.util.Arrays.asList("Manav", "Munjal")));
    book.setLanguage("English");
    book.setShelvingLocation("Top");
    book.setPublicationDate("2025-06-09");
    book.setPublisher("Publisher 1");
    book.setSubjects(new ArrayList<>(java.util.Arrays.asList("Memoir", "Engineering")));
    book.setId(0);
    book.setReturnDates(new ArrayList<>());
    book.setTotalCopies(2);
  }

  /**
   * Tests creation of new book object and checks with original book.
   */

  @Test
  public void equalsBothAreTheSameTest() {
    Book cmpBook = book;
    assertEquals(cmpBook, book);
  }

  /**
   * Tests hasCopies() for a book that has copies.
   */

  @Test
  public void testHasCopies() {
    assertEquals(true, book.hasCopies());
  }

  /**
   * Tests hasMultipleAuthors() for a book with multiple authors defined above.
   */

  @Test
  public void testHasMultipleAuthors() {
    assertEquals(true, book.hasMultipleAuthors());
  }

  /**
   * Tests deleteCopy() for a book with edge cases of 0 and -1 books.
   */

  @Test
  public void testDeleteCopy() {
    Book testBook = new Book("Test", 1);
    testBook.setTotalCopies(2);
    assertEquals(true, testBook.deleteCopy());
    assertEquals(1, testBook.getTotalCopies());
    // Edge Case: no copies available
    testBook.setTotalCopies(0);
    assertEquals(false, testBook.deleteCopy());
    // Edge Case: negative totalCopies
    testBook.setTotalCopies(-1);
    assertEquals(false, testBook.deleteCopy());
  }

  /**
   * Tests addCopy() for a book.
   */

  @Test
  public void testAddCopy() {
    Book testBook = new Book("Test", 2);
    int before = testBook.getTotalCopies();
    testBook.addCopy();
    assertEquals(before + 1, testBook.getTotalCopies());
  }

  /**
   * Tests checkoutCopy() for book having due date and edge case with no due date.
   */

  @Test
  public void testCheckoutCopy() {
    Book testBook = new Book("Test", 3);
    String dueDate = testBook.checkoutCopy();
    assertEquals(0, testBook.getCopiesAvailable());
    assertEquals(1, testBook.getReturnDates().size());
    assertEquals(java.time.LocalDate.now().plusWeeks(2).toString(), dueDate);
    // Edge Case: no copies available
    String dueDate2 = testBook.checkoutCopy();
    assertEquals(null, dueDate2);
  }

  /**
   * Tests returnCopy() for book having due date and edge cases with no invalid dates.
   */

  @Test
  public void testReturnCopy() {
    Book testBook = new Book("Test", 4);
    String dueDate = testBook.checkoutCopy();
    assertEquals(true, testBook.returnCopy(dueDate));
    assertEquals(1, testBook.getCopiesAvailable());
    // Edge Case: return with wrong date (Random string)
    assertEquals(false, testBook.returnCopy("Fake date :)"));
    // Edge Case: return with returnDates as null
    assertEquals(false, testBook.returnCopy(null));
    // Edge Case: return when returnDates is empty
    assertEquals(false, testBook.returnCopy(dueDate));
  }

  /**
   * Tests all getters and setters for Book class.
   */

  @Test
  public void testGettersAndSetters() {
    Book testBook = new Book();
    testBook.setTitle("Manav");
    assertEquals("Manav", testBook.getTitle());
    ArrayList<String> authors = new ArrayList<>();
    authors.add("Manav Munjal");
    testBook.setAuthors(authors);
    assertEquals(authors, testBook.getAuthors());
    testBook.setLanguage("English");
    assertEquals("English", testBook.getLanguage());
    testBook.setShelvingLocation("Top");
    assertEquals("Top", testBook.getShelvingLocation());
    testBook.setPublicationDate("2025-06-09");
    assertEquals("2025-06-09", testBook.getPublicationDate());
    testBook.setPublisher("Publisher 1");
    assertEquals("Publisher 1", testBook.getPublisher());
    ArrayList<String> subjects = new ArrayList<>();
    subjects.add("Memoir");
    testBook.setSubjects(subjects);
    assertEquals(subjects, testBook.getSubjects());
    testBook.setId(1);
    assertEquals(1, testBook.getId());
    ArrayList<String> returnDates = null;
    testBook.setReturnDates(returnDates);
    assertEquals(new ArrayList<>(), testBook.getReturnDates());
    testBook.setTotalCopies(2);
    assertEquals(2, testBook.getTotalCopies());
  }

  /**
   * Tests compareTo() with two books b1 and b2.
   */

  @Test
  public void testCompareTo() {
    Book b1 = new Book("A", 1);
    Book b2 = new Book("B", 2);
    assertEquals(-1, b1.compareTo(b2));
    assertEquals(1, b2.compareTo(b1));
    assertEquals(0, b1.compareTo(new Book("A", 1)));
  }

  /**
   * Tests equals() for books (includes edge cases like comparing with null, random string object).
   */

  @Test
  public void testEquals() {
    Book b1 = new Book("A", 1);
    Book b2 = new Book("A", 1);
    Book b3 = new Book("B", 2);
    assertEquals(true, b1.equals(b2));
    assertEquals(false, b1.equals(b3));
    // Edge Case: compare b1 with null
    assertEquals(false, b1.equals(null));
    // Edge Case: compare b1 with string object "Random"
    assertEquals(false, b1.equals("Random"));
  }

  /**
   * Tests toString() for a book with title "A" and id 1.
   */

  @Test
  public void testToString() {
    Book b = new Book("A", 1);
    assertEquals("(1)\tA", b.toString());
  }

}
