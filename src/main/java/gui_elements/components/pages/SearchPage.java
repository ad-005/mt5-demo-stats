/*
 * Created by JFormDesigner on Tue Jan 06 14:41:36 CET 2026
 */

package gui_elements.components.pages;

import java.awt.*;
import javax.swing.*;

import data.MockDataFactory;
import data_structures.Trade;
import gui_elements.components.panels.SearchFieldPanel;
import gui_elements.tables.*;
import models.AccountSelectionModel;
import models.TradeDataModel;
import table_controllers.TradeTableController;
import table_models.TradeTableModel;

import java.util.List;
import java.util.ArrayList;

/**
 * @author root
 */
public class SearchPage extends JPanel {
    private TradeDataModel tradeDataModel;
    private TradeTableModel tradeTableModel;
    private TradeTableController tradeTableController;
    private AccountSelectionModel accountSelectionModel;

    private SearchPage() {

    }

    public SearchPage(TradeDataModel tradeDataModel) {
        this.tradeDataModel = tradeDataModel;
        initComponents();
        initializeModels();
        initializeControllers();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        tradeTable1 = new TradeTable();
        searchFieldPanel1 = new SearchFieldPanel();

        //======== this ========
        setLayout(new GridLayout(1, 2, 10, 0));
        add(tradeTable1);
        add(searchFieldPanel1);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private TradeTable tradeTable1;
    private SearchFieldPanel searchFieldPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // START GETTERS
    public SearchFieldPanel getSearchFieldPanel() { return searchFieldPanel1; }

    // END GETTERS

    private void initializeModels() {
        tradeTableModel = new TradeTableModel();
        accountSelectionModel = new AccountSelectionModel();
        tradeTable1.setTableModel(tradeTableModel);

        List<Trade> initialTrades = MockDataFactory.generateTrades(200);
        tradeDataModel.setTrades(initialTrades);
    }

    private void initializeControllers() {
        tradeTableController = new TradeTableController(
                tradeTable1.getTable(),
                tradeTableModel,
                tradeDataModel,
                searchFieldPanel1,
                accountSelectionModel
        );

    }

    private void setDataModel(TradeDataModel dataModel) {
        this.tradeDataModel = dataModel;
    }
}
