package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.IUserController;
import br.edu.ifba.inf008.shell.models.UserModel;
import br.edu.ifba.inf008.shell.util.UserRoleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserController implements IUserController<UserModel, UserRoleEnum> {
    private final List<UserModel> users = new ArrayList<>();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public UserController() {}

    @Override
    public void addUser(String firstName, String lastName, String email, String password, UserRoleEnum role) {
        simpleUserValidation(firstName, lastName, email, role);

        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        if (getByEmail(email) != null) {
            throw new IllegalStateException("A user with email " + email + " already exists");
        }

        UserModel newUser = new UserModel(
            firstName.trim(),
            lastName.trim(),
                email,
            password,
            role
        );
        
        users.add(newUser);
    }

    public void addUser(UserModel user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        simpleUserValidation(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());

        if (user.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }

        if (getByEmail(user.getEmail()) != null) {
            throw new IllegalStateException("A user with email " + user.getEmail() + " already exists");
        }

        users.add(user);
    }

    @Override
    public List<UserModel> getAll() {
        return users;
    }

    @Override
    public UserModel getById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return users.stream()
                   .filter(user -> user.getId().equals(id))
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public UserModel getByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        return users.stream()
                   .filter(user -> user.getEmail().equals(email.trim()))
                   .findFirst()
                   .orElse(null);
    }

    @Override
    public void updateUser(UUID id, String firstName, String lastName, String email, String password, UserRoleEnum role) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        simpleUserValidation(firstName, lastName, email, role);

        UserModel existingUser = getById(id);
        if (existingUser == null) {
            throw new IllegalStateException("User with ID " + id + " not found");
        }

        UserModel userWithEmail = getByEmail(email);
        if (userWithEmail != null && !userWithEmail.getId().equals(id)) {
            throw new IllegalStateException("Cannot update: A user with email " + email + " already exists");
        }

        if (password != null && !password.trim().isEmpty()) {
            if (password.length() < 4) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }
            existingUser.setPassword(password);
        }

        existingUser.setFirstName(firstName.trim());
        existingUser.setLastName(lastName.trim());
        existingUser.setEmail(email);
        existingUser.setRole(role);
    }

    public void updateUser(UUID id, UserModel user) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        simpleUserValidation(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());

        UserModel existingUser = getById(id);
        if (existingUser == null) {
            throw new IllegalStateException("User with ID " + id + " not found");
        }

        UserModel userWithEmail = getByEmail(user.getEmail());
        if (userWithEmail != null && !userWithEmail.getId().equals(id)) {
            throw new IllegalStateException("Cannot update: A user with email " + user.getEmail() + " already exists");
        }

        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            if (user.getPassword().length() < 4) {
                throw new IllegalArgumentException("Password must be at least 4 characters long");
            }
            existingUser.setPassword(user.getPassword());
        }

        existingUser.setFirstName(user.getFirstName().trim());
        existingUser.setLastName(user.getLastName().trim());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
    }

    @Override
    public void deleteUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        users.removeIf(user -> user.getId().equals(id));
    }

    private void simpleUserValidation(String firstName, String lastName, String email, UserRoleEnum role) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        String trimmedEmail = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}