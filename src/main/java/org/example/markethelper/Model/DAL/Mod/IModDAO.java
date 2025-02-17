package org.example.markethelper.Model.DAL.Mod;

import org.example.markethelper.Model.BL.Mod;

import java.util.ArrayList;

public interface IModDAO {
    public ArrayList<Mod> getMods();
    public Mod getMod(String id);
    public boolean addMod(Mod mod);
    public boolean deleteMod(int id);
    public boolean updateMod(Mod mod);
    public boolean close();

}
