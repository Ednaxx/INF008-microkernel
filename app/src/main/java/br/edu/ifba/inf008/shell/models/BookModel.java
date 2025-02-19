package br.edu.ifba.inf008.shell.models;

import br.edu.ifba.inf008.shell.util.BookGenreEnum;

import java.io.Serializable;
import java.util.Date;

public class BookModel implements Serializable {
    private String title;
    private String author;
    private String isbn;
    private BookGenreEnum genre;
    private Date releaseDate;

    public BookModel(String title, String author, String isbn, BookGenreEnum genre, Date releaseDate) {
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}