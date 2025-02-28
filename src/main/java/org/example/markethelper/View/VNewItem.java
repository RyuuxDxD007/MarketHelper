package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

import java.util.function.Supplier;

public class VNewItem implements IView {


    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;

    public VNewItem(IView view) {
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
    @Override
    public void showPrincipalWindow() {
        actualParent = new VBox(50);
        HBox choiceBox = new HBox(50);
        ComboBox<String> choiceCombo = new ComboBox<>();
        choiceCombo.setItems(FXCollections.observableArrayList("Item", "Mod", "PrimePart", "Riven"));
        choiceBox.getChildren().add(choiceCombo);
        actualParent.getChildren().add(choiceBox);

        if(choiceCombo.getValue().equals("Item")) {
            showNewItemWindow();
        }
        else if(choiceCombo.getValue().equals("Mod")) {
            showNewModWindow();
        }
        else if(choiceCombo.getValue().equals("PrimePart")) {
            showNewPrimePartWindow();
        }
        else if(choiceCombo.getValue().equals("Riven")) {
            showNewRivenWindow();
        }

        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }
    public void showNewItemWindow(){
        VBox newItemBox = new VBox(50);

        HBox titleBox = new HBox(50);
        titleBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("New Item");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        priceBox.getChildren().addAll(priceLabel, priceField);

        newItemBox.getChildren().addAll(titleBox, nameBox, priceBox);

        actualParent.getChildren().add(newItemBox);

    }
    public void showNewModWindow(){
        VBox newModBox = new VBox(50);

        HBox titleBox = new HBox(50);
        titleBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("New Mod");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox polarityBox = new HBox(20);
        Label polarityLabel = new Label("Polarity : ");
        ComboBox<String> polarityCombo = new ComboBox<>();
        polarityCombo.setItems(FXCollections.observableArrayList("Any", "Umbra", "Unairu", "Zenurik", "Naramon", "Vazarin", "Madurai", "Penjaga"));
        polarityBox.getChildren().addAll(polarityLabel, polarityCombo);

        newModBox.getChildren().addAll(titleBox, nameBox, priceBox, polarityBox);
        actualParent.getChildren().add(newModBox);
    }
    public void showNewPrimePartWindow(){
        VBox newPrimePartBox = new VBox(50);

        HBox titleBox = new HBox(50);
        titleBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("New PrimePart");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox rarityBox = new HBox(20);
        Label rarityLabel = new Label("Rarity : ");
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("15", "25", "45", "65", "100"));
        rarityBox.getChildren().addAll(rarityLabel, rarityCombo);

        if(rarityCombo.getValue().equals("25")){
            HBox colorBox1 = new HBox(20);
            Label colorLabel1 = new Label("Color : ");
            ComboBox<String> colorCombo1 = new ComboBox<>();
            colorCombo1.setItems(FXCollections.observableArrayList("Bronze", "Silver"));
            colorBox1.getChildren().addAll(colorLabel1, colorCombo1);

            newPrimePartBox.getChildren().addAll(titleBox, nameBox, priceBox, rarityBox, colorBox1);
        }
        else if(rarityCombo.getValue().equals("65")){
            HBox colorBox2 = new HBox(20);
            Label colorLabel2 = new Label("Color : ");
            ComboBox<String> colorCombo2 = new ComboBox<>();
            colorCombo2.setItems(FXCollections.observableArrayList("Silver", "Gold"));
            colorBox2.getChildren().addAll(colorLabel2, colorCombo2);

            newPrimePartBox.getChildren().addAll(titleBox, nameBox, priceBox, rarityBox, colorBox2);
        }else {
            newPrimePartBox.getChildren().addAll(titleBox, nameBox, priceBox, rarityBox);
        }
        actualParent.getChildren().add(newPrimePartBox);
    }
    public void showNewRivenWindow(){
        VBox newRivenBox = new VBox(50);

        HBox titleBox = new HBox(50);
        titleBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Riven");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox polarityBox = new HBox(20);
        Label polarityLabel = new Label("Polarity : ");
        ComboBox<String> polarityCombo = new ComboBox<>();
        polarityCombo.setItems(FXCollections.observableArrayList("Any", "Umbra", "Unairu", "Zenurik", "Naramon", "Vazarin", "Madurai", "Penjaga"));
        polarityBox.getChildren().addAll(polarityLabel, polarityCombo);

        HBox rerollBox = new HBox(20);
        Label rerollLabel = new Label("Reroll : ");
        TextField rerollField = new TextField();
        rerollBox.getChildren().addAll(rerollLabel, rerollField);

        newRivenBox.getChildren().addAll(titleBox, nameBox, priceBox, polarityBox, rerollBox);
        actualParent.getChildren().add(newRivenBox);
    }
}
