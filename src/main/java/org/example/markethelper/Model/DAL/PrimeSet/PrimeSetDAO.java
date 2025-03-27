package org.example.markethelper.Model.DAL.PrimeSet;

import org.example.markethelper.Model.BL.PrimeSet;
import org.example.markethelper.Model.BL.PrimePart;
import java.sql.*;
import java.util.ArrayList;

public class PrimeSetDAO implements IPrimeSetDAO {  // Implementing the IPrimeSetDAO interface

    private Connection con;
    PreparedStatement insertPrimeSet;
    PreparedStatement getAllPrimeSets;
    PreparedStatement getPrimeSet;
    PreparedStatement deletePrimeSet;
    PreparedStatement updatePrimeSet;
    PreparedStatement addPrimePartToSet;
    PreparedStatement deletePrimePartFromSet;
    PreparedStatement deletePartFromAll;
    PreparedStatement getPrimePartsForSet;

    public PrimeSetDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS PrimeSet (id INT PRIMARY KEY, setName VARCHAR(255), setPrice INT)");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS PrimeSetPrimePart (primeSetID INT, primePartID INT, PRIMARY KEY (primeSetID, primePartID), FOREIGN KEY (primeSetID) REFERENCES PrimeSet(id), FOREIGN KEY (primePartID) REFERENCES PrimePart(id))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();

            this.insertPrimeSet = this.con.prepareStatement("INSERT INTO PrimeSet (id, setName, setPrice) VALUES (?, ?, ?)");
            this.deletePrimeSet = this.con.prepareStatement("DELETE FROM PrimeSet WHERE id=?");
            this.getAllPrimeSets = this.con.prepareStatement("SELECT * FROM PrimeSet");
            this.getPrimeSet = this.con.prepareStatement("SELECT * FROM PrimeSet WHERE id = ?");
            this.updatePrimeSet = this.con.prepareStatement("UPDATE PrimeSet SET setName=?, setPrice=? WHERE id=?");
            this.addPrimePartToSet = this.con.prepareStatement("INSERT INTO PrimeSetPrimePart (primeSetID, primePartID) VALUES (?, ?)");
            this.deletePrimePartFromSet = this.con.prepareStatement("DELETE FROM PrimeSetPrimePart WHERE primeSetID = ? AND primePartID = ?");
            this.deletePartFromAll = this.con.prepareStatement("DELETE FROM PrimeSetPrimePart WHERE primePartId = ?");
            this.getPrimePartsForSet = this.con.prepareStatement("SELECT pp.* FROM PrimePart pp JOIN PrimeSetPrimePart psp ON pp.id = psp.primePartID WHERE psp.primeSetID = ?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addPrimeSet(PrimeSet set) {
        try {
            this.insertPrimeSet.setInt(1, set.getSetId());
            this.insertPrimeSet.setString(2,set.getSetName());
            this.insertPrimeSet.setInt(3, set.getSetPrice());
            this.insertPrimeSet.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<PrimeSet> getAllPrimeSets() {
        ArrayList<PrimeSet> primeSets = new ArrayList<>();
        try {
            ResultSet set = this.getAllPrimeSets.executeQuery();
            while (set.next()) {
                int setId = set.getInt("id");
                ArrayList<PrimePart> primeParts = getPrimePartsForSet(setId);
                primeSets.add(new PrimeSet(setId, set.getString("setName"), primeParts, set.getInt("setPrice")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primeSets;
    }


    @Override
    public PrimeSet getPrimeSet(int id) {
        try {
            this.getPrimeSet.setInt(1, id);
            ResultSet set = this.getPrimeSet.executeQuery();

            if (set.next()) {
                ArrayList<PrimePart> primeParts = getPrimePartsForSet(id);

                return new PrimeSet(
                        set.getInt("id"),
                        set.getString("setName"),
                        primeParts,
                        set.getInt("setPrice")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public boolean updatePrimeSet(PrimeSet set) {
        try {
            this.updatePrimeSet.setInt(1, set.getSetPrice());
            this.updatePrimeSet.setString(2, set.getSetName());
            this.updatePrimeSet.setInt(3, set.getSetId());
            this.updatePrimeSet.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePrimeSet(int id) {
        try {
            this.deletePrimeSet.setInt(1, id);
            this.deletePrimeSet.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deletePrimePartFromAllSets(int primePartId) {
        try {
            this.deletePartFromAll.setInt(1, primePartId);
            this.deletePartFromAll.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addPrimePartToSet(int primeSetID, PrimePart primePart) {
        try {
            this.addPrimePartToSet.setInt(1, primeSetID);
            this.addPrimePartToSet.setInt(2, primePart.getId());
            this.addPrimePartToSet.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePrimePartFromSet(int primeSetID, PrimePart primePart) {
        try {
            this.deletePrimePartFromSet.setInt(1, primeSetID);
            this.deletePrimePartFromSet.setInt(2, primePart.getId());
            this.deletePrimePartFromSet.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<PrimePart> getPrimePartsForSet(int primeSetID) {
        ArrayList<PrimePart> primeParts = new ArrayList<>();
        try {
            this.getPrimePartsForSet.setInt(1, primeSetID);
            ResultSet set = this.getPrimePartsForSet.executeQuery();
            while (set.next()) {
                primeParts.add(new PrimePart(set.getInt("id"), set.getString("name"), set.getInt("price"), set.getInt("rarity"), set.getString("color")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primeParts;
    }

    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertPrimeSet != null) {
            try {
                this.insertPrimeSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getPrimeSet != null) {
            try {
                this.getPrimeSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllPrimeSets != null) {
            try {
                this.getAllPrimeSets.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deletePrimeSet != null) {
            try {
                this.deletePrimeSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.updatePrimeSet != null) {
            try {
                this.updatePrimeSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.addPrimePartToSet != null) {
            try {
                this.addPrimePartToSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deletePrimePartFromSet != null) {
            try {
                this.deletePrimePartFromSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getPrimePartsForSet != null) {
            try {
                this.getPrimePartsForSet.close();
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
