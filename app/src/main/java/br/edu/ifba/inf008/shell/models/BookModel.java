package br.edu.ifba.inf008.shell.models;

import br.edu.ifba.inf008.interfaces.models.IBookModel;
import br.edu.ifba.inf008.interfaces.models.BookGenreEnum;

import java.io.Serializable;
import java.time.LocalDate;

public class BookModel implements IBookModel, Serializable {
    private String title;
    private String author;
    private String isbn;
    private BookGenreEnum genre;
    private LocalDate releaseDate; 

    public BookModel(String title, String author, String isbn, BookGenreEnum genre, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BookGenreEnum getGenre() {
        return genre;
    }

    public void setGenre(BookGenreEnum genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookModel bookModel = (BookModel) o;
        return isbn.equals(bookModel.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
