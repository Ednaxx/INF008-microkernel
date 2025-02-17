package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.BookController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.layout.VBox;

public class BookView extends VBox {
    private final Core core;
    private final AuthenticationController authenticationController;
    private final BookController bookController;

    public BookView() {
        this.core = (Core) Core.getInstance();
        this.authenticationController = (AuthenticationController) core.getAuthenticationController();
        this.bookController = core.getBookController();
        initializeView();
    }

    private void initializeView() {
        UserModel currentUser = authenticationController.getCurrentUser();
        if (currentUser.getRole() == UserRoleEnum.ADMIN) {
            getChildren().add(new AdminBookView(bookController));
        } else {
            getChildren().add(new CustomerBookView());
        }
    }
}
