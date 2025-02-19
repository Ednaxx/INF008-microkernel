package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.PluginController;
import br.edu.ifba.inf008.shell.controllers.UIController;
import br.edu.ifba.inf008.shell.controllers.UserController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.controllers.BookController;
import br.edu.ifba.inf008.shell.controllers.LoanController;
import br.edu.ifba.inf008.shell.util.EntitySerializer;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;

public class Core extends ICore {
    private final IPluginController pluginController = new PluginController();
    private final UserController userController = new UserController();
    private final IAuthenticationController authenticationController = new AuthenticationController(userController);
    private final BookController bookController = new BookController();
    private final LoanController loanController = new LoanController();

    private UserModel admin;

    private Core() {
        loadData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData));
    }

    private void loadData() {
        userController.getAll().addAll(EntitySerializer.deserializeUsers());
        bookController.getAll().addAll(EntitySerializer.deserializeBooks());
        loanController.getAll().addAll(EntitySerializer.deserializeLoans());

        admin = new UserModel("Admin", "User", "admin@admin.com", "admin", UserRoleEnum.ADMIN);
        userController.addUser(admin);
    }

    private void saveData() {
        userController.deleteUser(admin.getId());

        EntitySerializer.serializeUsers(userController.getAll());
        EntitySerializer.serializeBooks(bookController.getAll());
        EntitySerializer.serializeLoans(loanController.getAll());
    }

    public static boolean init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

        instance = new Core();
        UIController.launch(UIController.class);
        return true;
    }

    public IUIController getUIController() {
        return UIController.getInstance();
    }

    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }

    public IPluginController getPluginController() {
        return pluginController;
    }

    public UserController getUserController() {
        return userController;
    }

    public BookController getBookController() {
        return bookController;
    }
    public LoanController getLoanController() {
        return loanController;
    }
}
