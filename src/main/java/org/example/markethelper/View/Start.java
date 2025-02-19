package org.example.markethelper.View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

import java.io.IOException;
import java.util.function.Supplier;

public class Start extends Application implements  IView {
    private static Scene scene;
    private static Stage stage;
    private VBox actualParent;
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }
    public Controller getController() {
        return controller;
    }

    @Override
    public void start(Stage stage) throws IOException{
        Start.stage = stage;
        if (this.controller == null) {
            this.controller = new Controller();
            this.controller.setView(this);
        }
        Start.stage.setOnCloseRequest(this.controller.generateCloseEvent());
        showPrincipalWindow();
        stage.show();
    }

    public void showPrincipalWindow(){
        actualParent = new VBox();
        actualParent.setAlignment(Pos.CENTER);
        Supplier<String[]> supplier = () -> new String[]{""};

        HBox buttonUser = new HBox();
        buttonUser.setAlignment(Pos.CENTER);
        Button newUser = new Button("New User");
        Button existingUser = new Button("Existing User");
        newUser.setOnAction(controller.generateEventHandlerAction("view-newUser",supplier));
        existingUser.setOnAction(controller.generateEventHandlerAction("view-identification",supplier));
        buttonUser.getChildren().addAll(newUser, existingUser);
        actualParent.getChildren().add(buttonUser);

        scene = new Scene(actualParent,1300,600);
        stage.setScene(scene);
    }

    @Override
    public void launchApp(){
        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                this.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    public void stopApp() {
        Platform.exit();
    }

    @Override
    public void init(){
        Parameters pam = getParameters();
        String view;
        if (pam.getUnnamed().isEmpty()) {
            view = "main";
        } else {
            view = pam.getUnnamed().get(0);
        }
        try {
            IView appView = FactoryIView.createView(view, this);
            if (appView instanceof Start) {
                setController(new Controller());
                launchApp();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public Stage getStage(){
        return stage;
    }

}
