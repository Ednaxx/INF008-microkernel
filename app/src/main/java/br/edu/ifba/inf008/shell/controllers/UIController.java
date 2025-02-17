package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.shell.Core;
import br.edu.ifba.inf008.shell.views.AuthenticationView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.geometry.Side;
import javafx.scene.Node;

import java.util.Objects;

public class UIController extends Application implements IUIController {
    private MenuBar menuBar;
    private TabPane tabPane;
    private static UIController uiController;

    public UIController() {}

    @Override
    public void init() {
        uiController = this;
    }

    public static UIController getInstance() {
        return uiController;
    }

    @Override
    public void start(Stage primaryStage) {
        ICore core = Core.getInstance();
        AuthenticationController authenticationController = (AuthenticationController) core.getAuthenticationController();
        new AuthenticationView(authenticationController, primaryStage).show();
    }

    public MenuItem createMenuItem(String menuText, String menuItemText) {
        Menu newMenu = null;
        for (Menu menu : menuBar.getMenus()) {
            if (Objects.equals(menu.getText(), menuText)) {
                newMenu = menu;
                break;
            }
        }
        if (newMenu == null) {
            newMenu = new Menu(menuText);
            menuBar.getMenus().add(newMenu);
        }

        MenuItem menuItem = new MenuItem(menuItemText);
        newMenu.getItems().add(menuItem);

        return menuItem;
    }

    public boolean createTab(String tabText, Node contents) {
        Tab tab = new Tab();
        tab.setText(tabText);
        tab.setContent(contents);
        tabPane.getTabs().add(tab);

        return true;
    }
}