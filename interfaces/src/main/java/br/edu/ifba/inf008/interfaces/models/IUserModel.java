package br.edu.ifba.inf008.interfaces.models;

import java.util.UUID;

public interface IUserModel {
    UUID getId();
    
    String getFirstName();
    void setFirstName(String firstName);
    
    String getLastName();
    void setLastName(String lastName);
    
    String getEmail();
    void setEmail(String email);
    
    String getPassword();
    void setPassword(String password);
    
    UserRoleEnum getRole();
    void setRole(UserRoleEnum role);
}
