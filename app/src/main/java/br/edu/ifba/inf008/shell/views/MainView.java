package br.edu.ifba.inf008.shell.views;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainView {
    private final Stage primaryStage;

    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Main Screen");

        TabPane tabPane = new TabPane();

        Tab bookTab = new Tab("Library", new BookView());
        bookTab.setClosable(false);
        Tab userTab = new Tab("User", new UserView());
        userTab.setClosable(false);

        tabPane.getTabs().addAll(bookTab, userTab);

        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}