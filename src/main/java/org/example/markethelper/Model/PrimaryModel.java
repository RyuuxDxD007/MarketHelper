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

    private int totalSizeItems;
    private int totalSizeSets;
    final private int ERROR_CODE = -1;


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
        totalSizeItems = all.size();
        return all;
    }

    public ArrayList<PrimeSet> getAllPrimeSets() {
        ArrayList<PrimeSet> all = new ArrayList<>();
        all = primeSetDAO.getAllPrimeSets();
        totalSizeSets = all.size();
        return all;
    }

    public ArrayList<PrimePart> getAllPrimeParts() {
        return primePartDAO.getPrimeParts();
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
    public void boolReset(){
        minB = false;
        maxB = false;
        averageMinB = false;
        averageMaxB = false;
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
            case "select-min-set":
                minB = !minB;
                break;
            case "select-max-set":
                maxB = !maxB;
                break;
            case "select-averageMin-set":
                averageMinB = !averageMinB;
                break;
            case "select-averageMax-set":
                averageMaxB = !averageMaxB;
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

    public void createItem(String name, int price) {
        Item item = new Item(totalSizeItems+1,name,price);
        itemDAO.addItem(item);
    }

    public void createMod(String name, int price, String polarity) {
        Mod mod = new Mod(totalSizeItems+1,name,price,polarity);
        modDAO.addMod(mod);
    }

    public void createPrimePart(String name, int price, int rarity, String color) {
        if(rarity==15){
            color = "Bronze";
        }
        else if(rarity==45){
            color = "Silver";
        }
        else if(rarity==100){
            color = "Gold";
        }
        PrimePart prime = new PrimePart(totalSizeItems+1,name,price,rarity,color);
        primePartDAO.addPrimePart(prime);
    }

    public void createRiven(String name, int price, String polarity, int reroll) {
        Riven riven = new Riven(totalSizeItems+1,name,price,polarity,reroll);
        rivenDAO.addRiven(riven);
    }

    public String getSeperatedSet(PrimeSet set) {
        return String.valueOf(set.getSeperatedPrice());
    }

    public String getPrimePartName(PrimeSet set, int i) {
        if(i<set.getPrimePartsSize()) {
            return set.getPrimePart(i).getName();
        }
        else {
            return "--";
        }
    }

    public void getFilteredSets() {
        support.firePropertyChange("show-allSets", null, SetFiltring());
    }
    public ArrayList<PrimeSet> SetFiltring() {
        ArrayList<PrimeSet> filtered = new ArrayList<>();
        filtered = getAllPrimeSets();
        for (int i = filtered.size() - 1; i >= 0; i--){
            if (minB) {
                if (filtered.get(i).getSetPrice() <= minI) {
                    filtered.remove(i);
                }
            }if (maxB) {
                if (filtered.get(i).getSetPrice() >= maxI) {
                    filtered.remove(i);
                }
            }if (averageMinB) {
                if (filtered.get(i).getSetPrice() <= averageMinI) {
                    filtered.remove(i);
                }
            }if (averageMaxB) {
                if (filtered.get(i).getSetPrice() >= averageMaxI) {
                    filtered.remove(i);
                }
            }
        }

        return filtered;
    }

    public void createSet(String name, int price, int part1, int part2, int part3, int part4, int part5, int part6) {

        ArrayList<PrimePart> primeParts = new ArrayList<>();
        addNotNullPart(primeParts,part1);
        addNotNullPart(primeParts,part2);
        addNotNullPart(primeParts,part3);
        addNotNullPart(primeParts,part4);
        addNotNullPart(primeParts,part5);
        addNotNullPart(primeParts,part6);

        PrimeSet set = new PrimeSet(totalSizeSets+1,name, primeParts, price);
        primeSetDAO.addPrimeSet(set);
        for (PrimePart part : primeParts) {
            primeSetDAO.addPrimePartToSet(totalSizeSets+1, part);
        }
    }

    private void addNotNullPart(ArrayList<PrimePart> primeParts, int id) {
        if (id != ERROR_CODE) {
            PrimePart part = primePartDAO.getPrimePart(id);
            primeParts.add(part);
        }
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
