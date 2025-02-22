package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.controllers.IPluginController;
import br.edu.ifba.inf008.interfaces.IPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginController implements IPluginController {
    public boolean init() {
        try {
            File currentDir = new File("./plugins");

            FilenameFilter jarFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".jar");
                }
            };

            String[] plugins = currentDir.list(jarFilter);

            if (plugins == null) return true;

            int i;
            URL[] jars = new URL[plugins.length];

            for (i = 0; i < plugins.length; i++) {
                jars[i] = (new File("./plugins/" + plugins[i])).toURI().toURL();
            }

            URLClassLoader ulc = new URLClassLoader(jars, App.class.getClassLoader());
            for (i = 0; i < plugins.length; i++) {
                String pluginName = plugins[i].split("\\.")[0];
                IPlugin plugin = (IPlugin) Class.forName("br.edu.ifba.inf008.plugins." + pluginName, true, ulc).getDeclaredConstructor().newInstance();
                plugin.init();
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getClass().getName() + " - " + e.getMessage());
            return false;
        }
    }
}
