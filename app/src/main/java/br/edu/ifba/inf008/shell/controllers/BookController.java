package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.shell.models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class BookController {
    private static BookController bookController;
    private final List<BookModel> books = new ArrayList<>();

    public BookController() {}

    public void init() {
        bookController = this;
    }

    public static BookController getInstance() {
        return bookController;
    }

    public void addBook(BookModel book) {
        books.add(book);
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public BookModel findBookByIsbn(String isbn) {
        for (BookModel book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}
