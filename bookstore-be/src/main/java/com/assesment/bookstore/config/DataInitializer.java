package com.assesment.bookstore.config;

import com.assesment.bookstore.model.Book;
import com.assesment.bookstore.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initializeDatabase(BookRepository bookRepository) {
        return args -> {
            // List of 10 default books
            bookRepository.save(Book.builder().title("1984").author("George Orwell").price(10.99).build());
            bookRepository.save(Book.builder().title("Brave New World").author("Aldous Huxley").price(9.99).build());
            bookRepository.save(Book.builder().title("The Catcher in the Rye").author("J.D. Salinger").price(8.99).build());
            bookRepository.save(Book.builder().title("To Kill a Mockingbird").author("Harper Lee").price(7.99).build());
            bookRepository.save(Book.builder().title("The Great Gatsby").author("F. Scott Fitzgerald").price(12.99).build());
            bookRepository.save(Book.builder().title("Moby Dick").author("Herman Melville").price(11.99).build());
            bookRepository.save(Book.builder().title("War and Peace").author("Leo Tolstoy").price(14.99).build());
            bookRepository.save(Book.builder().title("Pride and Prejudice").author("Jane Austen").price(9.49).build());
            bookRepository.save(Book.builder().title("The Lord of the Rings").author("J.R.R. Tolkien").price(15.99).build());
            bookRepository.save(Book.builder().title("The Hobbit").author("J.R.R. Tolkien").price(13.49).build());

            System.out.println("Books have been added to the database.");
        };
    }
}

