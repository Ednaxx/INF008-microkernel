package br.edu.ifba.inf008.interfaces.controllers;

public interface IAuthenticationController<T> {
    boolean signIn(String username, String password);
    void signOut();
    T getCurrentUser();
}
