package org.example.markethelper.Model.DAL.PrimePart;

import org.example.markethelper.Model.BL.PrimePart;

import java.util.ArrayList;

public interface IPrimePartDAO {
    public ArrayList<PrimePart> getPrimeParts();
    public PrimePart getPrimePart(int id);
    public boolean addPrimePart(PrimePart part);
    public boolean deletePrimePart(int id);
    public boolean updatePrimePart(PrimePart part);
    public boolean close();
}
