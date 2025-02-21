package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.IBookController;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.util.BookGenreEnum;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookController implements IBookController<BookModel, BookGenreEnum> {
    private final List<BookModel> books = new ArrayList<>();

    public BookController() {}

    public boolean isIsbnUnique(String isbn) {
        return books.stream().noneMatch(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public void addBook(String isbn, String title, String author, LocalDate releaseDate, BookGenreEnum genre) {
        String cleanIsbn = isbn.replaceAll("[^0-9]", "");
        simpleBookValidation(cleanIsbn, title, author, releaseDate, genre);

        BookModel newBook = new BookModel(
            title.trim(),
            author.trim(),
            cleanIsbn,
            genre,
            releaseDate
        );
        
        books.add(newBook);
    }

    public void addBook(BookModel book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        simpleBookValidation(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getReleaseDate(), book.getGenre());
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
    public void updateBook(String isbn, String newTitle, String newAuthor, LocalDate newReleaseDate, BookGenreEnum newGenre) {
        simpleBookValidation(isbn, newTitle, newAuthor, newReleaseDate, newGenre);

        BookModel existingBook = getByISBN(isbn);
        if (existingBook == null) {
            throw new IllegalStateException("Book with ISBN " + isbn + " not found");
        }

        existingBook.setTitle(newTitle.trim());
        existingBook.setAuthor(newAuthor.trim());
        existingBook.setGenre(newGenre);
        existingBook.setReleaseDate(newReleaseDate);
    }

    public void updateBook(String isbn, BookModel book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        simpleBookValidation(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getReleaseDate(), book.getGenre());

        BookModel existingBook = getByISBN(isbn);
        if (existingBook == null) {
            throw new IllegalStateException("Book with ISBN " + isbn + " not found");
        }

        existingBook.setTitle(book.getTitle().trim());
        existingBook.setAuthor(book.getAuthor().trim());
        existingBook.setGenre(book.getGenre());
        existingBook.setReleaseDate(book.getReleaseDate());
    }

    @Override
    public void deleteBook(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    private void simpleBookValidation(String isbn, String title, String author, LocalDate releaseDate, BookGenreEnum genre) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("Release date cannot be null");
        }
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null");
        }

        if (releaseDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Release date cannot be in the future");
        }

        if (isbn.length() != 13) {
            throw new IllegalArgumentException("ISBN must be 13 digits");
        }

        if (!isIsbnUnique(isbn)) {
            throw new IllegalStateException("A book with ISBN " + isbn + " already exists");
        }
    }
}
