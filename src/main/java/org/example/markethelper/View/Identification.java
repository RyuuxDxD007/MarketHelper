package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

import java.util.function.Supplier;

public class Identification implements IView{
    private IView view;
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;

    public Identification(IView view) {
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
        actualParent.setAlignment(Pos.CENTER);

        HBox idBox = new HBox(20);
        idBox.setAlignment(Pos.CENTER);
        Label idLabel = new Label("      ID:");
        TextField idField = new TextField();
        Button submitIdButton = new Button("Confirm");


        idBox.getChildren().addAll(idLabel, idField, submitIdButton);

        HBox passBox = new HBox(20);
        passBox.setAlignment(Pos.CENTER);
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        TextField passShow = new TextField();
        passShow.setManaged(false);
        passShow.setVisible(false);
        Button submitPassButton = new Button("Confirm");
        Button togglePass = new Button("👁");
        togglePass.setOnAction(e -> {
            if (passField.isVisible()) {
                passShow.setText(passField.getText());
                passField.setVisible(false);
                passField.setManaged(false);
                passShow.setVisible(true);
                passShow.setManaged(true);
            } else {
                passField.setText(passShow.getText());
                passField.setVisible(true);
                passField.setManaged(true);
                passShow.setVisible(false);
                passShow.setManaged(false);
            }
        });

        passBox.getChildren().addAll(passLabel, passField, passShow, togglePass, submitPassButton);
        passBox.setVisible(false);

        submitIdButton.setOnAction(e -> {
            idBox.setVisible(false);
            passBox.setVisible(true);
        });
        submitPassButton.setOnAction(e -> {
            Supplier<String[]> supplier = () -> new String[]{idField.getText(), passField.getText()};
            EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("verify", () -> new String[]{idField.getText(), passField.getText()});
            handler.handle(e);
            passBox.setVisible(false);
            idBox.setVisible(true);
        });
        HBox cancelBox = new HBox(20);
        cancelBox.setAlignment(Pos.CENTER);
        Button cancelButton = new Button("Cancel");
        Supplier<String[]> cancelSupplier = () -> new String[]{""};
        cancelButton.setOnAction(e -> {
            EventHandler<ActionEvent> handler = controller.generateEventHandlerAction("start", cancelSupplier);
            handler.handle(new ActionEvent());
        });
        cancelBox.getChildren().add(cancelButton);
        actualParent.getChildren().addAll(idBox, passBox, cancelBox);


        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }
    public void showErrorMessage(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }


}
