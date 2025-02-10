package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.*;
import br.edu.ifba.inf008.shell.controllers.AuthenticationController;
import br.edu.ifba.inf008.shell.controllers.IOController;
import br.edu.ifba.inf008.shell.controllers.PluginController;
import br.edu.ifba.inf008.shell.controllers.UIController;

public class Core extends ICore
{
    private final IAuthenticationController authenticationController = new AuthenticationController();
    private final IIOController ioController = new IOController();
    private final IPluginController pluginController = new PluginController();

    private Core() {}

    public static boolean init() {
	if (instance != null) {
	    System.out.println("Fatal error: core is already initialized!");
	    System.exit(-1);
	}

	instance = new Core();
        UIController.launch(UIController.class);
        return true;
    }
    
    public IUIController getUIController() {
        return UIController.getInstance();
    }
    
    public IAuthenticationController getAuthenticationController() {
        return authenticationController;
    }
    
    public IIOController getIOController() {
        return ioController;
    }
    
    public IPluginController getPluginController() {
        return pluginController;
    }
}
