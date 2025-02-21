package br.edu.ifba.inf008.interfaces;

import java.util.List;
import java.util.UUID;

public interface IUserController<T, R> {
    void addUser(String firstName, String lastName, String email, String password, R role);
    List<T> getAll();
    T getById(UUID id);
    T getByEmail(String email);
    void updateUser(UUID id, String firstName, String lastName, String email, String password, R role);
    void deleteUser(UUID id);
}
