package org.example.markethelper.Model;

import org.example.markethelper.Model.BL.Item;
import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.BL.PrimeSet;
import org.example.markethelper.Model.BL.Relic;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface IModel {
    void addPropertyChangeListener(PropertyChangeListener pcl);
    void removePropertyChangeListener(PropertyChangeListener pcl);
    void close();
    void getFiltered();
    boolean createUser(String id, String pass);
    boolean verifyUser(String id, String pass);
    String getItemRarity(Item item);
    String getItemReroll(Item item);
    String getItemPolarity(Item item);
    String getItemType(Item item);
    void boolChange(String check);
    void updateMinI(String min);
    void updateMaxI(String max);
    void updateAverageMinI(String averageMin);
    void updateAverageMaxI(String averageMax);
    void updateRarityI(String rarity);
    void createItem(String name, int i);
    void createMod(String name, int i, String polarity);
    void createPrimePart(String name, int i, int i1, String color);
    void createRiven(String name, int i, String polarity, int i1);
    String getSeperatedSet(PrimeSet set);
    String getPrimePartName(PrimeSet set, int i);
    void getFilteredSets();
    void boolReset();
    ArrayList<PrimePart> getAllPrimeParts();
    PrimePart getPrimePart(int i);
    void createSet(String name, int price, int part1, int part2, int part3, int part4, int part5, int part6);
    void deleteItem(int i);
    void deleteSet(int i);
    void updateItem(int id, String name, int price);
    void updateMod(int id, String name, int price, String polarity);
    void updatePrimePart(int id, String name, int price, int rarity, String color);
    void updateRiven(int id, String name, int price, String polarity, int rerolls);
    void updatePrimeSet(int id, String name, int price, int part1, int part2, int part3, int part4, int part5, int part6);

    void updatePrimeRelic(int id, String name, int price, int part1, int part2, int part3, int part4, int part5, int part6);

    void deleteRelic(int id);

    void createRelic(String name, int price, int part1, int part2, int part3, int part4, int part5, int part6);

    void getFilteredRelics();

    String getPrimePartNameRelic(Relic relic, int i);

    String getSeperatedRelic(Relic relic);
}
