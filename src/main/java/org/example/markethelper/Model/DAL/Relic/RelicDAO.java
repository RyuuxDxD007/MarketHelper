package org.example.markethelper.Model.DAL.Relic;

import org.example.markethelper.Model.BL.Relic;
import org.example.markethelper.Model.BL.PrimePart;

import java.sql.*;
import java.util.ArrayList;

public class RelicDAO implements IRelicDAO {

    private Connection con;
    PreparedStatement insertRelic;
    PreparedStatement getAllRelics;
    PreparedStatement getRelic;
    PreparedStatement deleteRelic;
    PreparedStatement updateRelic;
    PreparedStatement addPrimePartToRelic;
    PreparedStatement deletePrimePartFromRelic;
    PreparedStatement deletePartFromAllRelics;
    PreparedStatement getPrimePartsForRelic;

    public RelicDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Relic (id INT PRIMARY KEY, setPrice INT, seperatedPrice INT)");
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS RelicPrimePart (relicID INT, primePartID INT, PRIMARY KEY (relicID, primePartID), FOREIGN KEY (relicID) REFERENCES Relic(id), FOREIGN KEY (primePartID) REFERENCES PrimePart(id))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();

            this.insertRelic = this.con.prepareStatement("INSERT INTO Relic (id, setPrice, seperatedPrice) VALUES (?, ?, ?)");
            this.deleteRelic = this.con.prepareStatement("DELETE FROM Relic WHERE id=?");
            this.getAllRelics = this.con.prepareStatement("SELECT * FROM Relic");
            this.getRelic = this.con.prepareStatement("SELECT * FROM Relic WHERE id = ?");
            this.updateRelic = this.con.prepareStatement("UPDATE Relic SET setPrice=?, seperatedPrice=? WHERE id=?");
            this.addPrimePartToRelic = this.con.prepareStatement("INSERT INTO RelicPrimePart (relicID, primePartID) VALUES (?, ?)");
            this.deletePrimePartFromRelic = this.con.prepareStatement("DELETE FROM RelicPrimePart WHERE relicID = ? AND primePartID = ?");
            this.deletePartFromAllRelics = this.con.prepareStatement("DELETE FROM RelicPrimePart WHERE primePartID = ?");
            this.getPrimePartsForRelic = this.con.prepareStatement("SELECT pp.* FROM PrimePart pp JOIN RelicPrimePart rpp ON pp.id = rpp.primePartID WHERE rpp.relicID = ?");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addRelic(Relic relic) {
        try {
            this.insertRelic.setInt(1, relic.getSetPrice());
            this.insertRelic.setInt(2, relic.getSetPrice());
            this.insertRelic.setInt(3, relic.getSeperatedPrice());
            this.insertRelic.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Relic> getAllRelics() {
        ArrayList<Relic> relics = new ArrayList<>();
        try {
            ResultSet rs = this.getAllRelics.executeQuery();
            while (rs.next()) {
                relics.add(new Relic(new ArrayList<>(), rs.getInt("setPrice")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return relics;
    }

    @Override
    public Relic getRelic(int id) {
        try {
            this.getRelic.setInt(1, id);
            ResultSet rs = this.getRelic.executeQuery();
            if (rs.next()) {
                return new Relic(new ArrayList<>(), rs.getInt("setPrice"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateRelic(Relic relic) {
        try {
            this.updateRelic.setInt(1, relic.getSetPrice());
            this.updateRelic.setInt(2, relic.getSeperatedPrice());
            this.updateRelic.setInt(3, relic.getSetPrice());
            this.updateRelic.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteRelic(int id) {
        try {
            this.deleteRelic.setInt(1, id);
            this.deleteRelic.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void deletePrimePartFromAllRelics(int primePartId) {
        try {
            this.deletePartFromAllRelics.setInt(1, primePartId);
            this.deletePartFromAllRelics.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addPrimePartToRelic(int relicID, PrimePart primePart) {
        try {
            this.addPrimePartToRelic.setInt(1, relicID);
            this.addPrimePartToRelic.setInt(2, primePart.getId());
            this.addPrimePartToRelic.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePrimePartFromRelic(int relicID, PrimePart primePart) {
        try {
            this.deletePrimePartFromRelic.setInt(1, relicID);
            this.deletePrimePartFromRelic.setInt(2, primePart.getId());
            this.deletePrimePartFromRelic.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<PrimePart> getPrimePartsForRelic(int relicID) {
        ArrayList<PrimePart> primeParts = new ArrayList<>();
        try {
            this.getPrimePartsForRelic.setInt(1, relicID);
            ResultSet rs = this.getPrimePartsForRelic.executeQuery();
            while (rs.next()) {
                primeParts.add(new PrimePart(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("rarity"), rs.getString("color")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return primeParts;
    }

    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertRelic != null) {
            try {
                this.insertRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getRelic != null) {
            try {
                this.getRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllRelics != null) {
            try {
                this.getAllRelics.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deleteRelic != null) {
            try {
                this.deleteRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.updateRelic != null) {
            try {
                this.updateRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.addPrimePartToRelic != null) {
            try {
                this.addPrimePartToRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deletePrimePartFromRelic != null) {
            try {
                this.deletePrimePartFromRelic.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getPrimePartsForRelic != null) {
            try {
                this.getPrimePartsForRelic.close();
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
