package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.interfaces.controllers.*;
import br.edu.ifba.inf008.interfaces.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

public class BorrowedBooksSummaryPlugin implements IPlugin {
    public boolean init() {
        try {
            System.out.println("BorrowedBooksSummaryPlugin: Loading plugin...");
            ICore core = ICore.getInstance();

            if (core == null) {
                System.err.println("BorrowedBooksSummaryPlugin: Core instance is null");
                return false;
            }
            

            IUIController uiController = core.getUIController();
            ILoanController<ILoanModel, IUserModel, IBookModel> loanController = core.getLoanController();

            TableView<ILoanModel> tableView = new TableView<>();

            TableColumn<ILoanModel, String> titleColumn = new TableColumn<>("Book Title");
            titleColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getBook().getTitle()));
            titleColumn.setPrefWidth(200);

            TableColumn<ILoanModel, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getBook().getAuthor()));
            authorColumn.setPrefWidth(150);

            TableColumn<ILoanModel, String> isbnColumn = new TableColumn<>("ISBN");
            isbnColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getBook().getIsbn()));
            isbnColumn.setPrefWidth(130);

            TableColumn<ILoanModel, String> borrowerFirstNameColumn = new TableColumn<>("Borrower First Name");
            borrowerFirstNameColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getUser().getFirstName()));
            borrowerFirstNameColumn.setPrefWidth(150);

            TableColumn<ILoanModel, String> borrowerLastNameColumn = new TableColumn<>("Borrower Last Name");
            borrowerLastNameColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getUser().getLastName()));
            borrowerLastNameColumn.setPrefWidth(150);

            tableView.getColumns().addAll(
                titleColumn, 
                authorColumn, 
                isbnColumn, 
                borrowerFirstNameColumn, 
                borrowerLastNameColumn
            );

            tableView.setItems(FXCollections.observableArrayList(loanController.getAll()));

            VBox container = new VBox(tableView);
            container.setSpacing(10);
            container.setStyle("-fx-padding: 10px;");

            uiController.createTab("Borrowed Books Summary", container);

            return true;
        } catch (Exception e) {
            System.err.println("Error initializing BorrowedBooksSummaryPlugin: " + e.getMessage());
            return false;
        }
    }
}
