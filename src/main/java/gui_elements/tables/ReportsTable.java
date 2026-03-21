package gui_elements.tables;

import constants.Theme;
import gui_elements.renderers.ProfitCellRenderer;
import table_models.ReportsTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;

public class ReportsTable extends JScrollPane {
    private JTable reportsTable;

    public ReportsTable() {
        initComponents();
    }

    private void initComponents() {
        reportsTable = new JTable();
        reportsTable.setFont(Theme.Fonts.LABEL_DEFAULT);
        reportsTable.setShowVerticalLines(true);
        reportsTable.setShowHorizontalLines(true);
        setViewportView(reportsTable);
    }

    public JTable getTable() {
        return reportsTable;
    }

    public void setTableModel(ReportsTableModel model) {
        reportsTable.setModel(model);
        reportsTable.setAutoCreateRowSorter(true);
        reportsTable.getColumnModel().getColumn(2).setCellRenderer(new ProfitCellRenderer());
        reportsTable.getTableHeader().setReorderingAllowed(false);
        reportsTable.getTableHeader().setResizingAllowed(true);
        reportsTable.getTableHeader().setFont(Theme.Fonts.TABLE_HEADER);
    }
}
