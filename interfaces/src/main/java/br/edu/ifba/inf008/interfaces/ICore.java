package br.edu.ifba.inf008.interfaces;

public abstract class ICore {
    protected static ICore instance = null;

    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract IAuthenticationController getAuthenticationController();
    public abstract IIOController getIOController();
    public abstract IPluginController getPluginController();
}
