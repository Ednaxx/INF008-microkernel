package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserView extends VBox {
    private final Core core;
    private final AuthenticationController authenticationController;

    public UserView() {
        this.core = (Core) Core.getInstance();
        this.authenticationController = (AuthenticationController) core.getAuthenticationController();
        initializeView();
    }

    private void initializeView() {
        UserModel currentUser = authenticationController.getCurrentUser();
        if (currentUser.getRole() == UserRoleEnum.ADMIN) {
            initializeAdminView();
        } else {
            initializeCustomerView();
        }
    }

    private void initializeAdminView() {
        Label adminLabel = new Label("Admin User View");
        // Add admin-specific user management functionalities here
        getChildren().add(adminLabel);
    }

    private void initializeCustomerView() {
        Label customerLabel = new Label("Customer User View");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            authenticationController.signOut();
            new AuthenticationView((Stage) getScene().getWindow()).show();
        });
        getChildren().addAll(customerLabel, logoutButton);
    }
}