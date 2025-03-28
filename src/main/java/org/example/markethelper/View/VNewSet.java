package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;
import org.example.markethelper.Model.BL.PrimePart;

import java.util.function.Supplier;

public class VNewSet implements IView{


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
    final private int PART1 = 4;
    final private int PART2 = 5;
    final private int PART3 = 6;
    final private int PART4 = 7;
    final private int PART5 = 8;
    final private int PART6 = 9;

    public VNewSet(IView view) {
        this.view = view;
        this.controller = view.getController();
        this.stage = view.getStage();
        this.prefilledData = null;
    }
    public VNewSet(IView view, String[] prefilledData) {
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
        actualParent.setPadding(new Insets(50,50,50,50));

        HBox titleBox = new HBox(50);
        Label titleLabel = new Label("New Set info");
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
        actualParent.getChildren().addAll(titleBox, nameBox, priceBox);

        HBox primePartsBox = new HBox(20);
        Label primePartsLabel1 = new Label("Prime Part 1 : ");
        ComboBox<PrimePart> primePartsBox1 = new ComboBox<>();
        primePartsBox1.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox1.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });

        primePartsBox1.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART1] != null) {
            primePartsBox1.getSelectionModel().select(controller.getPrimePart(prefilledData[PART1]));
        }
        Label primePartsLabel2 = new Label("Prime Part 2 : ");
        ComboBox<PrimePart> primePartsBox2 = new ComboBox<>();
        primePartsBox2.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox2.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        primePartsBox2.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART2] != null) {
            primePartsBox2.getSelectionModel().select(controller.getPrimePart(prefilledData[PART2]));
        }
        Label primePartsLabel3 = new Label("Prime Part 3 : ");
        ComboBox<PrimePart> primePartsBox3 = new ComboBox<>();
        primePartsBox3.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox3.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        primePartsBox3.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART3] != null) {
            primePartsBox3.getSelectionModel().select(controller.getPrimePart(prefilledData[PART3]));
        }
        Label primePartsLabel4 = new Label("Prime Part 4 : ");
        ComboBox<PrimePart> primePartsBox4 = new ComboBox<>();
        primePartsBox4.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox4.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        primePartsBox4.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART4] != null) {
            primePartsBox4.getSelectionModel().select(controller.getPrimePart(prefilledData[PART4]));
        }
        Label primePartsLabel5 = new Label("Prime Part 5 : ");
        ComboBox<PrimePart> primePartsBox5 = new ComboBox<>();
        primePartsBox5.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox5.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        primePartsBox5.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART5] != null) {
            primePartsBox5.getSelectionModel().select(controller.getPrimePart(prefilledData[PART5]));
        }
        Label primePartsLabel6 = new Label("Prime Part 6 : ");
        ComboBox<PrimePart> primePartsBox6 = new ComboBox<>();
        primePartsBox6.setItems(FXCollections.observableArrayList(controller.getAllPrimeParts()));
        primePartsBox6.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        primePartsBox6.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(PrimePart item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName());
            }
        });
        if(prefilledData != null && prefilledData[PART6] != null) {
            primePartsBox6.getSelectionModel().select(controller.getPrimePart(prefilledData[PART6]));
        }
        primePartsBox.getChildren().addAll(primePartsLabel1, primePartsBox1, primePartsLabel2, primePartsBox2, primePartsLabel3
                , primePartsBox3, primePartsLabel4, primePartsBox4, primePartsLabel5, primePartsBox5, primePartsLabel6, primePartsBox6);

        actualParent.getChildren().add(primePartsBox);

        VBox controlBox = new VBox(20);
        controlBox.setAlignment(Pos.BOTTOM_CENTER);
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            PrimePart part1 = primePartsBox1.getValue();
            PrimePart part2 = primePartsBox2.getValue();
            PrimePart part3 = primePartsBox3.getValue();
            PrimePart part4 = primePartsBox4.getValue();
            PrimePart part5 = primePartsBox5.getValue();
            PrimePart part6 = primePartsBox6.getValue();
            if (part1 == null && part2 == null && part3 == null && part4 == null && part5 == null && part6 == null) {
                showErrorMessage("All ComboBoxes must have a selected Prime Part.");
            }
            else if ((part1 != null && part2 != null && part1.getId() == part2.getId()) ||
                    (part1 != null && part3 != null && part1.getId() == part3.getId()) ||
                    (part1 != null && part4 != null && part1.getId() == part4.getId()) ||
                    (part1 != null && part5 != null && part1.getId() == part5.getId()) ||
                    (part1 != null && part6 != null && part1.getId() == part6.getId()) ||
                    (part2 != null && part3 != null && part2.getId() == part3.getId()) ||
                    (part2 != null && part4 != null && part2.getId() == part4.getId()) ||
                    (part2 != null && part5 != null && part2.getId() == part5.getId()) ||
                    (part2 != null && part6 != null && part2.getId() == part6.getId()) ||
                    (part3 != null && part4 != null && part3.getId() == part4.getId()) ||
                    (part3 != null && part5 != null && part3.getId() == part5.getId()) ||
                    (part3 != null && part6 != null && part3.getId() == part6.getId()) ||
                    (part4 != null && part5 != null && part4.getId() == part5.getId()) ||
                    (part4 != null && part6 != null && part4.getId() == part6.getId()) ||
                    (part5 != null && part6 != null && part5.getId() == part6.getId())) {
                showErrorMessage("Duplicate Prime Parts are not allowed.");
            }
            else if (prefilledData != null){
                Supplier<String[]> supplierItem = () -> new String[]{
                        prefilledData[ID],
                        nameField.getText(),
                        priceField.getText(),
                        part1 != null ? String.valueOf(part1.getId()) : "-1",
                        part2 != null ? String.valueOf(part2.getId()) : "-1",
                        part3 != null ? String.valueOf(part3.getId()) : "-1",
                        part4 != null ? String.valueOf(part4.getId()) : "-1",
                        part5 != null ? String.valueOf(part5.getId()) : "-1",
                        part6 != null ? String.valueOf(part6.getId()) : "-1"
                };
                EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("update-set", supplierItem);
                handler.handle(new ActionEvent());
            }
            else {
                Supplier<String[]> supplierItem = () -> new String[]{
                        nameField.getText(),
                        priceField.getText(),
                        part1 != null ? String.valueOf(part1.getId()) : "-1",
                        part2 != null ? String.valueOf(part2.getId()) : "-1",
                        part3 != null ? String.valueOf(part3.getId()) : "-1",
                        part4 != null ? String.valueOf(part4.getId()) : "-1",
                        part5 != null ? String.valueOf(part5.getId()) : "-1",
                        part6 != null ? String.valueOf(part6.getId()) : "-1"
                };
                EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("create-set", supplierItem);
                handler.handle(new ActionEvent());
            }
        });

        Button cancelButton = new Button("Cancel");
        Supplier<String[]> supplier = () -> new String[]{""};
        cancelButton.setOnAction(e -> {
            EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("view-primeSets", supplier);
            handler.handle(new ActionEvent());
        });

        buttonBox.getChildren().addAll(confirmButton,cancelButton);

        actualParent.getChildren().add(buttonBox);
        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
