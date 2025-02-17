package org.example.markethelper.Model.DAL.Item;

import org.example.markethelper.Model.BL.Item;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAO implements IItemDAO {
    private Connection con;
    PreparedStatement insertItem;
    PreparedStatement getAllItems;
    PreparedStatement getItem;
    PreparedStatement deleteItem;
    PreparedStatement updateItem;

    public ItemDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Item (id INT PRIMARY KEY, name VARCHAR(100), price INT)");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertItem = this.con.prepareStatement("INSERT INTO Item (id, name, price) VALUES (?, ?, ?)");
            this.deleteItem = this.con.prepareStatement("DELETE FROM Item WHERE id=?");
            this.getAllItems = this.con.prepareStatement("SELECT * FROM Item");
            this.getItem = this.con.prepareStatement("SELECT * FROM Item WHERE id = ?");
            this.updateItem = this.con.prepareStatement("UPDATE Item SET name=?, price=? WHERE id=?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addItem(Item item) {
        try {
            this.insertItem.setInt(1, item.getId());
            this.insertItem.setString(2, item.getName());
            this.insertItem.setInt(3, item.getPrice());
            this.insertItem.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        try {
            ResultSet set = this.getAllItems.executeQuery();
            while (set.next()) {
                items.add(new Item(set.getInt("id"), set.getString("name"), set.getInt("price")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return items;
    }

    @Override
    public Item getItem(int id) {
        try {
            this.getItem.setInt(1, id);
            ResultSet set = this.getItem.executeQuery();
            if (set.next()) {
                return new Item(set.getInt("id"), set.getString("name"), set.getInt("price"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateItem(Item item) {
        try {
            this.updateItem.setString(1, item.getName());
            this.updateItem.setInt(2, item.getPrice());
            this.updateItem.setInt(3, item.getId());
            this.updateItem.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteItem(int id) {
        try {
            this.deleteItem.setInt(1, id);
            this.deleteItem.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertItem != null) {
            try {
                this.insertItem.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.updateItem != null) {
            try {
                this.updateItem.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deleteItem != null) {
            try {
                this.deleteItem.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getItem != null) {
            try {
                this.getItem.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllItems != null) {
            try {
                this.getAllItems.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.con != null) {
            try {
                this.con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        return safe;
    }
}
