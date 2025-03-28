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
        PreparedStatement deletePartFromAll;
        PreparedStatement getPrimePartsForRelic;
        PreparedStatement deleteRelicPartIntermediate;

        public RelicDAO(Connection con) {
            try {
                this.con = con;
                Statement statement = con.createStatement();
                try {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS Relic (id INT PRIMARY KEY, relicName VARCHAR(255), relicPrice INT)");
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS RelicPrimePart (relicID INT, primePartID INT, PRIMARY KEY (relicID, primePartID), FOREIGN KEY (relicID) REFERENCES Relic(id), FOREIGN KEY (primePartID) REFERENCES PrimePart(id))");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                statement.close();

                this.insertRelic = this.con.prepareStatement("INSERT INTO Relic (id, relicName, relicPrice) VALUES (?, ?, ?)");
                this.deleteRelic = this.con.prepareStatement("DELETE FROM Relic WHERE id=?");
                this.getAllRelics = this.con.prepareStatement("SELECT * FROM Relic");
                this.getRelic = this.con.prepareStatement("SELECT * FROM Relic WHERE id = ?");
                this.updateRelic = this.con.prepareStatement("UPDATE Relic SET relicName=?, relicPrice=? WHERE id=?");
                this.addPrimePartToRelic = this.con.prepareStatement("INSERT INTO RelicPrimePart (relicID, primePartID) VALUES (?, ?)");
                this.deletePrimePartFromRelic = this.con.prepareStatement("DELETE FROM RelicPrimePart WHERE relicID = ? AND primePartID = ?");
                this.deletePartFromAll = this.con.prepareStatement("DELETE FROM RelicPrimePart WHERE primePartId = ?");
                this.deleteRelicPartIntermediate = this.con.prepareStatement("DELETE FROM RelicPrimePart WHERE relicID = ?");
                this.getPrimePartsForRelic = this.con.prepareStatement("SELECT pp.* FROM PrimePart pp JOIN RelicPrimePart rpp ON pp.id = rpp.primePartID WHERE rpp.relicID = ?");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public boolean addRelic(Relic relic) {
            try {
                this.insertRelic.setInt(1, relic.getRelicId());
                this.insertRelic.setString(2, relic.getRelicName());
                this.insertRelic.setInt(3, relic.getRelicPrice());
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
                ResultSet set = this.getAllRelics.executeQuery();
                while (set.next()) {
                    int relicId = set.getInt("id");
                    ArrayList<PrimePart> primeParts = getPrimePartsForRelic(relicId);
                    relics.add(new Relic(relicId, set.getString("relicName"), primeParts, set.getInt("relicPrice")));
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
                ResultSet set = this.getRelic.executeQuery();

                if (set.next()) {
                    ArrayList<PrimePart> primeParts = getPrimePartsForRelic(id);

                    return new Relic(
                            set.getInt("id"),
                            set.getString("relicName"),
                            primeParts,
                            set.getInt("relicPrice")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return null;
        }

        @Override
        public boolean updateRelic(Relic relic) {
            try {
                this.updateRelic.setString(1, relic.getRelicName());
                this.updateRelic.setInt(2, relic.getRelicPrice());
                this.updateRelic.setInt(3, relic.getRelicId());
                this.updateRelic.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        @Override
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
        public boolean deletePrimePartFromAllRelics(int primePartId) {
            try {
                this.deletePartFromAll.setInt(1, primePartId);
                this.deletePartFromAll.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        @Override
        public boolean deleteRelicPartIntermediate(int relicId) {
            try {
                this.deleteRelicPartIntermediate.setInt(1, relicId);
                this.deleteRelicPartIntermediate.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return false;
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
                ResultSet set = this.getPrimePartsForRelic.executeQuery();
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
