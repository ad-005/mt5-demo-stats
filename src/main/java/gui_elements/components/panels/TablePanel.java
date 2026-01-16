package gui_elements.components.panels;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.BorderLayout;

public class TablePanel extends JPanel {

    public TablePanel() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane1 = new JScrollPane();
        JTable table1 = new JTable(new DataTable());

        scrollPane1.add(table1);

        add(scrollPane1, BorderLayout.CENTER);
    }

    class DataTable extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return 20;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }
    }

}
