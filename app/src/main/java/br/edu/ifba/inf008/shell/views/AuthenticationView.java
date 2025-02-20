package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AuthenticationView {
    private final IAuthenticationController<UserModel> authenticationController;
    private final Stage primaryStage;

    public AuthenticationView(Stage primaryStage) {
        this.authenticationController = Core.getInstance().getAuthenticationController();
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        Label errorMessage = new Label();
        errorMessage.setStyle("-fx-text-fill: red;");
        GridPane.setConstraints(errorMessage, 1, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            if (authenticationController.signIn(email, password)) {
                new MainView(primaryStage).show();
            } else {
                errorMessage.setText("Login failed. Please check your email and password.");
            }
        });

        loginButton.setDefaultButton(true);

        grid.getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton, errorMessage);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}