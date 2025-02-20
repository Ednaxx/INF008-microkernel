package br.edu.ifba.inf008.interfaces;

import java.util.List;
import java.util.UUID;

public interface IUserController<T> {
    void addUser(T user);
    List<T> getAll();
    T getById(UUID id);
    T getByEmail(String email);
    void updateUser(UUID id, T updatedUser);
    void deleteUser(UUID id);
}
