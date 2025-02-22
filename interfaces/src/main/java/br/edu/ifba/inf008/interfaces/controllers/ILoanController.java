package br.edu.ifba.inf008.interfaces.controllers;

import java.util.List;
import java.util.UUID;

public interface ILoanController<L, U, B> {
    List<L> getAll();
    L borrowBook(U user, B book);
    boolean returnBook(UUID loanId);
    boolean isBookBorrowed(B book);
    int getUserBorrowedBooksCount(U user);
    List<B> getBorrowedBooks(U user);
    L getActiveLoan(B book);
    boolean canUserBorrowMoreBooks(U user);
}
