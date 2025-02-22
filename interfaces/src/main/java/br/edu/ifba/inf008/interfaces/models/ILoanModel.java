package br.edu.ifba.inf008.interfaces.models;

import java.util.Date;
import java.util.UUID;

public interface ILoanModel {
    UUID getId();
    IUserModel getUser();
    IBookModel getBook();
    Date getBorrowingDate();
}
