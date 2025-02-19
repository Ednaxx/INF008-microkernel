package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.LoanModel;
import br.edu.ifba.inf008.shell.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LoanController {
    private static final int MAX_BOOKS_PER_USER = 5;
    private List<LoanModel> loans = new ArrayList<>();

    public LoanController() {}

    public List<LoanModel> getAll() {
        return loans;
    }

    public LoanModel borrowBook(UserModel user, BookModel book) {
        if (isBookBorrowed(book)) return null;
        
        if (getUserBorrowedBooksCount(user) >= MAX_BOOKS_PER_USER) return null;
        
        LoanModel loan = new LoanModel(user, book);
        loans.add(loan);
        return loan;
    }

    public boolean returnBook(UUID loanId) {
        return loans.removeIf(loan -> loan.getId().equals(loanId));
    }

    public boolean isBookBorrowed(BookModel book) {
        return loans.stream()
                .anyMatch(loan -> loan.getBook().getIsbn().equals(book.getIsbn()));
    }

    public int getUserBorrowedBooksCount(UserModel user) {
        return (int) loans.stream()
                .filter(loan -> loan.getUser().getId().equals(user.getId()))
                .count();
    }

    public List<BookModel> getBorrowedBooks(UserModel user) {
        return loans.stream()
                .filter(loan -> loan.getUser().getId().equals(user.getId()))
                .map(LoanModel::getBook)
                .collect(Collectors.toList());
    }

    public LoanModel getActiveLoan(BookModel book) {
        return loans.stream()
                .filter(loan -> loan.getBook().getIsbn().equals(book.getIsbn()))
                .findFirst()
                .orElse(null);
    }

    public boolean canUserBorrowMoreBooks(UserModel user) {
        return getUserBorrowedBooksCount(user) < MAX_BOOKS_PER_USER;
    }
}
