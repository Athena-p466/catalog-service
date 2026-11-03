package com.polarbookshop.catalog_service.domain;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public record EditResult(Book book, boolean created) {}

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList() {
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book) {
        if (bookRepository.existsByIsbn(book.isbn())) {
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public EditResult editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn)
                .map(existing -> {
                    var updated = new Book(existing.isbn(),
                            book.title(), book.author(), book.price());
                    return new EditResult(bookRepository.save(updated), false);
                })
                .orElseGet(() -> new EditResult(addBookToCatalog(book), true));
    }
}