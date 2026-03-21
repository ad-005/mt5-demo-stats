/*
 * Created by JFormDesigner on Tue Jan 06 14:40:22 CET 2026
 */

package gui_elements.tables;

import java.awt.*;
import constants.Theme;
import gui_elements.renderers.ProfitCellRenderer;
import table_models.TradeTableModel;

import javax.swing.*;

/**
 * @author root
 */
public class TradeTable extends JScrollPane {
    public TradeTable() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        tradeTable = new JTable();

        //======== this ========

        //---- tradeTable ----
        tradeTable.setFont(new Font("IBM Plex Mono", Font.PLAIN, 12));
        tradeTable.setShowVerticalLines(true);
        tradeTable.setShowHorizontalLines(true);
        setViewportView(tradeTable);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JTable tradeTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public JTable getTable() {
        return tradeTable;
    }

    public void setTableModel(TradeTableModel model) {
        tradeTable.setModel(model);
        tradeTable.setAutoCreateRowSorter(true);
        tradeTable.getColumnModel().getColumn(10).setCellRenderer(new ProfitCellRenderer());

        tradeTable.getTableHeader().setReorderingAllowed(false);
        tradeTable.getTableHeader().setResizingAllowed(true);
        tradeTable.getTableHeader().setFont(Theme.Fonts.TABLE_HEADER);
    }
}
