package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.shell.models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private final List<BookModel> books = new ArrayList<>();

    public BookController() {}

    public void addBook(BookModel book) {
        books.add(book);
    }

    public List<BookModel> getAll() {
        return books;
    }

    public BookModel getByISBN(String isbn) {
        for (BookModel book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    public void updateBook(String isbn, BookModel updatedBook) {
        for (BookModel book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setGenre(updatedBook.getGenre());
                book.setReleaseDate(updatedBook.getReleaseDate());
            }
        }
    }

    public void deleteBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }
}
