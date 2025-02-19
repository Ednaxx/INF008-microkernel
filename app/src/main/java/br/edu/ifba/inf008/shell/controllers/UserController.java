package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.shell.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserController {
    private List<UserModel> users = new ArrayList<>();

    public UserController() {}

    public void addUser(UserModel user) {
        users.add(user);
    }

    public List<UserModel> getAll() {
        return users;
    }

    public UserModel getById(UUID id) {
        for (UserModel user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public UserModel getByEmail(String email) {
        for (UserModel user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void updateUser(UUID id, UserModel updatedUser) {
        for (UserModel user : users) {
            if (user.getId().equals(id)) {
                user.setFirstName(updatedUser.getFirstName());
                user.setLastName(updatedUser.getLastName());
                user.setEmail(updatedUser.getEmail());
            }
        }
    }

    public void deleteUser(UUID id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}