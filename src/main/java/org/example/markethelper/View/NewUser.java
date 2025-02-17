package org.example.markethelper.View;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

import java.util.function.Supplier;

public class NewUser implements IView{
    private IView view;
    private static Scene scene;
    private static Stage stage;
    private Pane actualParent;
    private Controller controller;

    public NewUser(IView view) {
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
        //create container
        actualParent = new VBox();

        //title
        HBox titleBox = new HBox(40);
        Label title = new Label("Creation of user!");
        titleBox.getChildren().add(title);

        //first element id
        HBox idBox = new HBox(20);
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        idBox.getChildren().addAll(idLabel, idField);

        //second element password
        HBox passBox = new HBox(20);
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        TextField passShow = new TextField();
        passShow.setManaged(false);
        passShow.setVisible(false);
        //password toggle so it can be visible to check what is typed
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
        passBox.getChildren().addAll(passLabel, passField, passShow, togglePass);

        //the enter button
        Supplier<String[]> supplier = () -> new String[] {idField.getText(), passField.getText()};
        HBox buttonEnter = new HBox(40);
        Button enter = new Button("Enter");
        enter.setOnAction(controller.generateEventHandlerAction("new-user",supplier));
        buttonEnter.getChildren().add(enter);

        actualParent.getChildren().addAll(titleBox, idBox, passBox, buttonEnter);

        scene = new Scene(actualParent,640,480);
        stage.setScene(scene);
    }
}
