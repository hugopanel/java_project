package com.gesto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    public HelloController() {
        System.out.println("HelloController loaded!!!!!!!!!ewjafklseanflakemsds");
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}