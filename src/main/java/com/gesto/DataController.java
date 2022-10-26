package com.gesto;

import gesto.api.Database;
import gesto.api.types.Check;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DataController {
    private Database db;
    private Stage stage;
    private Scene scene;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblOrders;

    public DataController(Database db, Stage stage, Scene scene) {
        this.db = db;
        this.stage = stage;
        this.scene = scene;
    }

    public void initialize() {
        try {
            // Get the number of orders
            lblOrders.setText("Orders: " + db.Orders.getOrders().size());

            // Get the total amount
            var checks = db.Checks.getChecks();
            double sum = 0;
            for (Check check: checks) {
                sum += check.getAmount();
            }
            lblTotal.setText("Total: " + sum);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void exitScene() {
        stage.setScene(scene);
        stage.show();
    }
}
