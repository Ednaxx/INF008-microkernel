package br.edu.ifba.inf008.shell.views;

import java.time.LocalDate;
import java.util.Collections;
import br.edu.ifba.inf008.interfaces.controllers.IBookController;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.controllers.ILoanController;
import br.edu.ifba.inf008.shell.Core;

import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.LoanModel;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.collections.FXCollections;
import br.edu.ifba.inf008.interfaces.models.BookGenreEnum;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminBookView extends VBox {
    private final IBookController<BookModel, BookGenreEnum> bookController;
    private final ILoanController<LoanModel, UserModel, BookModel> loanController;
    private final ObservableList<BookModel> books;

    public AdminBookView() {
        ICore core = Core.getInstance();
        bookController = core.getBookController();
        loanController = core.getLoanController();
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
        titleColumn.setPrefWidth(250);
    
        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        authorColumn.setPrefWidth(250);

        TableColumn<BookModel, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getGenre() != null ? 
                cellData.getValue().getGenre().toString() : ""));
        genreColumn.setPrefWidth(150);
    
        TableColumn<BookModel, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        isbnColumn.setPrefWidth(150);
    
        TableColumn<BookModel, String> releaseDateColumn = new TableColumn<>("Release Date");
        releaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReleaseDate().toString()));
        releaseDateColumn.setPrefWidth(200);
    
        TableColumn<BookModel, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
    
            {
                editButton.setOnAction(e -> showAddBookPopup(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(e -> {
                    try {
                        BookModel book = getTableView().getItems().get(getIndex());
                        
                        if (loanController.isBookBorrowed(book)) {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Cannot Delete");
                            errorAlert.setHeaderText("Book is Currently Borrowed");
                            errorAlert.setContentText("This book cannot be deleted while it is borrowed.");
                            errorAlert.showAndWait();
                            return;
                        }
                        
                        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmDialog.setTitle("Confirm Delete");
                        confirmDialog.setHeaderText("Delete Book");
                        confirmDialog.setContentText("Are you sure you want to delete book: " + 
                                                   book.getTitle() + " by " + book.getAuthor() + "?");
                        
                        if (confirmDialog.showAndWait().get() == ButtonType.OK) {
                            bookController.deleteBook(book.getIsbn());
                            books.remove(book);
                        }
                    } catch (Exception ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Failed");
                        errorAlert.setContentText("Failed to delete book: " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                });
            }
    
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox pane = new HBox(editButton, deleteButton);
                    pane.setSpacing(10);
                    setGraphic(pane);
                }
            }
        });
        actionsColumn.setPrefWidth(200);
    
        table.setItems(books);
        
        Collections.addAll(table.getColumns(), 
        titleColumn, 
        authorColumn, 
        isbnColumn, 
        genreColumn,
        releaseDateColumn, 
        actionsColumn);

        return table;
    }

    private void showAddBookPopup(BookModel book) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(book == null ? "Add New Book" : "Edit Book");
    
        VBox popupVBox = new VBox();
        popupVBox.setSpacing(10);
        popupVBox.setStyle("-fx-padding: 20px;");
    
        Label errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");
        errorMessageLabel.setWrapText(true);
        errorMessageLabel.setMaxWidth(380);
    
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        if (book != null) titleField.setText(book.getTitle());
    
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        if (book != null) authorField.setText(book.getAuthor());
    
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        if (book != null) isbnField.setText(book.getIsbn());
        isbnField.setDisable(book != null);
    
        ComboBox<BookGenreEnum> genreComboBox = new ComboBox<>(FXCollections.observableArrayList(BookGenreEnum.values()));
        genreComboBox.setPromptText("Genre");
        if (book != null) genreComboBox.setValue(book.getGenre());
    
        DatePicker releaseDatePicker = new DatePicker();
        releaseDatePicker.setPromptText("Release Date");
        if (book != null && book.getReleaseDate() != null) {
            releaseDatePicker.setValue(book.getReleaseDate());
        }
    
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, saveButton, cancelButton);
    
        popupVBox.getChildren().addAll(
            errorMessageLabel,
            new Label("Title:"), titleField,
            new Label("Author:"), authorField,
            new Label("ISBN:"), isbnField,
            new Label("Genre:"), genreComboBox,
            new Label("Release Date:"), releaseDatePicker,
            buttonBox
        );
    
        saveButton.setOnAction(e -> {
            try {
                errorMessageLabel.setText("");
                
                if (titleField.getText().trim().isEmpty() ||
                    authorField.getText().trim().isEmpty() ||
                    isbnField.getText().trim().isEmpty() ||
                    genreComboBox.getValue() == null ||
                    releaseDatePicker.getValue() == null) {
                    throw new IllegalArgumentException("All fields are required");
                }
        
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                BookGenreEnum genre = genreComboBox.getValue();
                LocalDate releaseDate = releaseDatePicker.getValue();
        
                if (releaseDate.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("Release date cannot be in the future");
                }
        
                if (book == null) {
                    String cleanIsbn = isbn.replaceAll("[^0-9]", "");
                    if (cleanIsbn.length() != 13) {
                        throw new IllegalArgumentException("ISBN must be 13 digits");
                    }
                    
                    if (!bookController.isIsbnUnique(cleanIsbn)) {
                        throw new IllegalStateException("A book with ISBN " + cleanIsbn + " already exists");
                    }
                    
                    BookModel newBook = new BookModel(title, author, cleanIsbn, genre, releaseDate);
                    bookController.addBook(newBook);
                    books.add(newBook);
                } else {
                    book.setTitle(title);
                    book.setAuthor(author);
                    book.setGenre(genre);
                    book.setReleaseDate(releaseDate);
                    bookController.updateBook(book.getIsbn(), book);
                    
                    int index = books.indexOf(book);
                    books.set(index, book);
                }
                popupStage.close();
        
            } catch (IllegalArgumentException | IllegalStateException ex) {
                errorMessageLabel.setText("Error: " + ex.getMessage());
            } catch (Exception ex) {
                errorMessageLabel.setText("An unexpected error occurred: " + ex.getMessage());
            }
        });
    
        cancelButton.setOnAction(e -> popupStage.close());
    
        Scene popupScene = new Scene(popupVBox, 400, 500);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
