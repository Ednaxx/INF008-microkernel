package br.edu.ifba.inf008.shell.views;

import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import java.util.Collections;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IBookController;
import br.edu.ifba.inf008.interfaces.ILoanController;
import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.LoanModel;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CustomerBookView extends VBox {
    private final ILoanController<LoanModel, UserModel, BookModel> loanController;
    private final UserModel currentUser;
    private final ObservableList<BookModel> allBooks;
    private final ObservableList<BookModel> borrowedBooks;
    private TableView<BookModel> allBooksTable;

    public CustomerBookView() {
        ICore core = Core.getInstance();
        IBookController<BookModel> bookController = core.getBookController();
        this.loanController = core.getLoanController();
        IAuthenticationController<UserModel> authenticationController = core.getAuthenticationController();
        this.currentUser = authenticationController.getCurrentUser();
        this.allBooks = FXCollections.observableArrayList(bookController.getAll());
        this.borrowedBooks = FXCollections.observableArrayList(loanController.getBorrowedBooks(currentUser));
        initializeView();
    }

    private void initializeView() {
        Label customerLabel = new Label("Customer Book View");
        allBooksTable = createAllBooksTable();
        TableView<BookModel> borrowedBooksTable = createBorrowedBooksTable();

        getChildren().addAll(customerLabel, 
            new Label("All Books:"), allBooksTable, 
            new Label("Borrowed Books:"), borrowedBooksTable);
    }

    private boolean isBookBorrowed(BookModel book) {
        return loanController.isBookBorrowed(book);
    }

    private TableView<BookModel> createAllBooksTable() {
        TableView<BookModel> table = new TableView<>(allBooks);

        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<BookModel, Void> borrowColumn = new TableColumn<>("Borrow");
        borrowColumn.setCellFactory(col -> new TableCell<>() {
            private final Button borrowButton = new Button("Borrow");

            {
                borrowButton.setOnAction(e -> {
                    BookModel book = getTableView().getItems().get(getIndex());
                    LoanModel loan = loanController.borrowBook(currentUser, book);
                    if (loan != null) {
                        borrowedBooks.add(book);
                        allBooksTable.refresh();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Cannot Borrow Book");
                        alert.setHeaderText(null);
                        alert.setContentText(loanController.canUserBorrowMoreBooks(currentUser) ? 
                            "This book is already borrowed." : 
                            "You have reached the maximum number of books you can borrow.");
                        alert.showAndWait();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    BookModel book = getTableView().getItems().get(getIndex());
                    borrowButton.setDisable(isBookBorrowed(book) || 
                        !loanController.canUserBorrowMoreBooks(currentUser));
                    setGraphic(borrowButton);
                }
            }
        });

        Collections.addAll(table.getColumns(), titleColumn, authorColumn, borrowColumn);
        return table;
    }

    private TableView<BookModel> createBorrowedBooksTable() {
        TableView<BookModel> table = new TableView<>(borrowedBooks);

        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<BookModel, String> borrowDateColumn = new TableColumn<>("Borrowed Date");
        borrowDateColumn.setPrefWidth(150);
        borrowDateColumn.setCellValueFactory(cellData -> {
            LoanModel loan = loanController.getActiveLoan(cellData.getValue());
            if (loan == null) return new SimpleStringProperty("");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new SimpleStringProperty(
                loan.getBorrowingDate()
                .toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate().format(formatter)
            );
        });

        TableColumn<BookModel, Void> returnColumn = new TableColumn<>("Return");
        returnColumn.setCellFactory(col -> new TableCell<>() {
            private final Button returnButton = new Button("Return");

            {
                returnButton.setOnAction(e -> {
                    BookModel book = getTableView().getItems().get(getIndex());
                    LoanModel loan = loanController.getActiveLoan(book);
                    if (loan != null && loanController.returnBook(loan.getId())) {
                        borrowedBooks.remove(book);
                        allBooksTable.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(returnButton);
                }
            }
        });

        Collections.addAll(table.getColumns(), titleColumn, authorColumn, borrowDateColumn, returnColumn);
        return table;
    }
}