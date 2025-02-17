package org.example.markethelper.Model.DAL.PrimePart;

import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.DAL.PrimeSet.PrimeSetDAO;

import java.sql.*;
import java.util.ArrayList;

public class PrimePartDAO implements IPrimePartDAO {
    private Connection con;
    PreparedStatement insertPrimePart;
    PreparedStatement getAllPrimeParts;
    PreparedStatement getPrimePart;
    PreparedStatement deletePrimePart;
    PreparedStatement updatePrimePart;

    public PrimePartDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS PrimePart (id INT PRIMARY KEY, name VARCHAR(100), price INT, rarity INT, color VARCHAR(50))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertPrimePart = this.con.prepareStatement("INSERT INTO PrimePart (id, name, price, rarity, color) VALUES (?, ?, ?, ?, ?)");
            this.deletePrimePart = this.con.prepareStatement("DELETE FROM PrimePart WHERE id=?");
            this.getAllPrimeParts = this.con.prepareStatement("SELECT * FROM PrimePart");
            this.getPrimePart = this.con.prepareStatement("SELECT * FROM PrimePart WHERE id = ?");
            this.updatePrimePart = this.con.prepareStatement("UPDATE PrimePart SET name=?, price=?, rarity=?, color=? WHERE id=?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addPrimePart(PrimePart part) {
        try {
            this.insertPrimePart.setInt(1, part.getId());
            this.insertPrimePart.setString(2, part.getName());
            this.insertPrimePart.setInt(3, part.getPrice());
            this.insertPrimePart.setInt(4, part.getRarity());
            this.insertPrimePart.setString(5, part.getColor());
            this.insertPrimePart.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<PrimePart> getPrimeParts() {
        ArrayList<PrimePart> primeParts = new ArrayList<>();
        try {
            ResultSet set = this.getAllPrimeParts.executeQuery();
            while (set.next()) {
                primeParts.add(new PrimePart(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getInt("rarity"), set.getString("color")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primeParts;
    }

    @Override
    public PrimePart getPrimePart(int id) {
        try {
            this.getPrimePart.setInt(1, id);
            ResultSet set = this.getPrimePart.executeQuery();
            if (set.next()) {
                return new PrimePart(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getInt("rarity"), set.getString("color"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updatePrimePart(PrimePart part) {
        try {
            this.updatePrimePart.setString(1, part.getName());
            this.updatePrimePart.setInt(2, part.getPrice());
            this.updatePrimePart.setInt(3, part.getRarity());
            this.updatePrimePart.setString(4, part.getColor());
            this.updatePrimePart.setInt(5, part.getId());
            this.updatePrimePart.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePrimePart(int id) {
        boolean bool;
        try {
            PrimeSetDAO primeSetDAO = new PrimeSetDAO(con);
            primeSetDAO.deletePrimePartFromAllSets(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            this.deletePrimePart.setInt(1, id);
            this.deletePrimePart.executeUpdate();
            bool = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            bool = false;
        }
        return bool;
    }
    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertPrimePart != null) {
            try {
                this.insertPrimePart.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getPrimePart != null) {
            try {
                this.getPrimePart.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllPrimeParts != null) {
            try {
                this.getAllPrimeParts.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deletePrimePart != null) {
            try {
                this.deletePrimePart.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.updatePrimePart != null) {
            try {
                this.updatePrimePart.close();
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
