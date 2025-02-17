package org.example.markethelper.Model.DAL.User;

import org.example.markethelper.Model.BL.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private Connection con;
    PreparedStatement insertUser;
    PreparedStatement getAllUsers;
    PreparedStatement getUser;
    PreparedStatement deleteUser;


    public UserDAO(Connection con) {
        try {
            this.con = con;
            Statement statement = con.createStatement();
            try {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS AppUser (id VARCHAR(50) PRIMARY KEY, hash VARCHAR(128))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            statement.close();
            this.insertUser = this.con.prepareStatement("INSERT INTO AppUser (id, hash) VALUES (?, ?)");
            this.deleteUser = this.con.prepareStatement("DELETE FROM AppUser WHERE id=?");
            this.getAllUsers = this.con.prepareStatement("SELECT id,nom FROM AppUser");
            this.getUser = this.con.prepareStatement("SELECT * FROM AppUser WHERE id = ?");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addUser(String id, String pass) {
        User user = new User(id, null);
        user.computeHash(pass);
        try{
            this.insertUser.setString(1,user.getId());
            this.insertUser.setString(2,user.getHash());
            this.insertUser.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(String id) {
        try {
            this.getUser.setString(1,id);
            ResultSet set = this.getUser.executeQuery();
            if (set.next()) {
                return new User(set.getString("id"), set.getString("hash"));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try{
            ResultSet set = this.getAllUsers.executeQuery();
            while (set.next()) {
                users.add(new User(set.getString("id"), set.getString("hash")));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public void deleteUser(String id) {
        try{
            this.deleteUser.setString(1,id);
            this.deleteUser.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean verifyUser(String id, String pass) {
        User user = getUser(id);
        if (user == null) {
            return false;
        }
        else {
            return user.hashValid(pass);
        }
    }
    @Override
    public boolean close() {
        boolean safe = true;

        if (this.insertUser != null) {
            try {
                this.insertUser.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getUser != null) {
            try {
                this.getUser.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.getAllUsers != null) {
            try {
                this.getAllUsers.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                safe = false;
            }
        }

        if (this.deleteUser != null) {
            try {
                this.deleteUser.close();
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
