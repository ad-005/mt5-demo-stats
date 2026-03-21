/*
 * Created by JFormDesigner on Tue Jan 06 00:00:16 CET 2026
 */

package gui_elements.tables;

import java.awt.*;

import constants.Theme;
import table_models.AccountsTableModel;

import javax.swing.*;

/**
 * @author root
 */
public class AccountsTable extends JScrollPane {
    private AccountsTableModel accountsTableModel;

    private AccountsTable() {

    }

    public AccountsTable(AccountsTableModel accountsTableModel) {
        initComponents();

        this.accountsTableModel = accountsTableModel;
        accountsTable.setModel(accountsTableModel);

        accountsTable.getTableHeader().setReorderingAllowed(false);
        accountsTable.getTableHeader().setResizingAllowed(false);
        accountsTable.getTableHeader().setFont(Theme.Fonts.TABLE_HEADER);

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        accountsTable = new JTable();

        //======== this ========

        //---- accountsTable ----
        accountsTable.setFont(new Font("IBM Plex Mono", Font.PLAIN, 12));
        accountsTable.setShowVerticalLines(true);
        accountsTable.setShowHorizontalLines(true);
        accountsTable.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        accountsTable.setBorder(null);
        setViewportView(accountsTable);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JTable accountsTable;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public JTable getAccountsTable() {
        return accountsTable;
    }
}
