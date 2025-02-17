package org.example.markethelper.Model.DAL.Riven;

import org.example.markethelper.Model.BL.Riven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RivenDAO implements IRivenDAO {
    private Connection con;
    PreparedStatement insertRiven;
    PreparedStatement getAllRivens;
    PreparedStatement getRiven;
    PreparedStatement deleteRiven;
    PreparedStatement updateRiven;

    public RivenDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Riven (id INTEGER PRIMARY KEY, name VARCHAR(100), price INT, polarity VARCHAR(50), rerolls INT)");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertRiven = this.con.prepareStatement("INSERT INTO Riven (id, name, price, polarity, rerolls) VALUES (?, ?, ?, ?, ?)");
            this.deleteRiven = this.con.prepareStatement("DELETE FROM Riven WHERE id=?");
            this.getAllRivens = this.con.prepareStatement("SELECT id, name, price, polarity, rerolls FROM Riven");
            this.getRiven = this.con.prepareStatement("SELECT * FROM Riven WHERE id = ?");
            this.updateRiven = this.con.prepareStatement("UPDATE Riven SET name=?, price=?, polarity=?, rerolls=? WHERE id=?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addRiven(Riven riven) {
        try {
            this.insertRiven.setInt(1, riven.getId());
            this.insertRiven.setString(2, riven.getName());
            this.insertRiven.setInt(3, riven.getPrice());
            this.insertRiven.setString(4, riven.getPolarity());
            this.insertRiven.setInt(5, riven.getRerolls());
            this.insertRiven.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Riven getRiven(int id) {
        try {
            this.getRiven.setInt(1, id);
            ResultSet set = this.getRiven.executeQuery();
            if (set.next()) {
                return new Riven(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getString("polarity"), set.getInt("rerolls"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Riven> getRivens() {
        ArrayList<Riven> rivens = new ArrayList<>();
        try {
            ResultSet set = this.getAllRivens.executeQuery();
            while (set.next()) {
                rivens.add(new Riven(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getString("polarity"), set.getInt("rerolls")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rivens;
    }

    @Override
    public boolean deleteRiven(int id) {
        try {
            this.deleteRiven.setInt(1, id);
            this.deleteRiven.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateRiven(Riven riven) {
        try {
            this.updateRiven.setString(1, riven.getName());
            this.updateRiven.setInt(2, riven.getPrice());
            this.updateRiven.setString(3, riven.getPolarity());
            this.updateRiven.setInt(4, riven.getRerolls());
            this.updateRiven.setInt(5, riven.getId());
            this.updateRiven.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertRiven != null) {
            try {
                this.insertRiven.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }
        if (this.getRiven != null) {
            try {
                this.getRiven.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }
        if (this.getAllRivens != null) {
            try {
                this.getAllRivens.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }
        if (this.deleteRiven != null) {
            try {
                this.deleteRiven.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }
        if (this.updateRiven != null) {
            try {
                this.updateRiven.close();
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
