package org.example.markethelper.Model;

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

    @Override
    public void getAllItems(){
        ArrayList<Item> all = new ArrayList<>();
        ArrayList<PrimePart> primeparts = primePartDAO.getPrimeParts();
        ArrayList<Item> items = itemDAO.getItems();
        ArrayList<Mod> mods = modDAO.getMods();
        ArrayList<Riven> rivens = rivenDAO.getRivens();
        all.addAll(items);
        all.addAll(mods);
        all.addAll(rivens);
        all.addAll(primeparts);
        support.firePropertyChange("items", null, all);
    }
    public void getAllPrimeSets(){
        ArrayList<PrimeSet> primeSets = primeSetDAO.getAllPrimeSets();

    }
    public void getAllRelics(){
        ArrayList<Relic> relics = relicDAO.getAllRelics();
    }

    public boolean createUser(String id, String pass) {
        return userDAO.addUser(id, pass);
    }

    public boolean verifyUser(String id, String pass) {
        return userDAO.verifyUser(id, pass);
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
