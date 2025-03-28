package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.function.Supplier;

public class MainPage implements PropertyChangeListener, IView{
    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;
    TableView<Item> itemTable;

    public MainPage(IView view) {
        this.view = view;
        this.controller = view.getController();
        this.stage = view.getStage();
    }
    @Override
    public void setController(Controller controller) {
        view.setController(controller);
    }
    @Override
    public Controller getController() {
        return controller;
    }
    @Override
    public Stage getStage(){
        return stage;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChangeEvent received in MainPage: " + evt.getPropertyName());

        switch (evt.getPropertyName()) {
            case "show-allItems":
                ArrayList<Item> updatedItems = (ArrayList<Item>) evt.getNewValue();
                showAllItems(updatedItems);
                break;
            default:
                break;
        }
    }

    //Shouldn't be usable
    @Override
    public void launchApp() {
        throw new UnsupportedOperationException("Not usable in this view.");
    }

    @Override
    public void stopApp() {
        Platform.exit();
    }

    @Override
    public void showPrincipalWindow(){
        actualParent = new VBox();
        actualParent.setPadding(new Insets(10,10,10,10));
        Supplier<String[]> supplier = () -> new String[]{""};

        HBox filtringBox1 = new HBox(20);
        filtringBox1.setAlignment(Pos.CENTER);
        //CheckBox check1 = new CheckBox("Vaulted");
        //CheckBox check2 = new CheckBox("Ressurgence");
        CheckBox check3 = new CheckBox("Min");
        check3.setOnAction(controller.generateEventHandlerAction("select-min",supplier));
        CheckBox check4 = new CheckBox("Max");
        check4.setOnAction(controller.generateEventHandlerAction("select-max",supplier));
        TextField minField = new TextField();
        minField.textProperty().addListener((observable, oldValue, newValue) -> controller.minFieldChange(newValue,"item"));
        TextField maxField = new TextField();
        maxField.textProperty().addListener((observable, oldValue, newValue) -> controller.maxFieldChange(newValue,"item"));
        CheckBox check5 = new CheckBox("Average Min");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMin",supplier));
        TextField averageMinField = new TextField();
        averageMinField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMinFieldChange(newValue,"item"));
        CheckBox check1 = new CheckBox("Average Max");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMax",supplier));
        TextField averageMaxField = new TextField();
        averageMaxField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMaxFieldChange(newValue,"item"));
        filtringBox1.getChildren().addAll(check3, minField, check4, maxField, check5, averageMinField, check1, averageMaxField);

        HBox filtringBox2 = new HBox(20);
        filtringBox2.setAlignment(Pos.CENTER);
        CheckBox check6 = new CheckBox("Items");
        check6.setSelected(true);
        check6.setOnAction(controller.generateEventHandlerAction("select-item",supplier));
        CheckBox check7 = new CheckBox("Prime");
        check7.setSelected(true);
        check7.setOnAction(controller.generateEventHandlerAction("select-prime",supplier));
        CheckBox check8 = new CheckBox("Mods");
        check8.setSelected(true);
        check8.setOnAction(controller.generateEventHandlerAction("select-mod",supplier));
        CheckBox check9 = new CheckBox("Riven");
        check9.setSelected(true);
        check9.setOnAction(controller.generateEventHandlerAction("select-riven",supplier));
        CheckBox check10 = new CheckBox("Rarity");
        check10.setOnAction(controller.generateEventHandlerAction("select-rarity",supplier));
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("10", "25", "45", "65", "100"));
        rarityCombo.valueProperty().addListener((observable, oldValue, newValue) -> {controller.rarityFieldChange(newValue);});
        filtringBox2.getChildren().addAll(check6, check7, check8, check9, check10, rarityCombo);


        itemTable = new TableView<>();
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getItemType(cellData.getValue()))
        );

        TableColumn<Item, String> rarityCol = new TableColumn<>("Rarity");
        rarityCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getItemRarity(cellData.getValue()))
        );

        TableColumn<Item, String> rerollCol = new TableColumn<>("Reroll");
        rerollCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getItemReroll(cellData.getValue()))
        );

        TableColumn<Item, String> polarityCol = new TableColumn<>("Polarity");
        polarityCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getItemPolarity(cellData.getValue()))
        );

        itemTable.getColumns().addAll(nameCol, priceCol, typeCol, rarityCol, rerollCol, polarityCol );

        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        Button newItem = new Button("New Item");
        newItem.setOnAction(controller.generateEventHandlerAction("view-newItem",supplier));
        Button modifyItem = new Button("Modify");
        modifyItem.setOnAction(event -> {
            Item select = itemTable.getSelectionModel().getSelectedItem();
            if (select != null) {
                Supplier<String[]> supplierModify = select::toStringArray;
                controller.generateEventHandlerAction("modify-item", supplierModify).handle(event);
            } else {
                System.out.println("No item selected to modify.");
            }
        });
        Button deleteItem = new Button("Delete");
        deleteItem.setOnAction(event -> {
            Item select = itemTable.getSelectionModel().getSelectedItem();
            if (select != null) {
                Supplier<String[]> supplierDelete = () -> new String[]{String.valueOf(select.getId())};
                controller.generateEventHandlerAction("delete-item",supplierDelete).handle(event);
            } else {
                System.out.println("No item selected for deletion.");
            }
        });
        Button relics = new Button("Relics");
        relics.setOnAction(controller.generateEventHandlerAction("view-relics",supplier));
        Button primeSet = new Button("Prime Set");
        primeSet.setOnAction(controller.generateEventHandlerAction("view-primeSets",supplier));
        buttonBox.getChildren().addAll(newItem, modifyItem, deleteItem, relics, primeSet);

        actualParent.getChildren().addAll(filtringBox1, filtringBox2, itemTable, buttonBox);
        controller.showAllItems();

        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }

    public void showAllItems(ArrayList<Item> listItems) {
        if (itemTable != null) {
            itemTable.getItems().setAll(listItems);
        }
    }

}
