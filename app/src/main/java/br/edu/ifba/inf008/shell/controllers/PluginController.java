package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.App;
import br.edu.ifba.inf008.interfaces.IPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class PluginController {
    public static boolean init() {
        try {
            File currentDir = new File("./plugins");
            System.out.println("Plugin directory path: " + currentDir.getAbsolutePath());

            if (!currentDir.exists()) {
                System.out.println("Plugins directory does not exist. Creating...");
                if (!currentDir.mkdirs()) {
                    System.err.println("Failed to create plugins directory");
                    return false;
                }
            }

            FilenameFilter jarFilter = (dir, name) -> name.toLowerCase().endsWith(".jar");
            File[] pluginFiles = currentDir.listFiles(jarFilter);

            if (pluginFiles == null || pluginFiles.length == 0) {
                System.out.println("No plugins found");
                return true;
            }

            List<URL> jarUrls = new ArrayList<>();
            for (File file : pluginFiles) {
                System.out.println("Found plugin: " + file.getName());
                jarUrls.add(file.toURI().toURL());
            }

            URLClassLoader pluginClassLoader = new URLClassLoader(
                jarUrls.toArray(new URL[0]),
                App.class.getClassLoader()
            );

            for (File pluginFile : pluginFiles) {
                String pluginName = pluginFile.getName().split("\\.")[0];
                String fullClassName = "br.edu.ifba.inf008.plugins." + pluginName;

                try {
                    Class<?> pluginClass = Class.forName(fullClassName, true, pluginClassLoader);
                    IPlugin plugin = (IPlugin) pluginClass.getDeclaredConstructor().newInstance();
                    System.out.println("Successfully loaded plugin: " + pluginName);
                    plugin.init();
                } catch (ClassNotFoundException e) {
                    System.err.println("Plugin class not found: " + fullClassName);
                } catch (Exception e) {
                    System.err.println("Failed to load plugin " + pluginName + ": " + e);
                    e.printStackTrace();
                }
            }

            return true;

        } catch (Exception e) {
            System.err.println("Plugin initialization error: " + e.getClass().getName() + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}