package com.assesment.bookstore.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import com.assesment.bookstore.model.Book;
import com.assesment.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Arrange
        Book book1 = Book.builder()
                .title("Book Title 1")
                .author("J.R.R. Tolkien")
                .price(15.99)
                .build();
        Book book2 = Book.builder()
                .title("Book Title 2")
                .author("Tolkien")
                .price(12.99)
                .build();
        List<Book> bookList = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(bookList);

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book Title 1"))
                .andExpect(jsonPath("$[1].title").value("Book Title 2"));

        verify(bookService, times(1)).getAllBooks();
    }
}

