package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.IAuthenticationController;
import br.edu.ifba.inf008.shell.models.UserModel;

public class AuthenticationController implements IAuthenticationController {
    private final UserController userController;
    private UserModel currentUser;

    public AuthenticationController(UserController userController) {
        this.userController = userController;
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