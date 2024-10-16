package com.assesment.bookstore.service;

import com.assesment.bookstore.model.Book;
import com.assesment.bookstore.repository.BookRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService service;

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(
                Book.builder()
                        .title("1984")
                        .author("George Orwell")
                        .price(10.99)
                        .build(),
                Book.builder()
                        .title("Brave New World")
                        .author("Aldous Huxley")
                        .price(9.99)
                        .build()
        ));

        List<Book> books = service.getAllBooks();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

}
