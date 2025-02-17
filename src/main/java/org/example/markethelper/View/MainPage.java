package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.Item;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MainPage implements IView{
    private IView view;
    private static Scene scene;
    private static Stage stage;
    private Pane actualParent;
    private Controller controller;

    public MainPage(IView view) {
        this.view = view;
        this.controller = view.getController();
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

        HBox filtringBox = new HBox(10);
        CheckBox check1 = new CheckBox("Vaulted");
        CheckBox check2 = new CheckBox("Ressurgence");
        CheckBox check3 = new CheckBox("Min");
        CheckBox check4 = new CheckBox("Max");
        CheckBox check5 = new CheckBox("Average");
        CheckBox check6 = new CheckBox("Items");
        CheckBox check7 = new CheckBox("Prime");
        CheckBox check8 = new CheckBox("Mods");
        CheckBox check9 = new CheckBox("Riven");
        CheckBox check10 = new CheckBox("Rarity");
        TextField minField = new TextField();
        TextField maxField = new TextField();
        TextField averageField = new TextField();
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("Option 1", "Option 2", "Option 3"));
        filtringBox.getChildren().addAll(check1, check2, check3, minField, check4, maxField, check5, averageField, check6, check7, check8, check9, check10, rarityCombo);

        actualParent.getChildren().add(filtringBox);
        ArrayList<String> itemList = controller.showAllItems();
        showAllItems(itemList);

        scene = new Scene(actualParent,640,480);
        stage.setScene(scene);
    }

    public void showAllItems(ArrayList<String> listItems) {
        ObservableList<String> items = FXCollections.observableArrayList(listItems);
        ListView<String> listView = new ListView<String>(items);
        Supplier<String[]> supplier = () -> new String[] {listView.getSelectionModel().getSelectedItem()};
        listView.setOnMouseClicked(controller.generateEventHandlerMouse("show-items", supplier));
        actualParent.getChildren().add(listView);
    }
}
