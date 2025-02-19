package org.example.markethelper.View;

import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

import java.beans.PropertyChangeEvent;

public interface IView {
    void setController(Controller controller);
    Controller getController();
    Stage getStage();
    void launchApp();
    void stopApp();
    void showPrincipalWindow();
}
