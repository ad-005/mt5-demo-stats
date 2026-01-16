package models;

import data_structures.Account;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountDataModel {
    private final PropertyChangeSupport pcs;
    private List<Account> accounts;

    public static final String ACCOUNTS_PROPERTY = "accounts";

    public AccountDataModel() {
        this.pcs = new PropertyChangeSupport(this);
        this.accounts = new ArrayList<>();
    }

    public void setAccounts(List<Account> accounts) {
        if (accounts == null) {
            throw new IllegalArgumentException("List of accounts cannot be null.");
        }

        final List<Account> oldAccounts = this.accounts;
        this.accounts = new ArrayList<>(accounts);

        if (SwingUtilities.isEventDispatchThread()) {
            pcs.firePropertyChange(ACCOUNTS_PROPERTY, oldAccounts, this.accounts);
        } else {
            SwingUtilities.invokeLater(() ->
                    pcs.firePropertyChange(ACCOUNTS_PROPERTY, oldAccounts, this.accounts)
            );
        }
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

}
