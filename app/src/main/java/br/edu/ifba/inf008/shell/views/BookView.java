package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BookView extends VBox {
    private final Core core;
    private final AuthenticationController authenticationController;

    public BookView() {
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
        Label adminLabel = new Label("Admin Book View");
        // Add admin-specific book management functionalities here
        getChildren().add(adminLabel);
    }

    private void initializeCustomerView() {
        Label customerLabel = new Label("Customer Book View");
        // Add customer-specific book viewing and renting functionalities here
        getChildren().add(customerLabel);
    }
}