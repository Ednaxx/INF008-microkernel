package br.edu.ifba.inf008.shell.models;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Pattern;

import br.edu.ifba.inf008.shell.util.UserRoleEnum;

public class UserModel implements Serializable {
    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRoleEnum role;
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public UserModel(String firstName, String lastName, String email, String password, UserRoleEnum role) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = lastName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String trimmedEmail = email.trim();
        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = trimmedEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters long");
        }
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return id.equals(userModel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
