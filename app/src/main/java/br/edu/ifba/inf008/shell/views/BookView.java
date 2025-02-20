package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.interfaces.IBookController;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;
import javafx.scene.layout.VBox;

public class BookView extends VBox {
    private final IAuthenticationController<UserModel> authenticationController;
    private final IBookController<BookModel> bookController;

    public BookView() {
        ICore core = Core.getInstance();
        this.authenticationController = core.getAuthenticationController();
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
