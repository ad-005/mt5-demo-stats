/*
 * Created by JFormDesigner on Mon Jan 05 20:15:11 CET 2026
 */

package gui_elements.components;


import controllers.OverallStatsController;
import data.TradeFetcher;
import data_structures.Account;
import gui_elements.components.pages.*;
import gui_elements.components.pages.SearchPage;
import gui_elements.components.panels.AccountSidebarPanel;
import gui_elements.components.panels.SelectionPanel;
import constants.ViewType;
import models.AccountSelectionModel;
import models.OverallStatsModel;
import models.ReportDataModel;
import models.TradeDataModel;
import services.AccountFetchingService;
import services.AccountManagementService;
import services.ReportFetchingService;
import services.ReportManagementService;

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
    private ReportManagementService reportManagementService;
    private OverallStatsController overallStatsController;
    private ReportDataModel reportDataModel;
    private ReportsPage reportsPage;
    private TradeFetcher tradeFetcher = new TradeFetcher();
    private AccountSidebarPanel accountSidebarPanel;
    private boolean isAccountSidebarVisible;

    public MainViewPanel() {
        this.accountFetchingService = new AccountFetchingService();

        initModels();
        initComponents();
        reportsPage = new ReportsPage(reportDataModel, reportManagementService);
        contentPanel.add(reportsPage, ViewType.REPORTS.name());

        initControllers();
        bindAccountSelection();
        initSidebar();

        cardLayout = (CardLayout) contentPanel.getLayout();
        selectionPanel1.setNavigationListener(this::switchView);
        selectionPanel1.setAccountSidebarToggleListener(this::toggleAccountSidebar);
        switchView(ViewType.HOME);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        contentPanel = new JPanel();
        homePage1 = new HomePage();
        searchPage1 = new SearchPage(sharedTradeDataModel, sharedAccountSelectionModel);
        selectionPanel1 = new SelectionPanel();

        //======== this ========
        setLayout(new BorderLayout(0, 15));

        //======== contentPanel ========
        {
            contentPanel.setLayout(new CardLayout());
            contentPanel.add(homePage1, "HOME");
            contentPanel.add(searchPage1, "SEARCH");
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
        reportDataModel = new ReportDataModel();
        accountManagementService = new AccountManagementService(
                accountFetchingService,
                sharedAccountSelectionModel
        );
        reportManagementService = new ReportManagementService(new ReportFetchingService());

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
        homePage1.setSaveAsReportListener(e -> saveCurrentStatsAsReport());
        overallStatsController.refreshStatistics();
    }

    private void bindAccountSelection() {
        homePage1.getAccountSelectionPanel().setModel(sharedAccountSelectionModel);
        searchPage1.getSearchFieldPanel().getAccountSelectionPanel().setModel(sharedAccountSelectionModel);
    }

    private void initSidebar() {
        accountSidebarPanel = new AccountSidebarPanel(sharedAccountSelectionModel, accountManagementService);
        accountSidebarPanel.setVisible(false);
        isAccountSidebarVisible = false;
        add(accountSidebarPanel, BorderLayout.WEST);
    }

    private void toggleAccountSidebar() {
        isAccountSidebarVisible = !isAccountSidebarVisible;
        accountSidebarPanel.setVisible(isAccountSidebarVisible);
        revalidate();
        repaint();
    }

    private void saveCurrentStatsAsReport() {
        if (overallStatsController.getCurrentStatistics() == null) {
            JOptionPane.showMessageDialog(this, "No statistics available to save yet.", "Save Report", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String accountLogin = null;
        String accountName = null;
        Account selected = sharedAccountSelectionModel.getSelectedAccount();
        if (selected != null) {
            accountLogin = selected.getLogin();
            accountName = selected.getName();
        }

        reportManagementService.createReport(accountLogin, accountName, overallStatsController.getCurrentStatistics());
        reportsPage.refreshReports();
        JOptionPane.showMessageDialog(this, "Report saved successfully.", "Save Report", JOptionPane.INFORMATION_MESSAGE);
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
