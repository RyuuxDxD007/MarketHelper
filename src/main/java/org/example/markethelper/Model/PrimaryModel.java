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

public class PrimaryModel implements IModel {
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
    boolean rarityB = false;

    private int minI;
    private int maxI;
    private int averageMinI;
    private int averageMaxI;
    private int rarityI;


    public PrimaryModel() {
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
        } catch (Exception e) {
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


    public ArrayList<Item> getAllItems() {
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

    public void getAllPrimeSets() {
        support.firePropertyChange("show-allSets", null, primeSetDAO.getAllPrimeSets());
    }

    public void getAllRelics() {
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

    public ArrayList<Item> Filtring() {
        ArrayList<Item> filtered = new ArrayList<>();
        filtered = getAllItems();
        for (int i = filtered.size() - 1; i >= 0; i--){
            if (minB) {
                if (filtered.get(i).getPrice() <= minI) {
                    filtered.remove(i);
                }
            }if (maxB) {
                if (filtered.get(i).getPrice() >= maxI) {
                    filtered.remove(i);
                }
            }if (averageMinB) {
                if (filtered.get(i).getPrice() <= averageMinI) {
                    filtered.remove(i);
                }
            }if (averageMaxB) {
                if (filtered.get(i).getPrice() >= averageMaxI) {
                    filtered.remove(i);
                }
            }if (itemB) {
                if (getItemType(filtered.get(i)).equals("Item")) {
                    filtered.remove(i);
                }
            }if (modB) {
                if (getItemType(filtered.get(i)).equals("Mod")) {
                    filtered.remove(i);
                }
            }if (rivenB) {
                if (getItemType(filtered.get(i)).equals("Riven")) {
                    filtered.remove(i);
                }
            }if (primeB) {
                if (getItemType(filtered.get(i)).equals("PrimePart")) {
                    filtered.remove(i);
                }
            }if (rarityB) {
                if (getItemType(filtered.get(i)).equals("PrimePart")) {
                    PrimePart prime = (PrimePart) filtered.get(i);
                    if (prime.getRarity() != rarityI) {
                        filtered.remove(i);
                    }
                }
            }
        }

        return filtered;
    }

    public void getFiltered() {
        support.firePropertyChange("show-allItems", null, Filtring());
    }

    public void boolChange(String check) {
        switch (check) {
            case "select-min":
                minB = !minB;
                break;
            case "select-max":
                maxB = !maxB;
                break;
            case "select-averageMin":
                averageMinB = !averageMinB;
                break;
            case "select-averageMax":
                averageMaxB = !averageMaxB;
                break;
            case "select-item":
                itemB = !itemB;
                break;
            case "select-mod":
                modB = !modB;
                break;
            case "select-prime":
                primeB = !primeB;
                break;
            case "select-riven":
                rivenB = !rivenB;
                break;
            case "select-rarity":
                rarityB = !rarityB;
                break;
        }
    }

    public void updateMinI(String min) {
        if (min.matches("0|[1-9]\\d*")) {
            minI = Integer.parseInt(min);
        } else {
            minI = 0;
        }
    }

    public void updateMaxI(String max) {
        if (max.matches("0|[1-9]\\d*")) {
            maxI = Integer.parseInt(max);
        } else {
            maxI = 0;
        }
    }

    public void updateAverageMinI(String averageMin) {
        if (averageMin.matches("0|[1-9]\\d*")) {
            averageMinI = Integer.parseInt(averageMin);
        } else {
            averageMinI = 0;
        }
    }

    public void updateAverageMaxI(String averageMax) {
        if (averageMax.matches("0|[1-9]\\d*")) {
            averageMaxI = Integer.parseInt(averageMax);
        } else {
            averageMaxI = 0;
        }
    }

    public void updateRarityI(String rarity){
        rarityI=Integer.parseInt(rarity);
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
