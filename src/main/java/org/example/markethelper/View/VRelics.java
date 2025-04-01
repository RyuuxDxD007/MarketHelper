package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.Relic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.function.Supplier;

public class VRelics implements IView, PropertyChangeListener {

    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;
    TableView<Relic> relicTable;

    public VRelics(IView view) {
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
        System.out.println("PropertyChangeEvent received in VRelics: " + evt.getPropertyName());

        switch (evt.getPropertyName()) {
            case "show-allRelics":
                ArrayList<Relic> updatedSets = (ArrayList<Relic>) evt.getNewValue();
                showAllRelics(updatedSets);
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
        check3.setOnAction(controller.generateEventHandlerAction("select-min-relic",supplier));
        CheckBox check4 = new CheckBox("Max");
        check4.setOnAction(controller.generateEventHandlerAction("select-max-relic",supplier));
        TextField minField = new TextField();
        minField.textProperty().addListener((observable, oldValue, newValue) -> controller.minFieldChange(newValue,"relic"));
        TextField maxField = new TextField();
        maxField.textProperty().addListener((observable, oldValue, newValue) -> controller.maxFieldChange(newValue,"relic"));
        CheckBox check5 = new CheckBox("Average Min");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMin-relic",supplier));
        TextField averageMinField = new TextField();
        averageMinField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMinFieldChange(newValue,"relic"));
        CheckBox check1 = new CheckBox("Average Max");
        check5.setOnAction(controller.generateEventHandlerAction("select-averageMax-relic",supplier));
        TextField averageMaxField = new TextField();
        averageMaxField.textProperty().addListener((observable, oldValue, newValue) -> controller.averageMaxFieldChange(newValue,"relic"));
        filtringBox1.getChildren().addAll(check3, minField, check4, maxField, check5, averageMinField, check1, averageMaxField);



        relicTable = new TableView<>();

        TableColumn<Relic, String> setNameCol = new TableColumn<>("Relic Name");
        setNameCol.setCellValueFactory(new PropertyValueFactory<>("relicName"));

        TableColumn<Relic, Integer> setPriceCol = new TableColumn<>("Relic Price");
        setPriceCol.setCellValueFactory(new PropertyValueFactory<>("relicPrice"));

        TableColumn<Relic, String> separatedPriceCol = new TableColumn<>("Separated Price");
        separatedPriceCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getSetSeperatedPriceRelic(cellData.getValue())));

        TableColumn<Relic, String> part1Col = new TableColumn<>("Part 1");
        part1Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 0)));

        TableColumn<Relic, String> part2Col = new TableColumn<>("Part 2");
        part2Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 1)));

        TableColumn<Relic, String> part3Col = new TableColumn<>("Part 3");
        part3Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 2)));
        TableColumn<Relic, String> part4Col = new TableColumn<>("Part 4");
        part4Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 3)));

        TableColumn<Relic, String> part5Col = new TableColumn<>("Part 5");
        part5Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 4)));

        TableColumn<Relic, String> part6Col = new TableColumn<>("Part 6");
        part6Col.setCellValueFactory(cellData ->
                new SimpleStringProperty(controller.getPrimePartNameRelic(cellData.getValue(), 5)));
        relicTable.getColumns().addAll(setNameCol, setPriceCol, separatedPriceCol, part1Col, part2Col, part3Col, part4Col, part5Col, part6Col);

        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        Button newSet = new Button("New Relic");
        newSet.setOnAction(controller.generateEventHandlerAction("view-newRelic",supplier));
        Button modifySet = new Button("Modify");
        modifySet.setOnAction(event -> {
            Relic select = relicTable.getSelectionModel().getSelectedItem();
            if (select != null) {
                Supplier<String[]> supplierModify = select::toStringArray;
                controller.generateEventHandlerAction("modify-relic", supplierModify).handle(event);
            } else {
                System.out.println("No relic selected to modify.");
            }
        });
        Button deleteSet = new Button("Delete");
        deleteSet.setOnAction(event -> {
            Relic select = relicTable.getSelectionModel().getSelectedItem();
            if (select != null) {
                Supplier<String[]> supplierDelete = () -> new String[]{String.valueOf(select.getRelicId())};
                controller.generateEventHandlerAction("delete-relic",supplierDelete).handle(event);
            } else {
                System.out.println("No relic selected for deletion.");
            }
        });
        Button relics = new Button("PrimeSets");
        relics.setOnAction(controller.generateEventHandlerAction("view-primeSets",supplier));
        Button Items = new Button("Items");
        Items.setOnAction(controller.generateEventHandlerAction("view-mainPage",supplier));
        buttonBox.getChildren().addAll(newSet, modifySet, deleteSet, relics, Items);

        actualParent.getChildren().addAll(filtringBox1, relicTable, buttonBox);
        controller.showAllRelics();

        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);

    }
    public void showAllRelics(ArrayList<Relic> listSets) {
        if (relicTable != null) {
            relicTable.getItems().setAll(listSets);
        }
    }

}
