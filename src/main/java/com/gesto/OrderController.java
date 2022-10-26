package com.gesto;
//package com.example.app_javafx;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import gesto.api.types.*;
import gesto.api.types.MenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import gesto.api.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private Database db;
    private Stage stage;
    private Scene scene;
    private Table table;
    ObservableList<ItemWithPrice> orderList;

    public OrderController(Database db, Stage stage, Scene scene, Table table) {
        this.db = db;
        this.stage = stage;
        this.scene = scene;
        this.table = table;
    }

    public void initialize() {
        // Set up the interface
        lblTableNum.setText("#" + table.getNum());

        // Set up the TableView
        orderItemColumn.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));

        // Get the menu items
        try {
            List<MenuItem> menuItems = db.MenuItems.getMenuItems();
            orderList = FXCollections.observableArrayList();

            orderList.addListener((ListChangeListener.Change<? extends ItemWithPrice> change) -> {
                while (change.next()) {
                    orderTableView.scrollTo(orderTableView.getItems().size());
                }
            });

            int x1 = 0, y1 = 0;
            int x2 = 0, y2 = 0;
            int x3 = 0, y3 = 0;
            int x4 = 0, y4 = 0;
            int x5 = 0, y5 = 0;
            for (MenuItem menuItem: menuItems) {
                if (menuItem.getAvailable()) {
                    Button button = new Button(menuItem.getName());
                    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    button.setOnMouseClicked(e -> {
                        orderList.add(new ItemWithPrice(menuItem, menuItem.getPrice()));
                    });

                    switch (menuItem.getCategory()) {
                        case APPETIZERS:
                            appetizersGrid.add(button, x1, y1);
                            x1++;
                            if (x1 == appetizersGrid.getColumnCount()) {
                                x1 = 0;
                                y1++;
                            }
                            break;
                        case MAINS:
                            mainsGrid.add(button, x2, y2);
                            x2++;
                            if (x2 == mainsGrid.getColumnCount()) {
                                x2 = 0;
                                y2++;
                            }
                            break;
                        case DESSERTS:
                            dessertsGrid.add(button, x3, y3);
                            x3++;
                            if (x3 == dessertsGrid.getColumnCount()) {
                                x3 = 0;
                                y3++;
                            }
                            break;
                        case DRINKS:
                            drinksGrid.add(button, x4, y4);
                            x4++;
                            if (x4 == drinksGrid.getColumnCount()) {
                                x4 = 0;
                                y4++;
                            }
                            break;
                        case OTHER:
                            othersGrid.add(button, x5, y5);
                            x5++;
                            if (x5 == othersGrid.getColumnCount()) {
                                x5 = 0;
                                y5++;
                            }
                            break;
                    }
                }
            }

            orderTableView.setItems(orderList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void exitScene() {
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Label lblTableNum;

    @FXML
    private TableView orderTableView;

    @FXML
    private TableColumn orderItemColumn;

    @FXML
    private TableColumn orderPriceColumn;

    @FXML
    private Tab dessertsTab;

    @FXML
    private Tab drinksTab;

    @FXML
    private Tab entryTab;

    @FXML
    private BottomNavigationButton launchOrderButton;

    @FXML
    private Tab mainTab;

    @FXML
    private Tab sidesTab;

    @FXML
    private GridPane appetizersGrid;

    @FXML
    private GridPane mainsGrid;

    @FXML
    private GridPane dessertsGrid;

    @FXML
    private GridPane drinksGrid;

    @FXML
    private GridPane othersGrid;

    @FXML
    void launchOrderButtonListener() {
        //le but du bouton est double : envoyer la commande dans la base de données et créer un fichier texte correspondant à la commande
        //il faut chercher les plats dans commandeColumn et leur prix dans prixColumn tout deux font partis de la commandeTable
        List<MenuItem> menuItemList = new ArrayList<>();
        for (ItemWithPrice item: orderList)
            menuItemList.add(item.getMenuItem());

        try {
            // We add the order
            db.Orders.insertOrder(OrderStatus.PREPARING, table, menuItemList);

            // We generate a check
            Check check = db.Orders.getOrders()
                    .get(
                            db.Orders.getOrders()
                                    .size() - 1)
                    .generateCheck(db);
            db.Checks.insertCheck(check.getAmount(), check.getPaymentDate(), check.getOrder());

            // We print the check to a file
            try {
                File f = new File("C:\\Users\\hugop\\OneDrive\\Bureau\\check.txt");
                FileWriter fw = new FileWriter(f, false);
                fw.write("MyRestaurant -- Check\nPowered by Gesto\n\n---------------------\n");
                fw.write("Order on " + check.getPaymentDate() + "\n");
                fw.write("---- ITEMS LIST: ---------\n");
                for (MenuItem item: check.getOrder().getMenuItems(db)) {
                    fw.write(item.getName() + " : " + item.getPrice() + "\n");
                }
                fw.write("==================\nTotal due: " + check.getAmount() + ".");
                fw.write("\n\n\nThank you for dining at MyRestaurant! We hope to see you again soon!");
            } catch (Exception ex) {
                System.out.println(ex);
            }

            // We set the table as unavailable
            table.setAvailable(false);

            // We can close the window
            exitScene();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
