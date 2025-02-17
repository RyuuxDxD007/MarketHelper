package org.example.markethelper.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PrimaryModel implements IModel{
    private PropertyChangeSupport support;
    private DBConnection dbConn;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {

    }

    @Override
    public void close() {

    }
    public void createUser(String id, String pass) {

    }
}
