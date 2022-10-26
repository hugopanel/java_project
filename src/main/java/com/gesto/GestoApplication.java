package com.gesto;

import gesto.api.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GestoApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Database db = new Database("jdbc:ucanaccess://C:\\Users\\hugop\\OneDrive - Efrei\\S5 - Irvine\\Java Programming\\Project\\gesto.accdb");
        db.open();
        // Load the login page
        FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("login-view.fxml"));
        fxmlLoader.setController(new LoginController(db, stage));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Gesto");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}