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
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        this.author = author.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        // Basic ISBN-13 validation
        String cleanIsbn = isbn.replaceAll("[^0-9]", "");
        if (cleanIsbn.length() != 13) {
            throw new IllegalArgumentException("ISBN must be 13 digits");
        }
        this.isbn = cleanIsbn;
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
        if (releaseDate == null) {
            throw new IllegalArgumentException("Release date cannot be null");
        }
        if (releaseDate.after(new Date())) {
            throw new IllegalArgumentException("Release date cannot be in the future");
        }
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
