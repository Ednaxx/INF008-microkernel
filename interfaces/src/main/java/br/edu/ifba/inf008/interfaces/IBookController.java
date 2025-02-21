package br.edu.ifba.inf008.interfaces;

import java.util.List;

public interface IBookController<T> {
    void addBook(T book);
    List<T> getAll();
    T getByISBN(String isbn);
    void updateBook(String isbn, T updatedBook);
    void deleteBook(String isbn);
    boolean isIsbnUnique(String isbn);
}
