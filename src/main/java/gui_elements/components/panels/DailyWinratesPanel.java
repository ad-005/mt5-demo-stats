package gui_elements.components.panels;

import constants.Theme;
import controllers.OverallStatsController;
import gui_elements.components.elements.WinrateProgressBar;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DailyWinratesPanel extends JPanel implements PropertyChangeListener {
    private static final List<String> DAY_ORDER = List.of(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );
    private final JPanel rowsPanel;

    public DailyWinratesPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Daily Winrates", TitledBorder.CENTER, TitledBorder.TOP, Theme.Fonts.PANEL_TITLE));
        setOpaque(false);

        rowsPanel = new JPanel(new GridLayout(0, 3, 10, 8));
        rowsPanel.setOpaque(false);
        add(rowsPanel, BorderLayout.CENTER);

        renderRows(Map.of());
    }

    public void setController(OverallStatsController controller) {
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!"dailyWinRates".equals(evt.getPropertyName())) {
            return;
        }
        if (!(evt.getNewValue() instanceof Map<?, ?> rawMap)) {
            return;
        }
        Map<String, Double> dailyWinRates = new LinkedHashMap<>();
        for (String day : DAY_ORDER) {
            Object rawValue = rawMap.get(day);
            if (!(rawValue instanceof Number value)) {
                continue;
            }
            dailyWinRates.put(day, value.doubleValue());
        }
        renderRows(dailyWinRates);
    }

    private void renderRows(Map<String, Double> dailyWinRates) {
        rowsPanel.removeAll();

        int renderedRows = 0;
        for (String day : DAY_ORDER) {
            if (!dailyWinRates.containsKey(day)) {
                continue;
            }
            double pct = dailyWinRates.getOrDefault(day, 0.0);

            JLabel dayLabel = new JLabel(day);
            dayLabel.setFont(Theme.Fonts.LABEL_BOLD);

            WinrateProgressBar bar = new WinrateProgressBar((int) Math.round(pct));

            JLabel valueLabel = new JLabel(String.format("%.2f %%", pct), SwingConstants.RIGHT);
            valueLabel.setFont(Theme.Fonts.LABEL_BOLD);

            rowsPanel.add(dayLabel);
            rowsPanel.add(bar);
            rowsPanel.add(valueLabel);
            renderedRows++;
        }

        setVisible(renderedRows > 0);
        rowsPanel.revalidate();
        rowsPanel.repaint();
        revalidate();
        repaint();
    }
}
