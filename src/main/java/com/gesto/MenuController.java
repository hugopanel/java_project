package com.gesto;

import gesto.api.Database;
import gesto.api.types.MenuItem;
import gesto.api.types.MenuItemCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import com.gluonhq.charm.glisten.control.CardPane;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.gluonhq.charm.glisten.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class MenuController {

    @FXML
    private CardPane<Label> listView;

    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button removeBtn;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TabPane TabPane;

    @FXML
    private TableView<ItemWithPrice> tableAppetizers;

    @FXML
    private TableView<ItemWithPrice> tableMains;

    @FXML
    private TableView<ItemWithPrice> tableDesserts;

    @FXML
    private TableView<ItemWithPrice> tableDrinks;

    @FXML
    private TableView<ItemWithPrice> tableOthers;

    @FXML
    private TableColumn clmnNameAppetizers;
    @FXML
    private TableColumn clmnPriceAppetizers;
    @FXML
    private TableColumn clmnNameMains;
    @FXML
    private TableColumn clmnPriceMains;
    @FXML
    private TableColumn clmnNameDesserts;
    @FXML
    private TableColumn clmnPriceDesserts;
    @FXML
    private TableColumn clmnNameDrinks;
    @FXML
    private TableColumn clmnPriceDrinks;
    @FXML
    private TableColumn clmnNameOthers;
    @FXML
    private TableColumn clmnPriceOthers;

    private Database db;
    private Stage stage;
    private Scene scene;

    private MenuItem selectedItem;

    ObservableList<ItemWithPrice> appetizersList = FXCollections.observableArrayList();
    ObservableList<ItemWithPrice> mainsList = FXCollections.observableArrayList();
    ObservableList<ItemWithPrice> dessertsList = FXCollections.observableArrayList();
    ObservableList<ItemWithPrice> drinksList = FXCollections.observableArrayList();
    ObservableList<ItemWithPrice> othersList = FXCollections.observableArrayList();

    public MenuController(Database db, Stage stage, Scene scene){
        this.db = db;
        this.stage = stage;
        this.scene = scene;
    }

    public void initialize() {
        // Initialize the TableViews
        clmnNameAppetizers.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        clmnPriceAppetizers.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        clmnNameMains.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        clmnPriceMains.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        clmnNameDesserts.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        clmnPriceDesserts.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        clmnNameDrinks.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        clmnPriceDrinks.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        clmnNameOthers.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        clmnPriceOthers.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));

        tableAppetizers.setOnMouseClicked(mouseEvent -> {handleMouseClick(mouseEvent, tableAppetizers.getFocusModel().getFocusedItem());});
        tableMains.setOnMouseClicked(mouseEvent -> {handleMouseClick(mouseEvent, tableMains.getFocusModel().getFocusedItem());});
        tableDesserts.setOnMouseClicked(mouseEvent -> {handleMouseClick(mouseEvent, tableDesserts.getFocusModel().getFocusedItem());});
        tableDrinks.setOnMouseClicked(mouseEvent -> {handleMouseClick(mouseEvent, tableDrinks.getFocusModel().getFocusedItem());});
        tableOthers.setOnMouseClicked(mouseEvent -> {handleMouseClick(mouseEvent, tableOthers.getFocusModel().getFocusedItem());});

        // Populate the TableViews
        populateTables();
        
        // Add the event handlers for the buttons
        addBtn.setOnMouseClicked(e -> {
            // Add the new menu item to the database
            try {
                db.MenuItems.insertMenuItem(
                    txtName.getText(),
                    MenuItemCategory.values()[TabPane.getSelectionModel().getSelectedIndex()],
                    true,
                    Double.parseDouble(txtPrice.getText())
                );
                populateTables();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });

        updateBtn.setOnMouseClicked(e -> {
            // Update the selected menu item
            try {
                db.MenuItems.updateMenuItem(selectedItem.getID(),
                        new MenuItem(
                                selectedItem.getID(),
                                txtName.getText(),
                                MenuItemCategory.values()[TabPane.getSelectionModel().getSelectedIndex()],
                                true,
                                Double.parseDouble(txtPrice.getText())
                        ));
                populateTables();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });

        removeBtn.setOnMouseClicked(e -> {
            // Remove the selected menu item
            try {
                db.MenuItems.deleteMenuItem(selectedItem.getID());
                populateTables();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        });
    }

    private void populateTables() {
        appetizersList.clear();
        mainsList.clear();
        dessertsList.clear();
        drinksList.clear();
        othersList.clear();

        try {
            for (MenuItem item: db.MenuItems.getMenuItems()) {
                ItemWithPrice itemWithPrice = new ItemWithPrice(item, item.getPrice());

                switch (item.getCategory()) {
                    case APPETIZERS -> appetizersList.add(itemWithPrice);
                    case MAINS -> mainsList.add(itemWithPrice);
                    case DESSERTS -> dessertsList.add(itemWithPrice);
                    case DRINKS -> drinksList.add(itemWithPrice);
                    case OTHER -> othersList.add(itemWithPrice);
                }

                tableAppetizers.setItems(appetizersList);
                tableMains.setItems(mainsList);
                tableDesserts.setItems(dessertsList);
                tableDrinks.setItems(drinksList);
                tableOthers.setItems(othersList);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void handleMouseClick(MouseEvent event, ItemWithPrice focusedItem) {
        txtName.setText(focusedItem.getItemName());
        txtPrice.setText(Double.toString(focusedItem.getItemPrice()));
        selectedItem = focusedItem.getMenuItem();
    }

    @FXML
    private void exitWindow() {
        stage.setScene(scene);
        stage.show();
    }
}


