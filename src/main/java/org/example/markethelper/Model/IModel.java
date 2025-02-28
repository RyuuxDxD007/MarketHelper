package org.example.markethelper.Model;

import org.example.markethelper.Model.BL.Item;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public interface IModel {
    void addPropertyChangeListener(PropertyChangeListener pcl);
    void removePropertyChangeListener(PropertyChangeListener pcl);
    void close();
    //void getAllItems();
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
}
