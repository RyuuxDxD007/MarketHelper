package org.example.markethelper.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import org.example.markethelper.Model.*;
import org.example.markethelper.Model.BL.Item;
import org.example.markethelper.Model.BL.Mod;
import org.example.markethelper.Model.BL.Riven;
import org.example.markethelper.View.*;

import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Controller {

    private IModel model;
    private IView view;

    public void initialize() {
        this.model = new PrimaryModel();
        this.view = FactoryIView.createView("start", view);
        if (PropertyChangeListener.class.isAssignableFrom(view.getClass())) {
            PropertyChangeListener pcl = (PropertyChangeListener) view;
            model.addPropertyChangeListener(pcl);
        }
        view.setController(this);
        view.setController(this);
    }

    public void start() {
        this.view.launchApp();
    }

    public void stop() {
        this.model.close();
        this.view.stopApp();
    }

    public EventHandler<ActionEvent> generateEventHandlerAction(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                function.accept(params.get());
            }
        };
    }

    public EventHandler<MouseEvent> generateEventHandlerMouse(String action, Supplier<String[]> params) {
        Consumer<String[]> function = this.generateConsumer(action);
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    function.accept(params.get());
                }
            }
        };
    }

    public EventHandler<WindowEvent> generateCloseEvent() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                stop();
                System.exit(0);
            }
        };
    }

    private Consumer<String[]> generateConsumer(String action) {
        Consumer<String[]> t;
        switch (action) {

            //Item logics
            case "show-allItems":
                t = x -> showAllItems();
                break;
            case "modify-item":
                t = x -> {
                    changeView("view-newItem");
                    //showItem(selected);
                };
                break;
                //need fix
            case "delete-item":
                t = x -> showAllItems();
                break;

            //User related and view changes
            case "create-user":
                t = (x) -> {
                    if (this.createUser(x[0], x[1])) {
                        changeView("view-mainPage");
                    } else {
                        ((NewUser) this.view).showErrorMessage("Error: Id already taken.");
                    }
                };
                break;
            case "verify":
                t = (x) -> {
                    if (this.verifyUser(x[0], x[1])) {
                        changeView("view-mainPage");
                    } else {
                        ((Identification) this.view).showErrorMessage("Error: Id and/or Password is wrong!");
                    }
                };
                break;

            //view changes
            case "view-newItem":
                t = (x) -> changeView(action);
                break;
            case "view-relics":
                t = (x) -> changeView(action);
                break;
            case "view-primeSets":
                t = (x) -> changeView(action);
                break;
            case "view-newUser":
                t = (x) -> changeView(action);
                break;
            case "view-identification":
                t = (x) -> changeView(action);
                break;
            default:
                throw new InvalidParameterException(action + " n'existe pas.");
        }
        return t;
    }

    private boolean createUser(String id, String pass) {
        return this.model.createUser(id, pass);
    }

    public void changeView(String view) {
        IView newView = FactoryIView.createView(view, this.view);
        newView.setController(this.view.getController());
        //this.view = newView;
        //this.view.showPrincipalWindow();
        if (newView instanceof PropertyChangeListener) {
            if (this.view instanceof PropertyChangeListener) {
                this.model.removePropertyChangeListener((PropertyChangeListener) this.view);
            }
            this.model.addPropertyChangeListener((PropertyChangeListener) newView);
        }
        this.view = newView;
        this.view.showPrincipalWindow();
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    public void showAllItems() {
        this.model.getFiltered();
    }

    public boolean verifyUser(String id, String pass) {
        return this.model.verifyUser(id, pass);
    }

    public String getItemRarity(Item item) {
        return this.model.getItemRarity(item);
    }
    public String getItemReroll(Item item) {
        return this.model.getItemReroll(item);
    }
    public String getItemPolarity(Item item) {
        return this.model.getItemPolarity(item);
    }

    public String getItemType(Item item) {
        return this.model.getItemType(item);
    }

    //need to add model actions
}
