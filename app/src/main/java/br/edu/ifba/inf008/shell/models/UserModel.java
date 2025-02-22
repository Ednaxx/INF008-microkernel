package br.edu.ifba.inf008.shell.models;

import java.io.Serializable;
import java.util.UUID;

import br.edu.ifba.inf008.interfaces.models.IUserModel;
import br.edu.ifba.inf008.interfaces.models.UserRoleEnum;

public class UserModel implements IUserModel, Serializable {
    private final UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRoleEnum role;
    
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
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
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
