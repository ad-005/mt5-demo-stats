package table_models;

import data_structures.ReportSnapshot;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ReportsTableModel extends AbstractTableModel {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum ReportColumn {
        CREATED_AT("Created At", String.class, report -> report.getCreatedAt() != null
                ? report.getCreatedAt().format(DATE_TIME_FORMATTER)
                : ""),
        ACCOUNT("Account", String.class, ReportSnapshot::getAccountLabel),
        NET_PROFIT("Net Profit", Double.class, report -> report.toTradeStatistics() != null
                ? report.toTradeStatistics().netProfit()
                : 0.0),
        WINRATE("Winrate %", Double.class, report -> report.toTradeStatistics() != null
                ? report.toTradeStatistics().winrate()
                : 0.0),
        TOTAL_TRADES("Trades", Integer.class, report -> report.toTradeStatistics() != null
                ? report.toTradeStatistics().totalTradesTaken()
                : 0);

        private final String name;
        private final Class<?> type;
        private final Function<ReportSnapshot, Object> extractor;

        ReportColumn(String name, Class<?> type, Function<ReportSnapshot, Object> extractor) {
            this.name = name;
            this.type = type;
            this.extractor = extractor;
        }

        public Object getValue(ReportSnapshot report) {
            return extractor.apply(report);
        }
    }

    private final ReportColumn[] columns = ReportColumn.values();
    private List<ReportSnapshot> reports = new ArrayList<>();

    @Override
    public int getRowCount() {
        return reports.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column].name;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns[columnIndex].type;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return columns[columnIndex].getValue(reports.get(rowIndex));
    }

    public void setReports(List<ReportSnapshot> reports) {
        this.reports = new ArrayList<>(reports);
        fireTableDataChanged();
    }
}
