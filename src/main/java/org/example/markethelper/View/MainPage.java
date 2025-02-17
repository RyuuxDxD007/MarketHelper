package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.Item;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.function.Supplier;

public class MainPage implements IView{
    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;

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
        switch (evt.getPropertyName()) {
            case "show-all":
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

        HBox filtringBox1 = new HBox(10);
        CheckBox check1 = new CheckBox("Vaulted");
        CheckBox check2 = new CheckBox("Ressurgence");
        CheckBox check3 = new CheckBox("Min");
        CheckBox check4 = new CheckBox("Max");
        TextField minField = new TextField();
        TextField maxField = new TextField();
        filtringBox1.getChildren().addAll(check1, check2, check3, minField, check4, maxField);

        HBox filtringBox2 = new HBox(10);
        CheckBox check5 = new CheckBox("Average");
        TextField averageField = new TextField();
        CheckBox check6 = new CheckBox("Items");
        CheckBox check7 = new CheckBox("Prime");
        CheckBox check8 = new CheckBox("Mods");
        CheckBox check9 = new CheckBox("Riven");
        CheckBox check10 = new CheckBox("Rarity");
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));
        filtringBox2.getChildren().addAll(check5, averageField, check6, check7, check8, check9, check10, rarityCombo);


        actualParent.getChildren().addAll(filtringBox1,filtringBox2);
        controller.showAllItems();


        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }

    public void showAllItems(ArrayList<Item> listItems) {
        TableView<Item> tableView = new TableView<>();
        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Item, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Item, String> rarityColumn = new TableColumn<>("Rarity");
        rarityColumn.setCellValueFactory(new PropertyValueFactory<>("rarity"));
        tableView.getColumns().addAll(nameColumn, typeColumn, priceColumn, rarityColumn);
        ObservableList<Item> items = FXCollections.observableArrayList(listItems);
        tableView.setItems(items);
        actualParent.getChildren().add(tableView);
    }
}
