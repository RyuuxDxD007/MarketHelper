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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.PrimeSet;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.function.Supplier;

public class VPrimeSets implements IView, PropertyChangeListener {

    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;
    TableView<PrimeSet> setTable;

    public VPrimeSets(IView view) {
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
    public Stage getStage() {
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
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChangeEvent received in VPrimeSets: " + evt.getPropertyName());

        switch (evt.getPropertyName()) {
            case "show-allSets":
                ArrayList<PrimeSet> updatedSets = (ArrayList<PrimeSet>) evt.getNewValue();
                showAllSets(updatedSets);
                break;
            default:
                break;
        }
    }
    @Override
    public void showPrincipalWindow() {
        actualParent = new VBox();
        actualParent.setPadding(new Insets(10,10,10,10));
        Supplier<String[]> supplier = () -> new String[]{""};

        HBox filtringBox1 = new HBox(20);
        filtringBox1.setAlignment(Pos.CENTER);
        //CheckBox check1 = new CheckBox("Vaulted");
        //CheckBox check2 = new CheckBox("Ressurgence");
        CheckBox check3 = new CheckBox("Min");
        check3.setOnAction(controller.generateEventHandlerAction("select-min-set",supplier));
        CheckBox check4 = new CheckBox("Max");
        check4.setOnAction(controller.generateEventHandlerAction("select-max-set",supplier));
        TextField minField = new TextField();
        minField.textProperty().addListener((observable, oldValue, newValue) -> controller.minFieldChange(newValue,"set"));
        TextField maxField = new TextField();
        maxField.textProperty().addListener((observable, oldValue, newValue) -> controller.maxFieldChange(newValue,"set"));
        CheckBox check5 = new CheckBox("Average Min");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMin-set",supplier));
        TextField averageMinField = new TextField();
        averageMinField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMinFieldChange(newValue,"set"));
        CheckBox check1 = new CheckBox("Average Max");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMax-set",supplier));
        TextField averageMaxField = new TextField();
        averageMaxField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMaxFieldChange(newValue,"set"));
        filtringBox1.getChildren().addAll(check3, minField, check4, maxField, check5, averageMinField, check1, averageMaxField);



        setTable = new TableView<>();

        TableColumn<PrimeSet, String> setNameCol = new TableColumn<>("Set Name");
        setNameCol.setCellValueFactory(new PropertyValueFactory<>("setName"));

        TableColumn<PrimeSet, Integer> setPriceCol = new TableColumn<>("Set Price");
        setPriceCol.setCellValueFactory(new PropertyValueFactory<>("setPrice"));

        TableColumn<PrimeSet, String> separatedPriceCol = new TableColumn<>("Separated Price");
        separatedPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getSetSeperatedPrice(cellData.getValue())));

        TableColumn<PrimeSet, String> part1Col = new TableColumn<>("Part 1");
        part1Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 0)));

        TableColumn<PrimeSet, String> part2Col = new TableColumn<>("Part 2");
        part2Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 1)));

        TableColumn<PrimeSet, String> part3Col = new TableColumn<>("Part 3");
        part3Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 2)));
        TableColumn<PrimeSet, String> part4Col = new TableColumn<>("Part 4");
        part4Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 3)));

        TableColumn<PrimeSet, String> part5Col = new TableColumn<>("Part 5");
        part5Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 4)));

        TableColumn<PrimeSet, String> part6Col = new TableColumn<>("Part 6");
        part6Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartName(cellData.getValue(), 5)));
        setTable.getColumns().addAll(setNameCol, setPriceCol, separatedPriceCol, part1Col, part2Col, part3Col, part4Col, part5Col, part6Col);

        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        Button newSet = new Button("New Set");
        newSet.setOnAction(controller.generateEventHandlerAction("view-newSet",supplier));
        Button modifySet = new Button("Modify");
        modifySet.setOnAction(controller.generateEventHandlerAction("modify-set",supplier));
        Button deleteSet = new Button("Delete");
        deleteSet.setOnAction(controller.generateEventHandlerAction("delete-set",supplier));
        Button relics = new Button("Relics");
        relics.setOnAction(controller.generateEventHandlerAction("view-relics",supplier));
        Button Items = new Button("Items");
        Items.setOnAction(controller.generateEventHandlerAction("view-mainPage",supplier));
        buttonBox.getChildren().addAll(newSet, modifySet, deleteSet, relics, Items);

        actualParent.getChildren().addAll(filtringBox1, setTable, buttonBox);
        controller.showAllSets();

        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);

    }
    public void showAllSets(ArrayList<PrimeSet> listSets) {
        if (setTable != null) {
            setTable.getItems().setAll(listSets);
        }
    }

}
