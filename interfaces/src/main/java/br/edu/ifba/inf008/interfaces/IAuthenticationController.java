package br.edu.ifba.inf008.interfaces;

public interface IAuthenticationController<T> {
    boolean signIn(String username, String password);
    void signOut();
    T getCurrentUser();
}
