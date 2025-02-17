package org.example.markethelper.View;

import javafx.application.Platform;
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
    private Pane actualParent;
    private Controller controller;

    public Identification(IView view) {
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

        HBox idBox = new HBox(20);
        Label idLabel = new Label("Enter your ID:");
        TextField idField = new TextField();
        Button submitIdButton = new Button("Confirm");

        idBox.getChildren().addAll(idLabel, idField, submitIdButton);

        HBox passBox = new HBox(20);
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
            controller.generateEventHandlerAction("verify",supplier);
            passBox.setVisible(false);
            idBox.setVisible(true);
        });
        actualParent.getChildren().addAll(idBox, passBox);


        scene = new Scene(actualParent,640,480);
        stage.setScene(scene);
    }


}
