package org.example.markethelper.View;

import javafx.stage.Stage;
import org.example.markethelper.Controller.Controller;

public interface IView {
    public void setController(Controller controller);
    public Controller getController();
    public Stage getStage();
    public void launchApp();
    public void stopApp();
    public void showPrincipalWindow();
}
