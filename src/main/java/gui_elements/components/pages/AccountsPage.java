/*
 * Created by JFormDesigner on Mon Jan 05 23:16:06 CET 2026
 */

package gui_elements.components.pages;

import java.awt.*;
import javax.swing.*;

import data_structures.Account;
import gui_elements.components.panels.LoginPanel;
import gui_elements.tables.*;
import models.AccountDataModel;
import services.AccountFetchingService;
import services.AccountManagementService;
import table_controllers.AccountsTableController;
import table_models.AccountsTableModel;

/**
 * @author root
 */
public class AccountsPage extends JPanel {
    private AccountsTableController accountsTableController;
    private AccountDataModel accountDataModel;
    private AccountsTableModel accountsTableModel;
    private AccountManagementService accountManagementService;
    private AccountFetchingService accountFetchingService;

    public AccountsPage() {
        this.accountsTableModel = new AccountsTableModel();
        initComponents();
        initializeModels();
        initializeControllers();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        accountsTable1 = new AccountsTable(accountsTableModel);
        loginPanel1 = new LoginPanel();

        //======== this ========
        setLayout(new GridLayout(1, 2));
        add(accountsTable1);
        add(loginPanel1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private AccountsTable accountsTable1;
    private LoginPanel loginPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void initializeModels() {
        this.accountDataModel = new AccountDataModel();
        this.accountFetchingService = new AccountFetchingService();
        this.accountManagementService = new AccountManagementService(accountFetchingService);
    }

    private void initializeControllers() {
        accountsTableController = new AccountsTableController(
                accountDataModel,
                accountsTableModel,
                loginPanel1,
                accountManagementService
        );
    }
}
