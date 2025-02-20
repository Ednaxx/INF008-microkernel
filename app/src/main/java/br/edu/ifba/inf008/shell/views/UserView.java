package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.layout.VBox;

public class UserView extends VBox {
    private final IAuthenticationController<UserModel> authenticationController;

    public UserView() {
        this.authenticationController =  Core.getInstance().getAuthenticationController();
        initializeView();
    }

    private void initializeView() {
        UserModel currentUser = authenticationController.getCurrentUser();
        if (currentUser.getRole() == UserRoleEnum.ADMIN) {
            getChildren().add(new AdminUserView());
        } else {
            getChildren().add(new CustomerUserView());
        }
    }
}