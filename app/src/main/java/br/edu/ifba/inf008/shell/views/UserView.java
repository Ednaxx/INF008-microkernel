package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.UserController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.layout.VBox;

public class UserView extends VBox {
    private final Core core;
    private final AuthenticationController authenticationController;
    private final UserController userController;

    public UserView() {
        this.core = (Core) Core.getInstance();
        this.authenticationController = (AuthenticationController) core.getAuthenticationController();
        this.userController = core.getUserController();
        initializeView();
    }

    private void initializeView() {
        UserModel currentUser = authenticationController.getCurrentUser();
        if (currentUser.getRole() == UserRoleEnum.ADMIN) {
            getChildren().add(new AdminUserView(userController));
        } else {
            getChildren().add(new CustomerUserView(authenticationController));
        }
    }
}