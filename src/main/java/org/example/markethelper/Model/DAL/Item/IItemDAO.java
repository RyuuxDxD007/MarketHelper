package org.example.markethelper.Model.DAL.Item;


import org.example.markethelper.Model.BL.Item;

import java.util.ArrayList;

public interface IItemDAO {

    ArrayList<Item> getItems();
    Item getItem(int id);
    boolean addItem(Item item);
    boolean updateItem(Item item);
    boolean deleteItem(int id);
    boolean close();

}
