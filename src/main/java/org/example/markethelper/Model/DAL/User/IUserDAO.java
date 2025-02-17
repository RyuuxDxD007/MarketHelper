package org.example.markethelper.Model.DAL.User;

import org.example.markethelper.Model.BL.User;

import java.util.List;

public interface IUserDAO {
    public boolean addUser(String id, String pass);
    public User getUser(String id);
    public List<User> getAllUsers();
    public void deleteUser(String id);
    public boolean verifyUser(String id, String pass);
    public boolean close();
}
