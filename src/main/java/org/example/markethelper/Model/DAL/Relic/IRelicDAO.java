package org.example.markethelper.Model.DAL.Relic;

import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.BL.Relic;

import java.util.ArrayList;

public interface IRelicDAO {

    boolean addRelic(Relic relic);
    ArrayList<Relic> getAllRelics();
    Relic getRelic(int id);
    boolean updateRelic(Relic relic);
    boolean deleteRelic(int id);
    void deletePrimePartFromAllRelics(int primePartId);
    boolean addPrimePartToRelic(int relicID, PrimePart primePart);
    boolean deletePrimePartFromRelic(int relicID, PrimePart primePart);
    ArrayList<PrimePart> getPrimePartsForRelic(int relicID);
    boolean close();


}
