package com.gesto;

import gesto.api.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AppController {
    private Stage stage;
    private Database db;
    public AppController(Database db, Stage stage) {
        this.db = db;
        this.stage = stage;
    }

    @FXML
    private Button btnRoom;

    @FXML
    private Button btnMenu;

    @FXML
    private Button btnData;

    @FXML
    private void onClickRoomButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("room-view.fxml"));
            fxmlLoader.setController(new RoomController(db, stage, stage.getScene()));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void onClickMenuButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("menu-view.fxml"));
            fxmlLoader.setController(new MenuController(db, stage, stage.getScene()));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void onClickDataButton() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("data-view.fxml"));
            fxmlLoader.setController(new DataController(db, stage, stage.getScene()));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
