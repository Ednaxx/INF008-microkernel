package br.edu.ifba.inf008.shell.views;

import java.util.Date;
import br.edu.ifba.inf008.shell.controllers.BookController;
import br.edu.ifba.inf008.shell.models.BookModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminBookView extends VBox {
    private final BookController bookController;
    private ObservableList<BookModel> books;

    public AdminBookView(BookController bookController) {
        this.bookController = bookController;
        this.books = FXCollections.observableArrayList(bookController.getAll());
        initializeView();
    }

    private void initializeView() {
        Label adminLabel = new Label("Admin Book View");
        TableView<BookModel> bookTable = createBookTable();
        Button addBookButton = new Button("Add New Book");
        addBookButton.setOnAction(e -> showAddBookPopup(null));

        getChildren().addAll(adminLabel, bookTable, addBookButton);
    }

    private TableView<BookModel> createBookTable() {
        TableView<BookModel> table = new TableView<>();
    
        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        titleColumn.setPrefWidth(200);
    
        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(200);
    
        TableColumn<BookModel, String> releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReleaseDate().toString()));
        releaseDateColumn.setPrefWidth(150);
    
        TableColumn<BookModel, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
    
            {
                editButton.setOnAction(e -> showAddBookPopup(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(e -> {
                    BookModel book = getTableView().getItems().get(getIndex());
                    bookController.deleteBook(book.getIsbn());
                    books.remove(book);
                });
                HBox pane = new HBox(editButton, deleteButton);
                pane.setSpacing(10);
                setGraphic(pane);
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : getGraphic());
            }
        });
        actionsColumn.setPrefWidth(150);
    
        table.setItems(books);
        table.getColumns().addAll(titleColumn, authorColumn, releaseDateColumn, actionsColumn);
    
        return table;
    }

    private void showAddBookPopup(BookModel book) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(book == null ? "Add New Book" : "Edit Book");

        VBox popupVBox = new VBox();
        popupVBox.setSpacing(10);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        if (book != null) titleField.setText(book.getTitle());

        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        if (book != null) authorField.setText(book.getAuthor());

        DatePicker releaseDatePicker = new DatePicker();
        releaseDatePicker.setPromptText("Release Date");
        if (book != null) releaseDatePicker.setValue(((java.sql.Date) book.getReleaseDate()).toLocalDate());

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            Date releaseDate = java.sql.Date.valueOf(releaseDatePicker.getValue());

            if (book == null) {
                BookModel newBook = new BookModel(title, author, "", null, releaseDate);
                bookController.addBook(newBook);
                books.add(newBook);
            } else {
                book.setTitle(title);
                book.setAuthor(author);
                book.setReleaseDate(releaseDate);
                bookController.updateBook(book.getIsbn(), book);
                books.set(books.indexOf(book), book);
            }
            popupStage.close();
        });

        popupVBox.getChildren().addAll(new Label("Title:"), titleField, new Label("Author:"), authorField, new Label("Release Date:"), releaseDatePicker, saveButton);

        Scene popupScene = new Scene(popupVBox, 400, 300);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
