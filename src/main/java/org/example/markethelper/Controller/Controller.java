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
            case "create-item":
                t = x -> {
                    createItem(x[0],x[1]);
                    changeView("view-mainPage");
                };
                break;
            case "create-mod":
                t = x -> {
                    createMod(x[0],x[1],x[2]);
                    changeView("view-mainPage");
                };
                break;
            case "create-primePart":
                t = x -> {
                    createPrimePart(x[0],x[1],x[2],x[3]);
                    changeView("view-mainPage");
                };
                break;
            case "create-riven":
                t = x -> {
                    createRiven(x[0],x[1],x[2],x[3]);
                    changeView("view-mainPage");
                };
                break;


            //User related and view change from user
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

            //Select buttons changing bool
            case "select-min":
                t = (x) -> checkPressed(action);
                break;
            case "select-max":
                t = (x) -> checkPressed(action);
                break;
            case "select-averageMin":
                t = (x) -> checkPressed(action);
                break;
            case "select-averageMax":
                t = (x) -> checkPressed(action);
                break;
            case "select-item":
                t = (x) -> checkPressed(action);
                break;
            case "select-mod":
                t = (x) -> checkPressed(action);
                break;
            case "select-prime":
                t = (x) -> checkPressed(action);
                break;
            case "select-riven":
                t = (x) -> checkPressed(action);
                break;
            case "select-rarity":
                t = (x) -> checkPressed(action);
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
            case "view-mainPage":
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

    public void checkPressed(String check) {
        this.model.boolChange(check);
        this.model.getFiltered();
    }

    public void minFieldChange(String min) {
        this.model.updateMinI(min);
        this.model.getFiltered();
    }

    public void maxFieldChange(String max) {
        this.model.updateMaxI(max);
        this.model.getFiltered();
    }

    public void averageMinFieldChange(String averageMin) {
        this.model.updateAverageMinI(averageMin);
        this.model.getFiltered();
    }

    public void averageMaxFieldChange(String averageMax) {
        this.model.updateAverageMaxI(averageMax);
        this.model.getFiltered();
    }

    public void rarityFieldChange(String rarity) {
        this.model.updateRarityI(rarity);
        this.model.getFiltered();
    }

    public void createItem(String name, String price) {
        this.model.createItem(name, Integer.parseInt(price));
    }
    public void createMod(String name, String price, String polarity) {
        this.model.createMod(name, Integer.parseInt(price),polarity);
    }
    public void createPrimePart(String name, String price, String rarity, String color) {
        this.model.createPrimePart(name, Integer.parseInt(price),Integer.parseInt(rarity),color);
    }
    public void createRiven(String name, String price, String polarity, String reroll) {
        this.model.createRiven(name, Integer.parseInt(price), polarity, Integer.parseInt(reroll));
    }

    //need to add model actions
}
