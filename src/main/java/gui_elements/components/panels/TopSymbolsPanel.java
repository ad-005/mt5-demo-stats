package gui_elements.components.panels;

import constants.Theme;
import controllers.OverallStatsController;
import data_structures.SymbolStatistics;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TopSymbolsPanel extends JPanel implements PropertyChangeListener {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JScrollPane tableScrollPane;

    public TopSymbolsPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Top 5 Symbols", TitledBorder.CENTER, TitledBorder.TOP, Theme.Fonts.PANEL_TITLE));
        setOpaque(false);

        tableModel = new DefaultTableModel(new Object[]{"Symbol", "Trades", "Winrate %", "PnL"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getTableHeader().setFont(Theme.Fonts.TABLE_HEADER);
        table.setFont(Theme.Fonts.TABLE_DATA_LARGE);
        table.setRowHeight(28);
        table.setFillsViewportHeight(false);

        tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        updateTableSizing();
    }

    public void setController(OverallStatsController controller) {
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"symbolBreakdown".equals(evt.getPropertyName())) {
            return;
        }
        if (!(evt.getNewValue() instanceof Map<?, ?> rawMap)) {
            return;
        }

        List<Map.Entry<String, SymbolStatistics>> rows = new ArrayList<>();
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (entry.getKey() instanceof String key && entry.getValue() instanceof SymbolStatistics value) {
                rows.add(Map.entry(key, value));
            }
        }
        rows.sort(Comparator.<Map.Entry<String, SymbolStatistics>>comparingInt(e -> e.getValue().trades()).reversed()
                .thenComparingDouble(e -> -e.getValue().pnl()));

        tableModel.setRowCount(0);
        int limit = Math.min(5, rows.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, SymbolStatistics> row = rows.get(i);
            tableModel.addRow(new Object[]{
                    row.getKey(),
                    row.getValue().trades(),
                    String.format("%.2f", row.getValue().winRate()),
                    String.format("%.2f", row.getValue().pnl())
            });
        }

        updateTableSizing();
    }

    private void updateTableSizing() {
        int rowCount = tableModel.getRowCount();
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int bodyHeight = rowCount * table.getRowHeight();
        int tableHeight = headerHeight + bodyHeight;

        Dimension currentScrollPref = tableScrollPane.getPreferredSize();
        table.setPreferredScrollableViewportSize(new Dimension(currentScrollPref.width, tableHeight));

        Insets scrollInsets = tableScrollPane.getInsets();
        int scrollHeight = tableHeight + scrollInsets.top + scrollInsets.bottom;
        tableScrollPane.setPreferredSize(new Dimension(currentScrollPref.width, scrollHeight));
        tableScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, scrollHeight));

        Dimension panelPref = getPreferredSize();
        setMaximumSize(new Dimension(Integer.MAX_VALUE, panelPref.height));
        revalidate();
        repaint();
    }
}
