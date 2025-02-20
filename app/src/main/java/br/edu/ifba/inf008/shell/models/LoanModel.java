package br.edu.ifba.inf008.shell.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class LoanModel implements Serializable {
    private final UUID id;
    private final UserModel user;
    private final BookModel book;
    private final Date borrowingDate;

    public LoanModel(UserModel user, BookModel book) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        
        this.id = UUID.randomUUID();
        this.user = user;
        this.book = book;
        this.borrowingDate = new Date();
    }

    public UUID getId() {
        return id;
    }

    public UserModel getUser() {
        return user;
    }

    public BookModel getBook() {
        return book;
    }

    public Date getBorrowingDate() {
        return new Date(borrowingDate.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanModel loanModel = (LoanModel) o;
        return id.equals(loanModel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
