package table_controllers;

import data_structures.Account;
import gui_elements.components.panels.AccountSelectionPanel;
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
    private AccountSelectionPanel accountSelectionPanel;

    private JComboBox accountComboBox;

    public AccountsTableController(AccountDataModel model, AccountsTableModel tableModel, LoginPanel loginPanel, AccountManagementService accountManagementService) {
        this.model = model;
        this.tableModel = tableModel;
        this.loginPanel = loginPanel;
        this.accountManagementService = accountManagementService;
        this.accountSelectionPanel = new AccountSelectionPanel();
        this.accountComboBox = accountSelectionPanel.getAccountComboBox();
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

    // Handle account removal
    private void handleRemoveAccount() {
        accountComboBox.removeAllItems();

        for (Account account : model.getAccounts()) {
            accountComboBox.addItem(account);
        }

        int loginSelectionPane = JOptionPane.showConfirmDialog(
                loginPanel,
                accountComboBox,
                "Select account login for removal",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
//        String loginInputPane = JOptionPane.showInputDialog(loginPanel, "Input account login for removal");
//        System.out.println(account.toString());

//        if (loginInputPane == null) {
//            return;
//        } else if (loginInputPane.isEmpty()) {
//            JOptionPane.showMessageDialog(loginPanel,
//                    "Account with login \"" + loginInputPane + "\" not found.",
//                    "Account not found",
//                    JOptionPane.ERROR_MESSAGE);
//        }

        try {
            if (loginSelectionPane == JOptionPane.OK_OPTION) {
                String selectedLogin = ((Account) accountComboBox.getSelectedItem()).getLogin();

                accountManagementService.removeAccount(selectedLogin);
                model.setAccounts(accountManagementService.getAccounts());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(loginPanel, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add "add account" button listener
    private void initializeElementListeners() {
        loginPanel.getAddAccountButton().addActionListener(e -> handleAddAccount());
        loginPanel.getRemoveAccountButton().addActionListener(e -> handleRemoveAccount());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        tableModel.setAccounts((List<Account>) event.getNewValue());
    }
}
