package br.edu.ifba.inf008.shell.views;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomerBookView extends VBox {

    public CustomerBookView() {
        initializeView();
    }

    private void initializeView() {
        Label customerLabel = new Label("Customer Book View");
        // Add customer-specific book viewing and renting functionalities here
        getChildren().add(customerLabel);
    }
}
