package models;

import data_structures.Account;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.List;

public class AccountSelectionModel {
    public final String ACCOUNT_SELECTED_PROPERTY = "accountSelected";
    private List<Account> accounts;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Constructor
    public AccountSelectionModel() {

    }

    // Add property change listener
    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }

    // Remove property change listener
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

}
