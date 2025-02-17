package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.controllers.UserController;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.collections.FXCollections;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class AdminUserView extends VBox {
    private final UserController userController;
    private ObservableList<UserModel> users;

    public AdminUserView(UserController userController) {
        this.userController = userController;
        this.users = FXCollections.observableArrayList(userController.getAll());
        initializeView();
    }

    private void initializeView() {
        Label adminLabel = new Label("Admin User View");
        TableView<UserModel> userTable = createUserTable();
        Button addUserButton = new Button("Add New User");
        addUserButton.setOnAction(e -> showAddUserPopup(null));

        getChildren().addAll(adminLabel, userTable, addUserButton);
    }

    private TableView<UserModel> createUserTable() {
        TableView<UserModel> table = new TableView<>();

        TableColumn<UserModel, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));

        TableColumn<UserModel, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

        TableColumn<UserModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<UserModel, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(e -> showAddUserPopup(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(e -> {
                    UserModel user = getTableView().getItems().get(getIndex());
                    userController.deleteUser(user.getId());
                    users.remove(user);
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

        table.setItems(users);
        table.getColumns().addAll(firstNameColumn, lastNameColumn, emailColumn, actionsColumn);

        return table;
    }

    private void showAddUserPopup(UserModel user) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(user == null ? "Add New User" : "Edit User");

        VBox popupVBox = new VBox();
        popupVBox.setSpacing(10);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        if (user != null) firstNameField.setText(user.getFirstName());

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        if (user != null) lastNameField.setText(user.getLastName());

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        if (user != null) emailField.setText(user.getEmail());

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (user == null) {
                UserModel newUser = new UserModel(firstName, lastName, email, password, UserRoleEnum.CUSTOMER);
                userController.addUser(newUser);
                users.add(newUser);
            } else {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                userController.updateUser(user.getId(), user);
                users.set(users.indexOf(user), user);
            }
            popupStage.close();
        });

        popupVBox.getChildren().addAll(new Label("First Name:"), firstNameField, new Label("Last Name:"), lastNameField, new Label("Email:"), emailField, new Label("Password:"), passwordField, saveButton);

        Scene popupScene = new Scene(popupVBox, 300, 250);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
