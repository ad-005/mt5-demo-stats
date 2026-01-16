package table_controllers;

import data_structures.Account;
import gui_elements.components.panels.LoginPanel;
import gui_elements.tables.AccountsTable;
import models.AccountDataModel;
import services.AccountFetchingService;
import services.AccountManagementService;
import table_models.AccountsTableModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class AccountsTableController implements PropertyChangeListener {
    private final AccountDataModel model;
    private final AccountsTableModel tableModel;
    private final LoginPanel loginPanel;
    private final AccountManagementService accountManagementService;

    public AccountsTableController(AccountDataModel model, AccountsTableModel tableModel, LoginPanel loginPanel, AccountManagementService accountManagementService) {
        this.model = model;
        this.tableModel = tableModel;
        this.loginPanel = loginPanel;
        this.accountManagementService = accountManagementService;
        initializeElementListeners();
        model.addPropertyChangeListener("accounts", this);
        initializeData();
    }

    private void initializeData() {
        List<Account> accounts = accountManagementService.getAccounts();
        model.setAccounts(accounts);
    }

    // Handle account addition
    private void handleAddAccount() {
        try {
            Account newAccount = loginPanel.createAccountFromFields();
            accountManagementService.addAccount(newAccount);
            model.setAccounts(accountManagementService.getAccounts());
            loginPanel.clearInputs();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(loginPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add "add account" button listener
    private void initializeElementListeners() {
        loginPanel.getAddAccountButton().addActionListener(e -> handleAddAccount());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        tableModel.setAccounts((List<Account>) event.getNewValue());
    }
}
