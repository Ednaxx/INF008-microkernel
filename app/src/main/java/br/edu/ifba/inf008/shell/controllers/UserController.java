package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.shell.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static UserController userController;
    private final List<UserModel> users = new ArrayList<>();

    public UserController() {}

    public void init() {
        userController = this;
    }

    public static UserController getInstance() {
        return userController;
    }


    public void addUser(UserModel user) {
        users.add(user);
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public UserModel findUserByUsername(String username) {
        for (UserModel user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
