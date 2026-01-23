/*
 * Created by JFormDesigner on Mon Jan 05 20:15:11 CET 2026
 */

package gui_elements.components;


import controllers.OverallStatsController;
import data.TradeFetcher;
import data_structures.Account;
import gui_elements.components.pages.*;
import gui_elements.components.pages.AccountsPage;
import gui_elements.components.pages.SearchPage;
import gui_elements.components.panels.SelectionPanel;
import constants.ViewType;
import models.AccountSelectionModel;
import models.OverallStatsModel;
import models.TradeDataModel;
import services.AccountFetchingService;
import services.AccountManagementService;

import java.util.List;

import java.awt.*;
import javax.swing.*;

/**
 * @author root
 */
public class MainViewPanel extends JPanel {
    private CardLayout cardLayout;
    private TradeDataModel sharedTradeDataModel;
    private AccountSelectionModel sharedAccountSelectionModel;
    private AccountManagementService accountManagementService;
    private AccountFetchingService accountFetchingService;
    private OverallStatsController overallStatsController;
    private TradeFetcher tradeFetcher = new TradeFetcher();

    public MainViewPanel() {
        this.accountFetchingService = new AccountFetchingService();

        initModels();
        initComponents();

        accountsPage1.setAccountManagementService(accountManagementService);

        initControllers();
        bindAccountSelection();

        cardLayout = (CardLayout) contentPanel.getLayout();
        selectionPanel1.setNavigationListener(this::switchView);
        switchView(ViewType.HOME);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        contentPanel = new JPanel();
        homePage1 = new HomePage();
        searchPage1 = new SearchPage(sharedTradeDataModel, sharedAccountSelectionModel);
        accountsPage1 = new AccountsPage(accountManagementService);
        selectionPanel1 = new SelectionPanel();

        //======== this ========
        setLayout(new BorderLayout(0, 15));

        //======== contentPanel ========
        {
            contentPanel.setLayout(new CardLayout());
            contentPanel.add(homePage1, "HOME");
            contentPanel.add(searchPage1, "SEARCH");
            contentPanel.add(accountsPage1, "ACCOUNTS");
        }
        add(contentPanel, BorderLayout.CENTER);
        add(selectionPanel1, BorderLayout.NORTH);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JPanel contentPanel;
    private HomePage homePage1;
    private SearchPage searchPage1;
    private AccountsPage accountsPage1;
    private SelectionPanel selectionPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // Switch view method for the cardLayout
    private void switchView(ViewType viewType) {
        cardLayout.show(contentPanel, viewType.name());
    }

    // Method for single source of truth model initialization
    private void initModels() {
        tradeFetcher.fetchTrades();

        sharedTradeDataModel = new TradeDataModel();
        sharedAccountSelectionModel = new AccountSelectionModel();
        accountManagementService = new AccountManagementService(
                accountFetchingService,
                sharedAccountSelectionModel
        );

        List<Account> accounts = accountManagementService.getAccounts();
        sharedAccountSelectionModel.setAccounts(accounts);
    }

    private void initControllers() {
        OverallStatsModel statsModel = new OverallStatsModel();
        overallStatsController = new OverallStatsController(
                statsModel,
                sharedTradeDataModel,
                sharedAccountSelectionModel
        );

        homePage1.setStatsController(overallStatsController);
    }

    private void bindAccountSelection() {
        homePage1.getAccountSelectionPanel().setModel(sharedAccountSelectionModel);
        searchPage1.getSearchFieldPanel().getAccountSelectionPanel().setModel(sharedAccountSelectionModel);
    }

    // Method for setting up the account selection model with the panels that use it
//    private void initAccountSelectionModel() {
//        sharedAccountSelectionModel = new AccountSelectionModel();
//
//        homePage1.getAccountSelectionPanel().setModel(sharedAccountSelectionModel);
//        sharedAccountSelectionModel.addPropertyChangeListener(event -> {
//            if (event.getPropertyName().equals(sharedAccountSelectionModel.ACCOUNT_SELECTED_PROPERTY)) {
//
//            }
//        });
//    }
}
