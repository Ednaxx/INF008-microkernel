package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.models.UserRoleEnum;
import br.edu.ifba.inf008.interfaces.controllers.IAuthenticationController;
import br.edu.ifba.inf008.interfaces.controllers.IUserController;
import br.edu.ifba.inf008.shell.models.UserModel;

public class AuthenticationController implements IAuthenticationController<UserModel> {
    private final IUserController<UserModel, UserRoleEnum> userController;
    private UserModel currentUser;

    public AuthenticationController(IUserController<UserModel, UserRoleEnum> userController) {
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
    public void signOut() {
        if (currentUser != null) {
            currentUser = null;
        }
    }

    public UserModel getCurrentUser() {
        return currentUser;
    }
}