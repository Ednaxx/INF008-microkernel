package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.interfaces.controllers.IAuthenticationController;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.models.UserModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUserView extends VBox {
    private final IAuthenticationController<UserModel> authenticationController;

    public CustomerUserView() {
        authenticationController = Core.getInstance().getAuthenticationController();
        initializeView();
    }

    private void initializeView() {
        Label customerLabel = new Label("Customer User View");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            authenticationController.signOut();
            new AuthenticationView((Stage) getScene().getWindow()).show();
        });
        getChildren().addAll(customerLabel, logoutButton);
    }
}
