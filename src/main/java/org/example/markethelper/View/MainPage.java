package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
                Platform.runLater(() -> showAllItems(updatedItems));
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
        Supplier<String[]> supplier = () -> new String[]{""};

        HBox filtringBox1 = new HBox(20);
        filtringBox1.setAlignment(Pos.CENTER);
        //CheckBox check1 = new CheckBox("Vaulted");
        //CheckBox check2 = new CheckBox("Ressurgence");
        CheckBox check3 = new CheckBox("Min");
        CheckBox check4 = new CheckBox("Max");
        TextField minField = new TextField();
        TextField maxField = new TextField();
        filtringBox1.getChildren().addAll(check3, minField, check4, maxField);

        HBox filtringBox2 = new HBox(20);
        filtringBox2.setAlignment(Pos.CENTER);
        CheckBox check5 = new CheckBox("Average");
        TextField averageField = new TextField();
        CheckBox check6 = new CheckBox("Items");
        CheckBox check7 = new CheckBox("Prime");
        CheckBox check8 = new CheckBox("Mods");
        CheckBox check9 = new CheckBox("Riven");
        CheckBox check10 = new CheckBox("Rarity");
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("10", "25", "45", "65", "100"));
        filtringBox2.getChildren().addAll(check5, averageField, check6, check7, check8, check9, check10, rarityCombo);


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
        modifyItem.setOnAction(controller.generateEventHandlerAction("modify-item",supplier));
        Button deleteItem = new Button("Delete");
        deleteItem.setOnAction(controller.generateEventHandlerAction("delete-item",supplier));
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
