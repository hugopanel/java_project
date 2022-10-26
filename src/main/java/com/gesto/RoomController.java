package com.gesto;

import gesto.api.types.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;

import gesto.api.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RoomController {
    @FXML
    private ListView lstAvailable;

    @FXML
    private ListView lstWaiting;

    @FXML
    private ListView lstServed;

    @FXML
    private ListView lstCheck;
    private Database db;
    private Stage stage;
    private Scene scene;

    public RoomController(Database db, Stage stage, Scene scene) {
        this.db = db;
        this.stage = stage;
        this.scene = scene;
    }

    public void initialize() {
        // load the tables from the database
        try {
            var tables = db.Tables.getTables();
            ObservableList<String> list = FXCollections.observableArrayList();
            for (Table table: tables)
                list.add(table.getNum());
            lstWaiting.setItems(list);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        // add the event listener
        lstWaiting.setOnMouseClicked(e -> {
            // get the list item's name
            try {
                Table table = db.Tables.getTable((String) lstWaiting.getFocusModel().getFocusedItem());
                FXMLLoader fxmlLoader = new FXMLLoader(GestoApplication.class.getResource("order-view.fxml"));
                fxmlLoader.setController(new OrderController(db, stage, stage.getScene(), table));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void exitWindow() {
        stage.setScene(scene);
        stage.show();
    }
}
