package org.example.markethelper.Model.DAL.Riven;

import org.example.markethelper.Model.BL.Riven;

import java.util.ArrayList;

public interface IRivenDAO {
    public ArrayList<Riven> getRivens();
    public Riven getRiven(int id);
    public boolean addRiven(Riven riven);
    public boolean updateRiven(Riven riven);
    public boolean deleteRiven(int id);
    public boolean close();
}
