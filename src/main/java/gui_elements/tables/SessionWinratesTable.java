/*
 * Created by JFormDesigner on Wed Jan 07 21:28:19 CET 2026
 */

package gui_elements.tables;

import constants.UIConstants;
import gui_elements.renderers.ProgressCellRenderer;
import table_models.SessionWinratesTableModel;

import java.awt.*;
import javax.swing.*;

/**
 * @author root
 */
public class SessionWinratesTable extends JScrollPane {
    public SessionWinratesTable() {
        initComponents();

        sessionWinratesTable.setModel(new SessionWinratesTableModel());
        sessionWinratesTable.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());

        sessionWinratesTable.getTableHeader().setReorderingAllowed(false);
        sessionWinratesTable.getTableHeader().setResizingAllowed(false);
        sessionWinratesTable.getTableHeader().setFont(UIConstants.TABLE_HEADER_FONT);
        sessionWinratesTable.getTableHeader().setOpaque(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        sessionWinratesTable = new JTable();

        //======== this ========

        //---- sessionWinratesTable ----
        sessionWinratesTable.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        sessionWinratesTable.setRowSelectionAllowed(false);
        sessionWinratesTable.setRowHeight(30);
        setViewportView(sessionWinratesTable);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JTable sessionWinratesTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
