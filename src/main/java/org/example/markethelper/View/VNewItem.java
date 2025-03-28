package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.Item;

import java.util.function.Supplier;

public class VNewItem implements IView {


    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;
    private String[] prefilledData;
    final private int TYPE = 0;
    final private int ID = 1;
    final private int NAME = 2;
    final private int PRICE = 3;
    final private int POLARITY = 4;
    final private int REROLL = 5;
    final private int RARITY = 4;
    final private int COLOR = 5;


    public VNewItem(IView view) {
        this.view = view;
        this.controller = view.getController();
        this.stage = view.getStage();
        this.prefilledData = null;
    }

    public VNewItem(IView view, String[] prefilledData) {
        this.view = view;
        this.controller = view.getController();
        this.stage = view.getStage();
        this.prefilledData = prefilledData;
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
        actualParent.setPadding(new Insets(50, 50, 50, 50));

        HBox choiceBox = new HBox(20);
        Label choiceLabel = new Label("Type of item you would like to add : ");
        ComboBox<String> choiceCombo = new ComboBox<>();
        choiceCombo.setItems(FXCollections.observableArrayList("Item", "Mod", "PrimePart", "Riven"));
        choiceBox.getChildren().addAll(choiceLabel, choiceCombo);
        if (prefilledData != null) {
            choiceCombo.setDisable(true);
            choiceCombo.getSelectionModel().select(prefilledData[TYPE]);
        }
        actualParent.getChildren().add(choiceBox);

        VBox controlBox = new VBox(20);
        controlBox.setAlignment(Pos.BOTTOM_CENTER);
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        Button confirmButton = new Button("Confirm");
        Button cancelButton = new Button("Cancel");
        Supplier<String[]> supplier = () -> new String[]{""};
        cancelButton.setOnAction(e -> {
            EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("view-mainPage", supplier);
            handler.handle(new ActionEvent());
        });
        buttonBox.getChildren().addAll(confirmButton, cancelButton);

        choiceCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            actualParent.getChildren().removeIf(node -> node instanceof VBox);
            switch (newValue) {
                case "Item":
                    Supplier<String[]> supplierItem = showNewItemWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("create-item", supplierItem);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "Mod":
                    Supplier<String[]> supplierMod = showNewModWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("create-mod", supplierMod);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "PrimePart":
                    Supplier<String[]> supplierPrime = showNewPrimePartWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("create-primePart", supplierPrime);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "Riven":
                    Supplier<String[]> supplierRiven = showNewRivenWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("create-riven", supplierRiven);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
            }
        });
        if(prefilledData != null) {
            switch (prefilledData[TYPE]) {
                case "Item":
                    Supplier<String[]> supplierItem = showNewItemWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("update-item", supplierItem);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "Mod":
                    Supplier<String[]> supplierMod = showNewModWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("update-mod", supplierMod);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "PrimePart":
                    Supplier<String[]> supplierPrime = showNewPrimePartWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("update-primePart", supplierPrime);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
                case "Riven":
                    Supplier<String[]> supplierRiven = showNewRivenWindow();
                    confirmButton.setOnAction(e -> {
                        EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("update-riven", supplierRiven);
                        handler.handle(new ActionEvent());
                    });
                    actualParent.getChildren().remove(buttonBox);
                    actualParent.getChildren().add(buttonBox);
                    break;
            }
        }
        else {
            actualParent.getChildren().add(buttonBox);
        }
        scene = new Scene(actualParent, 1300, 600);
        stage.setScene(scene);
    }

    public Supplier<String[]> showNewItemWindow() {
        VBox newItemBox = new VBox(50);

        HBox titleBox = new HBox(50);
        Label titleLabel = new Label("New Item info");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        if (prefilledData != null) {
            nameField.setText(prefilledData[NAME]);
        }
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        if (prefilledData != null) {
            priceField.setText(prefilledData[PRICE]);
        }
        priceBox.getChildren().addAll(priceLabel, priceField);

        newItemBox.getChildren().addAll(titleBox, nameBox, priceBox);

        actualParent.getChildren().add(newItemBox);

        if(prefilledData != null){
            return () -> new String[]{prefilledData[ID], nameField.getText(), priceField.getText()};
        }
        else {
            return () -> new String[]{nameField.getText(), priceField.getText()};
        }
    }

    public Supplier<String[]> showNewModWindow() {
        VBox newModBox = new VBox(50);

        HBox titleBox = new HBox(50);
        Label titleLabel = new Label("New Mod info");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        if (prefilledData != null) {
            nameField.setText(prefilledData[NAME]);
        }
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        if (prefilledData != null) {
            priceField.setText(prefilledData[PRICE]);
        }
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox polarityBox = new HBox(20);
        Label polarityLabel = new Label("Polarity : ");
        ComboBox<String> polarityCombo = new ComboBox<>();
        polarityCombo.setItems(FXCollections.observableArrayList("Any", "Umbra", "Unairu", "Zenurik", "Naramon", "Vazarin", "Madurai", "Penjaga"));
        if (prefilledData != null) {
            polarityCombo.setValue(prefilledData[POLARITY]);
        }
        polarityBox.getChildren().addAll(polarityLabel, polarityCombo);

        newModBox.getChildren().addAll(titleBox, nameBox, priceBox, polarityBox);
        actualParent.getChildren().add(newModBox);
        if(prefilledData != null){
            return () -> new String[]{prefilledData[ID], nameField.getText(), priceField.getText(), polarityCombo.getValue()};
        }
        else {
            return () -> new String[]{nameField.getText(), priceField.getText(), polarityCombo.getValue()};
        }
    }

    public Supplier<String[]> showNewPrimePartWindow() {
        VBox newPrimePartBox = new VBox(50);

        HBox titleBox = new HBox(50);
        Label titleLabel = new Label("New PrimePart info");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        if (prefilledData != null) {
            nameField.setText(prefilledData[NAME]);
        }
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        if (prefilledData != null) {
            priceField.setText(prefilledData[PRICE]);
        }
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox rarityBox = new HBox(20);
        Label rarityLabel = new Label("Rarity : ");
        ComboBox<String> rarityCombo = new ComboBox<>();
        rarityCombo.setItems(FXCollections.observableArrayList("15", "25", "45", "65", "100"));
        if (prefilledData != null) {
            rarityCombo.setValue(prefilledData[RARITY]);
        }
        rarityBox.getChildren().addAll(rarityLabel, rarityCombo);

        VBox colorVBox = new VBox();
        ComboBox<String> colorCombo = new ComboBox<>();

        rarityCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorVBox.getChildren().clear();
            if ("25".equals(newValue)) {
                HBox colorBox = new HBox(20);
                Label colorLabel = new Label("Color : ");
                colorCombo.setItems(FXCollections.observableArrayList("Bronze", "Silver"));
                if (prefilledData != null) {
                    colorCombo.setValue(prefilledData[COLOR]);
                }
                colorBox.getChildren().addAll(colorLabel, colorCombo);
                colorVBox.getChildren().add(colorBox);
            } else if ("65".equals(newValue)) {
                HBox colorBox = new HBox(20);
                Label colorLabel = new Label("Color : ");
                colorCombo.setItems(FXCollections.observableArrayList("Silver", "Gold"));
                if (prefilledData != null) {
                    colorCombo.setValue(prefilledData[COLOR]);
                }
                colorBox.getChildren().addAll(colorLabel, colorCombo);
                colorVBox.getChildren().add(colorBox);
            }
        });
        newPrimePartBox.getChildren().addAll(titleBox, nameBox, priceBox, rarityBox, colorVBox);
        actualParent.getChildren().add(newPrimePartBox);
        if(prefilledData != null){
            return () -> new String[]{prefilledData[ID], nameField.getText(), priceField.getText(), rarityCombo.getValue(), colorCombo.getValue()};
        }
        else {
            return () -> new String[]{nameField.getText(), priceField.getText(), rarityCombo.getValue(), colorCombo.getValue()};
        }
    }

    public Supplier<String[]> showNewRivenWindow() {
        VBox newRivenBox = new VBox(50);

        HBox titleBox = new HBox(50);
        Label titleLabel = new Label("Riven info");
        titleBox.getChildren().add(titleLabel);

        HBox nameBox = new HBox(20);
        Label nameLabel = new Label("Name : ");
        TextField nameField = new TextField();
        if (prefilledData != null) {
            nameField.setText(prefilledData[NAME]);
        }
        nameBox.getChildren().addAll(nameLabel, nameField);

        HBox priceBox = new HBox(20);
        Label priceLabel = new Label("Price : ");
        TextField priceField = new TextField();
        if (prefilledData != null) {
            priceField.setText(prefilledData[PRICE]);
        }
        priceBox.getChildren().addAll(priceLabel, priceField);

        HBox polarityBox = new HBox(20);
        Label polarityLabel = new Label("Polarity : ");
        ComboBox<String> polarityCombo = new ComboBox<>();
        polarityCombo.setItems(FXCollections.observableArrayList("Any", "Umbra", "Unairu", "Zenurik", "Naramon", "Vazarin", "Madurai", "Penjaga"));
        if (prefilledData != null) {
            polarityCombo.setValue(prefilledData[POLARITY]);
        }
        polarityBox.getChildren().addAll(polarityLabel, polarityCombo);

        HBox rerollBox = new HBox(20);
        Label rerollLabel = new Label("Reroll : ");
        TextField rerollField = new TextField();
        if (prefilledData != null) {
            rerollField.setText(prefilledData[REROLL]);
        }
        rerollBox.getChildren().addAll(rerollLabel, rerollField);

        newRivenBox.getChildren().addAll(titleBox, nameBox, priceBox, polarityBox, rerollBox);
        actualParent.getChildren().add(newRivenBox);

        if(prefilledData != null){
            return () -> new String[]{ prefilledData[ID], nameField.getText(), priceField.getText(), polarityCombo.getValue(), rerollField.getText()};
        }
        else {
            return () -> new String[]{nameField.getText(), priceField.getText(), polarityCombo.getValue(), rerollField.getText()};
        }
    }
}
