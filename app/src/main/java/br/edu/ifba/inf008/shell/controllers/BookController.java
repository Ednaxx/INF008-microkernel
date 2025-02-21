package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.IBookController;
import br.edu.ifba.inf008.shell.models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookController implements IBookController<BookModel> {
    private final List<BookModel> books = new ArrayList<>();

    public BookController() {}

    public boolean isIsbnUnique(String isbn) {
        return books.stream().noneMatch(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public void addBook(BookModel book) {
        if (book == null || book.getIsbn() == null || book.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("Book and ISBN cannot be null or empty");
        }
        
        if (!isIsbnUnique(book.getIsbn())) {
            throw new IllegalStateException("A book with ISBN " + book.getIsbn() + " already exists");
        }
        
        books.add(book);
    }

    @Override
    public List<BookModel> getAll() {
        return books;
    }

    @Override
    public BookModel getByISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        
        return books.stream()
                   .filter(book -> book.getIsbn().equals(isbn))
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public void updateBook(String isbn, BookModel updatedBook) {
        if (isbn == null || updatedBook == null) {
            throw new IllegalArgumentException("ISBN and updated book cannot be null");
        }

        if (!isbn.equals(updatedBook.getIsbn()) && !isIsbnUnique(updatedBook.getIsbn())) {
            throw new IllegalStateException("Cannot update: A book with ISBN " + updatedBook.getIsbn() + " already exists");
        }

        BookModel existingBook = getByISBN(isbn);
        if (existingBook == null) {
            throw new IllegalStateException("Book with ISBN " + isbn + " not found");
        }

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setReleaseDate(updatedBook.getReleaseDate());
    }

    @Override
    public void deleteBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
