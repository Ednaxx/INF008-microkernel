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
        return borrowingDate;
    }
}
