package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.BookController;
import br.edu.ifba.inf008.shell.controllers.UserController;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CustomerBookView extends VBox {
    private final Core core;
    private final BookController bookController;
    private final UserController userController;
    private final AuthenticationController authenticationController;
    private final UserModel currentUser;
    private final ObservableList<BookModel> allBooks;
    private final ObservableList<BookModel> borrowedBooks;
    private TableView<BookModel> allBooksTable;

    public CustomerBookView() {
        this.core = (Core) Core.getInstance();
        this.bookController = core.getBookController();
        this.userController = core.getUserController();
        this.authenticationController = (AuthenticationController) core.getAuthenticationController();
        this.currentUser = this.authenticationController.getCurrentUser();
        this.allBooks = FXCollections.observableArrayList(bookController.getAll());
        this.borrowedBooks = FXCollections.observableArrayList(currentUser.getBorrowedBooks());
        initializeView();
    }

    private void initializeView() {
        Label customerLabel = new Label("Customer Book View");
        allBooksTable = createAllBooksTable();
        TableView<BookModel> borrowedBooksTable = createBorrowedBooksTable();

        getChildren().addAll(customerLabel, new Label("All Books:"), allBooksTable, new Label("Borrowed Books:"), borrowedBooksTable);
    }

    private TableView<BookModel> createAllBooksTable() {
        TableView<BookModel> table = new TableView<>(allBooks);

        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<BookModel, Void> borrowColumn = new TableColumn<>("Borrow");
        borrowColumn.setCellFactory(col -> new TableCell<>() {
            private final Button borrowButton = new Button("Borrow");

            {
                borrowButton.setOnAction(e -> {
                    BookModel book = getTableView().getItems().get(getIndex());
                    if (!borrowedBooks.contains(book) && userController.borrowBook(currentUser.getId(), book)) {
                        borrowedBooks.add(book);
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
                    BookModel book = getTableView().getItems().get(getIndex());
                    borrowButton.setDisable(borrowedBooks.contains(book));
                    setGraphic(borrowButton);
                }
            }
        });

        table.getColumns().addAll(titleColumn, authorColumn, borrowColumn);
        return table;
    }

    private TableView<BookModel> createBorrowedBooksTable() {
        TableView<BookModel> table = new TableView<>(borrowedBooks);

        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));

        TableColumn<BookModel, Void> returnColumn = new TableColumn<>("Return");
        returnColumn.setCellFactory(col -> new TableCell<>() {
            private final Button returnButton = new Button("Return");

            {
                returnButton.setOnAction(e -> {
                    BookModel book = getTableView().getItems().get(getIndex());
                    if (userController.returnBook(currentUser.getId(), book.getIsbn())) {
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

        table.getColumns().addAll(titleColumn, authorColumn, returnColumn);
        return table;
    }
}