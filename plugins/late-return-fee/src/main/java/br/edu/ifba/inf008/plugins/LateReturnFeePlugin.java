package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.interfaces.controllers.*;
import br.edu.ifba.inf008.interfaces.models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class LateReturnFeePlugin implements IPlugin {
    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double DAILY_FEE = 0.50;

    public boolean init() {
        try {
            System.out.println("LateReturnFeePlugin: Starting plugin...");
            ICore core = ICore.getInstance();

            if (core == null) {
                System.err.println("LateReturnFeePlugin: Core instance is null");
                return false;
            }

            IUIController uiController = core.getUIController();
            ILoanController<ILoanModel, IUserModel, IBookModel> loanController = core.getLoanController();

            TableView<ILoanModel> tableView = new TableView<>();

            TableColumn<ILoanModel, String> bookColumn = new TableColumn<>("Book");
            bookColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getBook().getTitle()));

            TableColumn<ILoanModel, String> userColumn = new TableColumn<>("User");
            userColumn.setCellValueFactory(data -> 
                new SimpleStringProperty(data.getValue().getUser().getFirstName() + " " + 
                                       data.getValue().getUser().getLastName()));

            TableColumn<ILoanModel, String> borrowDateColumn = new TableColumn<>("Borrow Date");
            borrowDateColumn.setCellValueFactory(data -> {
                LocalDate borrowDate = data.getValue().getBorrowingDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                return new SimpleStringProperty(borrowDate.toString());
            });

            TableColumn<ILoanModel, String> daysLateColumn = new TableColumn<>("Days Late");
            daysLateColumn.setCellValueFactory(data -> {
                LocalDate borrowDate = data.getValue().getBorrowingDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(
                    borrowDate.plusDays(LOAN_PERIOD_DAYS), 
                    LocalDate.now()
                );
                return new SimpleStringProperty(String.valueOf(Math.max(0, daysLate)));
            });

            TableColumn<ILoanModel, String> feeColumn = new TableColumn<>("Late Fee (BRL)");
            feeColumn.setCellValueFactory(data -> {
                LocalDate borrowDate = data.getValue().getBorrowingDate()
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long daysLate = ChronoUnit.DAYS.between(
                    borrowDate.plusDays(LOAN_PERIOD_DAYS), 
                    LocalDate.now()
                );
                double fee = Math.max(0, daysLate * DAILY_FEE);
                return new SimpleStringProperty(String.format("R$ %.2f", fee));
            });

            tableView.getColumns().addAll(
                bookColumn, userColumn, borrowDateColumn, daysLateColumn, feeColumn
            );

            List<ILoanModel> lateReturns = loanController.getAll().stream()
                .filter(loan -> {
                    LocalDate borrowDate = loan.getBorrowingDate()
                        .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return ChronoUnit.DAYS.between(borrowDate, LocalDate.now()) > LOAN_PERIOD_DAYS;
                })
                .collect(Collectors.toList());

            tableView.setItems(FXCollections.observableArrayList(lateReturns));

            VBox container = new VBox(tableView);
            container.setSpacing(10);
            container.setStyle("-fx-padding: 10px;");

            uiController.createTab("Late Returns", container);

            return true;
        } catch (Exception e) {
            System.err.println("Error initializing LateReturnFeePlugin: " + e.getMessage());
            return false;
        }
    }
}
