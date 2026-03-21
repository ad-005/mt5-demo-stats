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
    private final Map<String, WinrateProgressBar> bars = new LinkedHashMap<>();
    private final Map<String, JLabel> labels = new LinkedHashMap<>();

    public DailyWinratesPanel() {
        setLayout(new GridLayout(0, 3, 10, 8));
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Daily Winrates", TitledBorder.CENTER, TitledBorder.TOP, Theme.Fonts.PANEL_TITLE));
        setOpaque(false);

        List<String> days = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        for (String day : days) {
            JLabel dayLabel = new JLabel(day);
            dayLabel.setFont(Theme.Fonts.LABEL_BOLD);
            WinrateProgressBar bar = new WinrateProgressBar(0);
            JLabel valueLabel = new JLabel("0.00 %", SwingConstants.RIGHT);
            valueLabel.setFont(Theme.Fonts.LABEL_BOLD);
            add(dayLabel);
            add(bar);
            add(valueLabel);
            bars.put(day, bar);
            labels.put(day, valueLabel);
        }
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
        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            if (!(entry.getKey() instanceof String day) || !(entry.getValue() instanceof Number value)) {
                continue;
            }
            WinrateProgressBar bar = bars.get(day);
            JLabel label = labels.get(day);
            if (bar == null || label == null) {
                continue;
            }
            double pct = value.doubleValue();
            bar.setValue((int) Math.round(pct));
            label.setText(String.format("%.2f %%", pct));
        }
    }
}
