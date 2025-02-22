package br.edu.ifba.inf008.interfaces.models;

import java.time.LocalDate;

public interface IBookModel {
    String getTitle();
    void setTitle(String title);
    
    String getAuthor();
    void setAuthor(String author);
    
    String getIsbn();
    void setIsbn(String isbn);
    
    BookGenreEnum getGenre();
    void setGenre(BookGenreEnum genre);
    
    LocalDate getReleaseDate();
    void setReleaseDate(LocalDate releaseDate);
}
