package org.example.markethelper.Model;

import java.beans.PropertyChangeListener;

public interface IModel {
    public void addPropertyChangeListener(PropertyChangeListener pcl);
    public void removePropertyChangeListener(PropertyChangeListener pcl);
    public void close();
    public void createUser(String id, String pass);
}
