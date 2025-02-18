package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;

public class AuthenticationController implements IAuthenticationController {
    private final UserController userController;
    private UserModel currentUser;

    public AuthenticationController(UserController userController) {
        this.userController = userController;
        UserModel admin = new UserModel("Admin", "User", "admin@admin.com", "admin", UserRoleEnum.ADMIN);
        userController.addUser(admin);

        // TODO: Remove this
        UserModel customer = new UserModel("Customer", "User", "customer@customer.com", "customer", UserRoleEnum.CUSTOMER);
        userController.addUser(customer);
    }

    @Override
    public boolean signIn(String email, String password) {
        UserModel user = userController.getByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    @Override
    public boolean signOut() {
        if (currentUser != null) {
            currentUser = null;
            return true;
        }
        return false;
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }
}