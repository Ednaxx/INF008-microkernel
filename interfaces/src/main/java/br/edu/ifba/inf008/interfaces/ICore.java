package br.edu.ifba.inf008.interfaces;

public abstract class ICore {
    protected static ICore instance = null;

    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract <T> IAuthenticationController<T> getAuthenticationController();
    public abstract IPluginController getPluginController();
    public abstract <T> IUserController<T> getUserController();
    public abstract <T> IBookController<T> getBookController();
    public abstract <L, U, B> ILoanController<L, U, B> getLoanController();
}
