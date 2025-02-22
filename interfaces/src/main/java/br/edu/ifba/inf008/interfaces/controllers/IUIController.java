package br.edu.ifba.inf008.interfaces.controllers;

import javafx.scene.control.MenuItem;
import javafx.scene.Node;

public interface IUIController {
    MenuItem createMenuItem(String menuText, String menuItemText);
    boolean createTab(String tabText, Node contents);
}
