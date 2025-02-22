package br.edu.ifba.inf008.shell.views;

import br.edu.ifba.inf008.shell.controllers.UIController;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    private final Stage primaryStage;

    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Main Screen");

        // Create main tab pane for core functionality
        TabPane mainTabPane = new TabPane();
        mainTabPane.setId("mainTabPane");

        TabPane pluginTabPane = new TabPane();
        pluginTabPane.setId("pluginTabPane");
        pluginTabPane.setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #ccc;");

        Tab bookTab = new Tab("Library", new BookView());
        bookTab.setClosable(false);
        Tab userTab = new Tab("User", new UserView());
        userTab.setClosable(false);

        mainTabPane.getTabs().addAll(bookTab, userTab);
        
        mainTabPane.getSelectionModel().select(bookTab);

        UIController uiController = UIController.getInstance();
        uiController.setTabPane(pluginTabPane);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(mainTabPane, pluginTabPane);

        Scene scene = new Scene(layout, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
