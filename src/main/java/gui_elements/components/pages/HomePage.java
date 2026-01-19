/*
 * Created by JFormDesigner on Tue Jan 06 20:02:58 CET 2026
 */

package gui_elements.components.pages;

import javax.swing.*;

import controllers.OverallStatsController;
import data.MockDataFactory;
import data_structures.Trade;
import gui_elements.components.panels.*;
import models.AccountSelectionModel;
import models.OverallStatsModel;
import models.TradeDataModel;
import net.miginfocom.swing.*;

import java.util.List;

/**
 * @author root
 */
public class HomePage extends JPanel {
//    private OverallStatsModel model;
//    private OverallStatsController controller;

    public HomePage() {
//        model = new OverallStatsModel();
//        controller = new OverallStatsController(model, sharedDataModel, accountSelectionModel);

        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        accountSelectionPanel1 = new AccountSelectionPanel();
        tradingBiasPanel1 = new TradingBiasPanel();
        winratePanel1 = new WinratePanel();
        sessionWinratesCustomPanel1 = new SessionWinratesCustomPanel();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3,align center center",
            // columns
            "[]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));
        add(accountSelectionPanel1, "cell 12 0");
        add(tradingBiasPanel1, "cell 12 1 1 2");
        add(winratePanel1, "cell 12 3");
        add(sessionWinratesCustomPanel1, "cell 12 4,growx");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private AccountSelectionPanel accountSelectionPanel1;
    private TradingBiasPanel tradingBiasPanel1;
    private WinratePanel winratePanel1;
    private SessionWinratesCustomPanel sessionWinratesCustomPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

//    public void setStatsModel(OverallStatsModel statsModel) {
//        this.model = statsModel;
//    }

//    public OverallStatsController getController() {
//        return controller;
//    }

    public void setStatsController(OverallStatsController controller) {
        winratePanel1.setController(controller);
        tradingBiasPanel1.setController(controller);
        sessionWinratesCustomPanel1.setController(controller);
    }
    
    public AccountSelectionPanel getAccountSelectionPanel() { return accountSelectionPanel1; }
}
