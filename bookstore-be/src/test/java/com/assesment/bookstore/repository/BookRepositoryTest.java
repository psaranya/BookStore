package com.assesment.bookstore.repository;

import com.assesment.bookstore.model.Book;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static junit.framework.TestCase.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertTrue(books.isEmpty()); // This should fail if there are books in the DB
    }

    @Test
    public void testSaveBook() {
       Book book = Book.builder()
                .title("The Hobbit")
                .author("J.R.R. Tolkien")
                .price(15.99)
                .build();
        Book savedBook = bookRepository.save(book);

        assertNotNull(savedBook.getId());
        assertEquals("The Hobbit", savedBook.getTitle());
    }

}
