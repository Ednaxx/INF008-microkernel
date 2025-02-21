package br.edu.ifba.inf008.shell.views;

import java.util.Collections;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUserController;
import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminUserView extends VBox {
    private final IUserController<UserModel> userController;
    private final IAuthenticationController<UserModel> authenticationController;
    private final ObservableList<UserModel> users;

    public AdminUserView() {
        ICore core = Core.getInstance();
        userController = core.getUserController();
        this.authenticationController = core.getAuthenticationController();
        this.users = FXCollections.observableArrayList(userController.getAll());
        initializeView();
    }

    private void initializeView() {
        Label adminLabel = new Label("Admin User View");
        TableView<UserModel> userTable = createUserTable();
        Button addUserButton = new Button("Add New User");
        addUserButton.setOnAction(e -> showAddUserPopup(null));

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            authenticationController.signOut();
            new AuthenticationView((Stage) getScene().getWindow()).show();
        });

        HBox buttonBox = new HBox(10, addUserButton, logoutButton);
        getChildren().addAll(adminLabel, userTable, buttonBox);
    }

    private TableView<UserModel> createUserTable() {
        TableView<UserModel> table = new TableView<>();

        TableColumn<UserModel, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        firstNameColumn.setPrefWidth(150);

        TableColumn<UserModel, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        lastNameColumn.setPrefWidth(150);

        TableColumn<UserModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        emailColumn.setPrefWidth(200);

        TableColumn<UserModel, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole().toString()));
        roleColumn.setPrefWidth(100);

        TableColumn<UserModel, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(e -> showAddUserPopup(getTableView().getItems().get(getIndex())));
                deleteButton.setOnAction(e -> {
                    try {
                        UserModel user = getTableView().getItems().get(getIndex());
                        
                        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmDialog.setTitle("Confirm Delete");
                        confirmDialog.setHeaderText("Delete User");
                        confirmDialog.setContentText("Are you sure you want to delete user: " + 
                                                   user.getFirstName() + " " + user.getLastName() + "?");
                        
                        if (confirmDialog.showAndWait().get() == ButtonType.OK) {
                            userController.deleteUser(user.getId());
                            users.remove(user);
                        }
                    } catch (Exception ex) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText("Delete Failed");
                        errorAlert.setContentText("Failed to delete user: " + ex.getMessage());
                        errorAlert.showAndWait();
                    }
                });
            }

            private void setButtons() {
                UserModel user = getTableView().getItems().get(getIndex());
                boolean isDefaultAdmin = user.getRole() == UserRoleEnum.ADMIN && user.getEmail().equals("admin@admin.com");
                editButton.setDisable(isDefaultAdmin);
                deleteButton.setDisable(isDefaultAdmin);

                HBox pane = new HBox(editButton, deleteButton);
                pane.setSpacing(10);
                setGraphic(pane);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setButtons();
                }
            }
        });
        actionsColumn.setPrefWidth(150);

        table.setItems(users);
        Collections.addAll(table.getColumns(), firstNameColumn, lastNameColumn, emailColumn, roleColumn, actionsColumn);

        return table;
    }

    private void showAddUserPopup(UserModel user) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(user == null ? "Add New User" : "Edit User");
    
        VBox popupVBox = new VBox();
        popupVBox.setSpacing(10);
    
        Label errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red;");
        errorMessageLabel.setWrapText(true);
        errorMessageLabel.setMaxWidth(380);
    
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
    
        ComboBox<UserRoleEnum> roleComboBox = new ComboBox<>(FXCollections.observableArrayList(UserRoleEnum.values()));
        roleComboBox.setPromptText("Role");
        if (user != null) roleComboBox.setValue(user.getRole());
    
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                errorMessageLabel.setText("");
    
                if (firstNameField.getText().trim().isEmpty() ||
                    lastNameField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty() ||
                    (user == null && passwordField.getText().trim().isEmpty()) ||
                    roleComboBox.getValue() == null) {
                    throw new IllegalArgumentException("All fields are required");
                }
    
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                UserRoleEnum role = roleComboBox.getValue();
    
                UserModel existingUser = userController.getByEmail(email);
                if (existingUser != null && (user == null || !existingUser.getId().equals(user.getId()))) {
                    throw new IllegalStateException("Email address is already in use");
                }
    
                if (user == null) {
                    UserModel newUser = new UserModel(firstName, lastName, email, password, role);
                    userController.addUser(newUser);
                    users.add(newUser);
                } else {
                    if (!password.trim().isEmpty()) {
                        user.setPassword(password);
                    }
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setRole(role);
                    userController.updateUser(user.getId(), user);
                    users.set(users.indexOf(user), user);
                }
                popupStage.close();
    
            } catch (IllegalArgumentException | IllegalStateException ex) {
                errorMessageLabel.setText("Error: " + ex.getMessage());
            } catch (Exception ex) {
                errorMessageLabel.setText("An unexpected error occurred: " + ex.getMessage());
            }
        });
    
        popupVBox.getChildren().addAll(
            errorMessageLabel,
            new Label("First Name:"), firstNameField,
            new Label("Last Name:"), lastNameField,
            new Label("Email:"), emailField,
            new Label("Password:"), passwordField,
            new Label("Role:"), roleComboBox,
            saveButton
        );
    
        Scene popupScene = new Scene(popupVBox, 400, 500);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}
