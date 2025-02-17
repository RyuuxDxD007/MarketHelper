package org.example.markethelper.Model.DAL.Item;


import org.example.markethelper.Model.BL.Item;

import java.util.ArrayList;

public interface IItemDAO {

    public ArrayList<Item> getItems();
    public Item getItem(int id);
    public boolean addItem(Item item);
    public boolean updateItem(Item item);
    public boolean deleteItem(int id);
    public boolean close();

}
