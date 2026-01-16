/*
 * Created by JFormDesigner on Mon Jan 05 20:15:11 CET 2026
 */

package gui_elements.components;


import gui_elements.components.pages.*;
import gui_elements.components.pages.AccountsPage;
import gui_elements.components.pages.SearchPage;
import gui_elements.components.panels.SelectionPanel;
import constants.ViewType;
import models.TradeDataModel;

import java.awt.*;
import javax.swing.*;

/**
 * @author root
 */
public class MainViewPanel extends JPanel {
    private CardLayout cardLayout;
    private TradeDataModel sharedModel;

    public MainViewPanel() {
        initModel();
        initComponents();

        cardLayout = (CardLayout) contentPanel.getLayout();
        selectionPanel1.setNavigationListener(this::switchView);
        switchView(ViewType.HOME);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        contentPanel = new JPanel();
        homePage1 = new HomePage(sharedModel);
        searchPage1 = new SearchPage(sharedModel);
        accountsPage1 = new AccountsPage();
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
    private void initModel() {
        sharedModel = new TradeDataModel();
    }
}
