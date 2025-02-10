package br.edu.ifba.inf008.interfaces;

public interface IAuthenticationController {
    public boolean signIn(String username, String password);
    public boolean signOut();
    public boolean signUp(String username, String password);
}
