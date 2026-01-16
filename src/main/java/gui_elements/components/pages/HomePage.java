/*
 * Created by JFormDesigner on Tue Jan 06 20:02:58 CET 2026
 */

package gui_elements.components.pages;

import javax.swing.*;

import controllers.OverallStatsController;
import data.MockDataFactory;
import data_structures.Trade;
import gui_elements.components.panels.*;
import models.OverallStatsModel;
import models.TradeDataModel;
import net.miginfocom.swing.*;

import java.util.List;

/**
 * @author root
 */
public class HomePage extends JPanel {
    private OverallStatsModel model;
    private OverallStatsController controller;

    private HomePage() {

    }

    public HomePage(TradeDataModel sharedDataModel) {
        model = new OverallStatsModel();
        controller = new OverallStatsController(model, sharedDataModel);

        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        tradingBiasPanel1 = new TradingBiasPanel(controller);
        winratePanel1 = new WinratePanel(controller);
        sessionWinratesCustomPanel1 = new SessionWinratesCustomPanel(controller);

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
            "[]"));
        add(tradingBiasPanel1, "cell 12 0 1 2");
        add(winratePanel1, "cell 12 2");
        add(sessionWinratesCustomPanel1, "cell 12 3,growx");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private TradingBiasPanel tradingBiasPanel1;
    private WinratePanel winratePanel1;
    private SessionWinratesCustomPanel sessionWinratesCustomPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public OverallStatsController getController() {
        return controller;
    }
}
