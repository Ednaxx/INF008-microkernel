package br.edu.ifba.inf008.shell.controllers;

import br.edu.ifba.inf008.interfaces.controllers.IUIController;
import br.edu.ifba.inf008.shell.views.AuthenticationView;
import javafx.application.Application;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.Node;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class UIController extends Application implements IUIController {
    private MenuBar menuBar;
    private TabPane tabPane;
    private static UIController uiController;
    private final List<PendingTab> pendingTabs = new ArrayList<>();

    private static class PendingTab {
        String text;
        Node content;

        PendingTab(String text, Node content) {
            this.text = text;
            this.content = content;
        }
    }

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
        new AuthenticationView(primaryStage).show();
        PluginController.init();
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
        for (PendingTab pendingTab : pendingTabs) {
            createTab(pendingTab.text, pendingTab.content);
        }
        pendingTabs.clear();
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
        if (tabPane == null) {
            pendingTabs.add(new PendingTab(tabText, contents));
            return true;
        }

        Tab tab = new Tab();
        tab.setText(tabText);
        tab.setContent(contents);
        tab.setClosable(false);
        tabPane.getTabs().add(tab);

        return true;
    }
}
