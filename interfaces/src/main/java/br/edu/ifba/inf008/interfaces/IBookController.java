package br.edu.ifba.inf008.interfaces;

import java.util.List;
import java.util.Date;

public interface IBookController<T, G> {
    void addBook(String isbn, String title, String author, Date releaseDate, G genre);
    void addBook(T book);
    List<T> getAll();
    T getByISBN(String isbn);
    void updateBook(String isbn, String title, String author, Date releaseDate, G genre);
    void updateBook(String isbn, T book);
    void deleteBook(String isbn);
    boolean isIsbnUnique(String isbn);
}
