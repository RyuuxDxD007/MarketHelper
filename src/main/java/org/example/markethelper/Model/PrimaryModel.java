package org.example.markethelper.Model;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.example.markethelper.Model.BL.*;
import org.example.markethelper.Model.DAL.Item.IItemDAO;
import org.example.markethelper.Model.DAL.Item.ItemDAO;
import org.example.markethelper.Model.DAL.Mod.IModDAO;
import org.example.markethelper.Model.DAL.Mod.ModDAO;
import org.example.markethelper.Model.DAL.PrimePart.IPrimePartDAO;
import org.example.markethelper.Model.DAL.PrimePart.PrimePartDAO;
import org.example.markethelper.Model.DAL.PrimeSet.IPrimeSetDAO;
import org.example.markethelper.Model.DAL.PrimeSet.PrimeSetDAO;
import org.example.markethelper.Model.DAL.Relic.IRelicDAO;
import org.example.markethelper.Model.DAL.Relic.RelicDAO;
import org.example.markethelper.Model.DAL.Riven.IRivenDAO;
import org.example.markethelper.Model.DAL.Riven.RivenDAO;
import org.example.markethelper.Model.DAL.User.IUserDAO;
import org.example.markethelper.Model.DAL.User.UserDAO;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class PrimaryModel implements IModel{
    private PropertyChangeSupport support;
    private DBConnection dbConn;
    private IRelicDAO relicDAO;
    private IItemDAO itemDAO;
    private IModDAO modDAO;
    private IRivenDAO rivenDAO;
    private IPrimePartDAO primePartDAO;
    private IPrimeSetDAO primeSetDAO;
    private IUserDAO userDAO;

    //boolean for filtering
    boolean minB = false;
    boolean maxB = false;
    boolean averageMinB = false;
    boolean averageMaxB = false;
    boolean itemB = false;
    boolean modB = false;
    boolean rivenB = false;
    boolean primeB = false;
    boolean RarityB = false;


    public PrimaryModel(){
        support = new PropertyChangeSupport(this);
        dbConn = new DBConnection();
        try {
            relicDAO = new RelicDAO(dbConn.getCon());
            itemDAO = new ItemDAO(dbConn.getCon());
            modDAO = new ModDAO(dbConn.getCon());
            rivenDAO = new RivenDAO(dbConn.getCon());
            primePartDAO = new PrimePartDAO(dbConn.getCon());
            primeSetDAO = new PrimeSetDAO(dbConn.getCon());
            userDAO = new UserDAO(dbConn.getCon());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


    public ArrayList<Item> getAllItems(){
        ArrayList<Item> all = new ArrayList<>();
        ArrayList<PrimePart> primeparts = primePartDAO.getPrimeParts();
        ArrayList<Item> items = itemDAO.getItems();
        ArrayList<Mod> mods = modDAO.getMods();
        ArrayList<Riven> rivens = rivenDAO.getRivens();
        all.addAll(items);
        all.addAll(mods);
        all.addAll(rivens);
        all.addAll(primeparts);
        return all;
    }
    public void getAllPrimeSets(){
        support.firePropertyChange("show-allSets", null, primeSetDAO.getAllPrimeSets());
    }
    public void getAllRelics(){
        support.firePropertyChange("show-allRelics", null, relicDAO.getAllRelics());
    }

    public boolean createUser(String id, String pass) {
        return userDAO.addUser(id, pass);
    }

    public boolean verifyUser(String id, String pass) {
        return userDAO.verifyUser(id, pass);
    }

    @Override
    public String getItemRarity(Item item) {
        if (item instanceof PrimePart) {
            return String.valueOf(((PrimePart) item).getRarity());
        } else {
            return "--";
        }
    }

    @Override
    public String getItemReroll(Item item) {
        if (item instanceof Riven) {
            return String.valueOf(((Riven) item).getRerolls());
        } else {
            return "--";
        }
    }

    @Override
    public String getItemPolarity(Item item) {
        if (item instanceof Mod) {
            return ((Mod) item).getPolarity();
        } else {
            return "--";
        }
    }

    @Override
    public String getItemType(Item item) {
        if (item instanceof PrimePart) {
            return "PrimePart";
        } else if (item instanceof Riven) {
            return "Riven";
        } else if (item instanceof Mod) {
            return "Mod";
        } else {
            return "Item";
        }
    }
    public ArrayList<Item> Filtring (){
        ArrayList<Item> filtered = new ArrayList<>();
        filtered = getAllItems();
        for (Item item : filtered) {
            if (minB) {
                if(item.getPrice()<=minI){
                    filtered.remove(item);
                }
            }
            if (maxB) {
                if(item.getPrice()>=maxI){
                    filtered.remove(item);
                }
            }
            if (averageMinB) {
                if(item.getPrice()<=averageMinI){
                    filtered.remove(item);
                }
            }
            if (averageMaxB) {
                if(item.getPrice()>=averageMaxI){
                    filtered.remove(item);
                }
            }
            if (itemB) {
                if(getItemType(item).equals("Item")){
                    filtered.remove(item);
                }
            }
            if (modB) {
                if(getItemType(item).equals("Mod")){
                    filtered.remove(item);
                }
            }
            if (rivenB) {
                if(getItemType(item).equals("Riven")){
                    filtered.remove(item);
                }
            }
            if (primeB) {
                if(getItemType(item).equals("PrimePart")){
                filtered.remove(item);
            }
            }
            if (RarityB) {
                if (item instanceof PrimePart) {
                    PrimePart primePartItem = (PrimePart) item;
                    if (primePartItem.getRarity() != RarityI) {
                        filtered.remove(item);
            }
        }

        return filtered;
    }
    public void getFiltered (){
        support.firePropertyChange("show-allItems", null, Filtring());
    }


    @Override
    public void close() {
        dbConn.close();
        relicDAO.close();
        itemDAO.close();
        modDAO.close();
        primePartDAO.close();
        primeSetDAO.close();
        userDAO.close();
    }
}
