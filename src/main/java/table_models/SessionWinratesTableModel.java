package table_models;

import data.MockDataFactory;
import data_structures.Session;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class SessionWinratesTableModel extends AbstractTableModel {

    private final String[] columns = {"Session", "Winrate"};
    private List<Session> sessions = MockDataFactory.generateSessionData();

    @Override
    public int getRowCount() {
        return sessions.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Session session = sessions.get(rowIndex);

        switch (columnIndex) {
            case 0: return session.getSessionName();
            case 1: return session.getWinrate();
            default: return null;
        }
    }

    @Override
    public final String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
}
