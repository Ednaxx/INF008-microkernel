package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.PluginController;
import br.edu.ifba.inf008.shell.controllers.UIController;
import br.edu.ifba.inf008.shell.controllers.UserController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.models.BookModel;
import br.edu.ifba.inf008.shell.models.LoanModel;
import br.edu.ifba.inf008.shell.controllers.BookController;
import br.edu.ifba.inf008.shell.controllers.LoanController;
import br.edu.ifba.inf008.shell.util.EntitySerializer;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;

public class Core extends ICore {
    private final IPluginController pluginController = new PluginController();
    private final IUserController<UserModel> userController = new UserController();
    private final IAuthenticationController<UserModel> authenticationController = new AuthenticationController(userController);
    private final IBookController<BookModel> bookController = new BookController();
    private final ILoanController<LoanModel, UserModel, BookModel> loanController = new LoanController();

    private Core() {
        loadData();
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveData));
    }

    private void createDefaultAdmin() {
        UserModel defaultAdminUser = new UserModel("Admin", "User", "admin@admin.com", "admin", UserRoleEnum.ADMIN);
        userController.addUser(defaultAdminUser);
    }

    private boolean hasAdminUser() {
        return userController.getAll().stream()
            .anyMatch(user -> user.getRole() == UserRoleEnum.ADMIN);
    }

    private void loadData() {
        userController.getAll().addAll(EntitySerializer.deserializeUsers());
        bookController.getAll().addAll(EntitySerializer.deserializeBooks());
        loanController.getAll().addAll(EntitySerializer.deserializeLoans());

        if (!hasAdminUser()) createDefaultAdmin();
    }

    private void saveData() {
        EntitySerializer.serializeUsers(userController.getAll());
        EntitySerializer.serializeBooks(bookController.getAll());
        EntitySerializer.serializeLoans(loanController.getAll());
    }

    public static void init() {
        if (instance != null) {
            System.out.println("Fatal error: core is already initialized!");
            System.exit(-1);
        }

        instance = new Core();
        UIController.launch(UIController.class);
    }

    public IUIController getUIController() {
        return UIController.getInstance();
    }

    public IPluginController getPluginController() {
        return pluginController;
    }

    @SuppressWarnings("unchecked")
    public IAuthenticationController<UserModel> getAuthenticationController() {
        return authenticationController;
    }

    @SuppressWarnings("unchecked")
    public IUserController<UserModel> getUserController() {
        return userController;
    }

    @SuppressWarnings("unchecked")
    public IBookController<BookModel> getBookController() {
        return bookController;
    }

    @SuppressWarnings("unchecked")
    public ILoanController<LoanModel, UserModel, BookModel> getLoanController() {
        return loanController;
    }
}
