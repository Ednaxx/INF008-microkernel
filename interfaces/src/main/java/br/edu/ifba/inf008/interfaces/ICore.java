package br.edu.ifba.inf008.interfaces;

import br.edu.ifba.inf008.interfaces.controllers.*;

public abstract class ICore {
    protected static ICore instance = null;

    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract IPluginController getPluginController();
    public abstract <T> IAuthenticationController<T> getAuthenticationController();
    public abstract <T, R> IUserController<T, R> getUserController();
    public abstract <T, G> IBookController<T, G> getBookController();
    public abstract <L, U, B> ILoanController<L, U, B> getLoanController();
}
