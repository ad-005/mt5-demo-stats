package models;

import data_structures.Account;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.List;

public class AccountSelectionModel {
    public final Account ALL_ACCOUNTS = null;
    private List<Account> accounts = new ArrayList<>();
    private Account selectedAccount = ALL_ACCOUNTS;

    public static final String ACCOUNT_SELECTED_PROPERTY = "accountSelected";
    public static final String AVAILABLE_ACCOUNTS_PROPERTY = "availableAccounts";
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    // Constructor
    public AccountSelectionModel() {

    }

    // START GETTERS
    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public List<Account> getAccounts() {
        return accounts != null ? List.copyOf(accounts) : List.of();
    }

    public boolean isAllAccountsSelected() {
        return selectedAccount == ALL_ACCOUNTS;
    }
    // END GETTERS

    // START SETTERS
    public void setSelectedAccount(Account account) {
        Account oldValue = this.selectedAccount;
        this.selectedAccount = account;
        pcs.firePropertyChange(ACCOUNT_SELECTED_PROPERTY, oldValue, account);
    }

    public void setAccounts(List<Account> accounts) {
        List<Account> oldValue = new ArrayList<>(this.accounts);
        this.accounts.clear();
        this.accounts.addAll(accounts);
        pcs.firePropertyChange(AVAILABLE_ACCOUNTS_PROPERTY, oldValue, accounts);
    }
    // END SETTERS

    // Add property change listener
    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void addPropertyChangeListener(String property, PropertyChangeListener l) { pcs.addPropertyChangeListener(property, l); }


    // Remove property change listener
    public void removePropertyChangeListener(String property, PropertyChangeListener l) { pcs.removePropertyChangeListener(property, l); }

}
