package org.example.markethelper.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import org.example.markethelper.Model.*;
import org.example.markethelper.Model.BL.*;
import org.example.markethelper.View.*;

import java.beans.PropertyChangeListener;
import java.security.InvalidParameterException;
import java.util.ArrayList;
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
                    modify("view-modifyItem", x );
                };
                break;
            case "delete-item":
                t = x -> {
                    deleteItem(x[0]);
                    showAllItems();
                };
                break;
            case "create-item":
                t = x -> {
                    createItem(x[0], x[1]);
                    changeView("view-mainPage");
                };
                break;
            case "create-mod":
                t = x -> {
                    createMod(x[0], x[1], x[2]);
                    changeView("view-mainPage");
                };
                break;
            case "create-primePart":
                t = x -> {
                    createPrimePart(x[0], x[1], x[2], x[3]);
                    changeView("view-mainPage");
                };
                break;
            case "create-riven":
                t = x -> {
                    createRiven(x[0], x[1], x[2], x[3]);
                    changeView("view-mainPage");
                };
                break;
            case "update-item":
                t = x -> {
                    updateItem(x[0], x[1], x[2]);
                    changeView("view-mainPage");
                };
                break;
            case "update-mod":
                t = x -> {
                    updateMod(x[0], x[1], x[2], x[3]);
                    changeView("view-mainPage");
                };
                break;
            case "update-primePart":
                t = x -> {
                    updatePrimePart(x[0], x[1], x[2], x[3], x[4]);
                    changeView("view-mainPage");
                };
                break;
            case "update-riven":
                t = x -> {
                    updateRiven(x[0], x[1], x[2], x[3], x[4]);
                    changeView("view-mainPage");
                };
                break;

            //Set Logics
            case "show-allSets":
                t = x -> showAllSets();
                break;
            case "modify-set":
                t = x -> {
                    modify("view-modifySet", x );
                };
                break;
            case "delete-set":
                t = x -> {
                    deleteSet(x[0]);
                    showAllSets();
                };
                break;
            case "create-set":
                t = x -> {
                    createSet(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7]);
                    changeView("view-primeSets");
                };
                break;
            case "update-set":
                t = x -> {
                    updatePrimeSet(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8]);
                    changeView("view-primeSets");
                };
                break;

            //Relic Logics
            case "show-allRelics":
                t = x -> showAllRelics();
                break;
            case "modify-relic":
                t = x -> {
                    modify("view-modifyRelic", x );
                };
                break;
            case "delete-relic":
                t = x -> {
                    deleteRelic(x[0]);
                    showAllRelics();
                };
                break;
            case "create-relic":
                t = x -> {
                    createRelic(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7]);
                    changeView("view-relics");
                };
                break;
            case "update-relic":
                t = x -> {
                    updatePrimeRelic(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8]);
                    changeView("view-relics");
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

            //Select buttons changing bool for Items
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

            //Select buttons changing bool for Sets
            case "select-min-set":
                t = (x) -> checkPressedSet(action);
                break;
            case "select-max-set":
                t = (x) -> checkPressedSet(action);
                break;
            case "select-averageMin-set":
                t = (x) -> checkPressedSet(action);
                break;
            case "select-averageMax-set":
                t = (x) -> checkPressedSet(action);
                break;

            //Select buttons changing bool for Sets
            case "select-min-relic":
                t = (x) -> checkPressedRelic(action);
                break;
            case "select-max-relic":
                t = (x) -> checkPressedRelic(action);
                break;
            case "select-averageMin-relic":
                t = (x) -> checkPressedRelic(action);
                break;
            case "select-averageMax-relic":
                t = (x) -> checkPressedRelic(action);
                break;

            //view changes
            case "start":
                t = (x) -> changeView(action);
                break;
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
            case "view-newSet":
                t = (x) -> changeView(action);
                break;
            case "view-newRelic":
                t = (x) -> changeView(action);
                break;
            default:
                throw new InvalidParameterException(action + " n'existe pas.");
        }
        return t;
    }

    private void updatePrimeSet(String id, String name, String price, String part1, String part2, String part3, String part4, String part5, String part6) {
        this.model.updatePrimeSet(Integer.parseInt(id), name, Integer.parseInt(price), Integer.parseInt(part1), Integer.parseInt(part2),
                Integer.parseInt(part3), Integer.parseInt(part4), Integer.parseInt(part5), Integer.parseInt(part6));
    }

    private void updatePrimeRelic(String id, String name, String price, String part1, String part2, String part3, String part4, String part5, String part6) {
        this.model.updatePrimeRelic(Integer.parseInt(id), name, Integer.parseInt(price), Integer.parseInt(part1), Integer.parseInt(part2),
                Integer.parseInt(part3), Integer.parseInt(part4), Integer.parseInt(part5), Integer.parseInt(part6));
    }

    private void updateRiven(String id, String name, String price, String polarity, String rerolls) {
        this.model.updateRiven(Integer.parseInt(id), name, Integer.parseInt(price), polarity, Integer.parseInt(rerolls));
    }

    private void updatePrimePart(String id, String name, String price, String rarity, String color) {
        this.model.updatePrimePart(Integer.parseInt(id), name, Integer.parseInt(price), Integer.parseInt(rarity), color);
    }

    private void updateMod(String id, String name, String price, String polarity) {
        this.model.updateMod(Integer.parseInt(id), name, Integer.parseInt(price), polarity);
    }

    private void updateItem(String id, String name, String price) {
        this.model.updateItem(Integer.parseInt(id), name, Integer.parseInt(price));
    }

    private void deleteSet(String id) {
        this.model.deleteSet(Integer.parseInt(id));
    }

    private void deleteRelic(String id) {
        this.model.deleteRelic(Integer.parseInt(id));
    }

    private void deleteItem(String id) {
        this.model.deleteItem(Integer.parseInt(id));
    }

    private void createSet(String name, String price, String part1, String part2, String part3, String part4, String part5, String part6) {
        this.model.createSet(name, Integer.parseInt(price), Integer.parseInt(part1), Integer.parseInt(part2),
                Integer.parseInt(part3), Integer.parseInt(part4), Integer.parseInt(part5), Integer.parseInt(part6));
    }

    private void createRelic(String name, String price, String part1, String part2, String part3, String part4, String part5, String part6) {
        this.model.createRelic(name, Integer.parseInt(price), Integer.parseInt(part1), Integer.parseInt(part2),
                Integer.parseInt(part3), Integer.parseInt(part4), Integer.parseInt(part5), Integer.parseInt(part6));
    }

    private boolean createUser(String id, String pass) {
        return this.model.createUser(id, pass);
    }

    public void changeView(String view) {
        this.model.boolReset();
        IView newView = FactoryIView.createView(view, this.view);
        newView.setController(this.view.getController());
        if (newView instanceof PropertyChangeListener) {
            if (this.view instanceof PropertyChangeListener) {
                this.model.removePropertyChangeListener((PropertyChangeListener) this.view);
            }
            this.model.addPropertyChangeListener((PropertyChangeListener) newView);
        }
        this.view = newView;
        this.view.showPrincipalWindow();
    }
    public void modify(String view, String[] item) {
        IView newView = FactoryIView.createView(view, this.view, item);
        newView.setController(this.view.getController());
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

    public void checkPressedSet(String check) {
        this.model.boolChange(check);
        this.model.getFilteredSets();
    }

    public void checkPressedRelic(String check) {
        this.model.boolChange(check);
        this.model.getFilteredRelics();
    }

    public void minFieldChange(String min, String option) {
        this.model.updateMinI(min);
        if (option.equals("item")) {
            this.model.getFiltered();
        }
        if (option.equals("set")) {
            this.model.getFilteredSets();
        }
    }

    public void maxFieldChange(String max, String option) {
        this.model.updateMaxI(max);
        if (option.equals("item")) {
            this.model.getFiltered();
        }
        if (option.equals("set")) {
            this.model.getFilteredSets();
        }
    }

    public void averageMinFieldChange(String averageMin, String option) {
        this.model.updateAverageMinI(averageMin);
        if (option.equals("item")) {
            this.model.getFiltered();
        }
        if (option.equals("set")) {
            this.model.getFilteredSets();
        }
    }

    public void averageMaxFieldChange(String averageMax, String option) {
        this.model.updateAverageMaxI(averageMax);
        if (option.equals("item")) {
            this.model.getFiltered();
        }
        if (option.equals("set")) {
            this.model.getFilteredSets();
        }
    }

    public void rarityFieldChange(String rarity) {
        this.model.updateRarityI(rarity);
        this.model.getFiltered();
    }

    public void createItem(String name, String price) {
        this.model.createItem(name, Integer.parseInt(price));
    }

    public void createMod(String name, String price, String polarity) {
        this.model.createMod(name, Integer.parseInt(price), polarity);
    }

    public void createPrimePart(String name, String price, String rarity, String color) {
        this.model.createPrimePart(name, Integer.parseInt(price), Integer.parseInt(rarity), color);
    }

    public void createRiven(String name, String price, String polarity, String reroll) {
        this.model.createRiven(name, Integer.parseInt(price), polarity, Integer.parseInt(reroll));
    }

    public String getSetSeperatedPrice(PrimeSet set) {
        return this.model.getSeperatedSet(set);
    }

    public String getPrimePartName(PrimeSet set, int i) {
        return this.model.getPrimePartName(set, i);
    }

    public void showAllSets() {
        this.model.getFilteredSets();
    }

    public ArrayList<PrimePart> getAllPrimeParts() {
        return this.model.getAllPrimeParts();
    }

    public PrimePart getPrimePart(String id){
        if(id != null && !id.equals("null")){
            return this.model.getPrimePart(Integer.parseInt(id));
        }
        else {
            return null;
        }
    }

    public String getSetSeperatedPriceRelic(Relic relic) {
        return this.model.getSeperatedRelic(relic);
    }

    public String getPrimePartNameRelic(Relic relic, int i) {
        return this.model.getPrimePartNameRelic(relic,i);
    }

    public void showAllRelics() {
        this.model.getFilteredRelics();
    }
}
