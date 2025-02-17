package org.example.markethelper.Model.DAL.Mod;

import org.example.markethelper.Model.BL.Mod;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModDAO implements IModDAO {
    private Connection con;
    PreparedStatement insertMod;
    PreparedStatement getAllMods;
    PreparedStatement getMod;
    PreparedStatement deleteMod;
    PreparedStatement updateMod;

    public ModDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Mod (id INT PRIMARY KEY, name VARCHAR(100), price INT, polarity VARCHAR(50))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertMod = this.con.prepareStatement("INSERT INTO Mod (id, name, price, polarity) VALUES (?, ?, ?, ?)");
            this.deleteMod = this.con.prepareStatement("DELETE FROM Mod WHERE id=?");
            this.getAllMods = this.con.prepareStatement("SELECT * FROM Mod");
            this.getMod = this.con.prepareStatement("SELECT * FROM Mod WHERE id = ?");
            this.updateMod = this.con.prepareStatement("UPDATE Mod SET name=?, price=?, polarity=? WHERE id=?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addMod(Mod mod) {
        try {
            this.insertMod.setInt(1, mod.getId());
            this.insertMod.setString(2, mod.getName());
            this.insertMod.setInt(3, mod.getPrice());
            this.insertMod.setString(4, mod.getPolarity());
            this.insertMod.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Mod> getMods() {
        ArrayList<Mod> mods = new ArrayList<>();
        try {
            ResultSet set = this.getAllMods.executeQuery();
            while (set.next()) {
                mods.add(new Mod(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getString("polarity")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mods;
    }

    @Override
    public Mod getMod(String id) {
        try {
            this.getMod.setString(1, id);
            ResultSet set = this.getMod.executeQuery();
            if (set.next()) {
                return new Mod(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getString("polarity"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateMod(Mod mod) {
        try {
            this.updateMod.setString(1, mod.getName());
            this.updateMod.setInt(2, mod.getPrice());
            this.updateMod.setString(3, mod.getPolarity());
            this.updateMod.setInt(4, mod.getId());
            this.updateMod.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMod(int id) {
        try {
            this.deleteMod.setInt(1, id);
            this.deleteMod.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertMod != null) {
            try {
                this.insertMod.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getMod != null) {
            try {
                this.getMod.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllMods != null) {
            try {
                this.getAllMods.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deleteMod != null) {
            try {
                this.deleteMod.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.updateMod != null) {
            try {
                this.updateMod.close();
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
