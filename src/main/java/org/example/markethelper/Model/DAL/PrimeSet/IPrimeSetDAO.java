package org.example.markethelper.Model.DAL.PrimeSet;

import org.example.markethelper.Model.BL.PrimePart;
import org.example.markethelper.Model.BL.PrimeSet;

import java.util.ArrayList;

public interface IPrimeSetDAO {

    boolean addPrimeSet(PrimeSet set);
    ArrayList<PrimeSet> getAllPrimeSets();
    PrimeSet getPrimeSet(int id);
    boolean updatePrimeSet(PrimeSet set);
    boolean deletePrimeSet(int id);
    void deletePrimePartFromAllSets(int primePartId);
    boolean addPrimePartToSet(int primeSetID, PrimePart primePart);
    boolean deletePrimePartFromSet(int primeSetID, PrimePart primePart);
    ArrayList<PrimePart> getPrimePartsForSet(int primeSetID);
    boolean close();
}
