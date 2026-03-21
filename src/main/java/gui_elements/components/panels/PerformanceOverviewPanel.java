package gui_elements.components.panels;

import constants.Theme;
import controllers.OverallStatsController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PerformanceOverviewPanel extends JPanel implements PropertyChangeListener {
    private final DecimalFormat decimal = Theme.Formatting.PROFIT;
    private final Map<String, JLabel> valueLabels = new HashMap<>();
    private int consecutiveWins;
    private int consecutiveLosses;
    private OverallStatsController controller;

    public PerformanceOverviewPanel() {
        setLayout(new GridLayout(0, 4, 10, 10));
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Performance Overview", TitledBorder.CENTER, TitledBorder.TOP, Theme.Fonts.PANEL_TITLE));
        setOpaque(false);
        addMetric("Net Profit", "netProfit");
        addMetric("Total Profit", "totalProfit");
        addMetric("Total Loss", "totalLoss");
        addMetric("Average Trade", "averageTrade");
        addMetric("Average Win", "averageProfit");
        addMetric("Average Loss", "averageLoss");
        addMetric("Largest Win", "largestWin");
        addMetric("Largest Loss", "largestLoss");
        addMetric("Profit Factor", "profitFactor");
        addMetric("Expectancy", "expectancy");
        addMetric("Risk/Reward", "riskRewardRatio");
        addMetric("Max Drawdown", "maxDrawdown");
        addMetric("Max DD %", "maxDrawdownPct");
        addMetric("Sharpe", "sharpeRatio");
        addMetric("Sortino", "sortinoRatio");
        addMetric("Consec Wins / Losses", "streakComposite");
    }

    public void setController(OverallStatsController controller) {
        this.controller = controller;
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String key = evt.getPropertyName();
        if ("consecutiveWins".equals(key)) {
            Object value = evt.getNewValue();
            if (value instanceof Number n) {
                consecutiveWins = n.intValue();
            }
            updateStreakValue();
            return;
        }
        if ("consecutiveLosses".equals(key)) {
            Object value = evt.getNewValue();
            if (value instanceof Number n) {
                consecutiveLosses = n.intValue();
            }
            updateStreakValue();
            return;
        }
        JLabel label = valueLabels.get(key);
        if (label == null) {
            return;
        }
        Object value = evt.getNewValue();
        if (value instanceof Number number) {
            label.setText(decimal.format(number.doubleValue()));
        }
    }

    private void addMetric(String title, String propertyKey) {
        JPanel metric = new JPanel(new BorderLayout());
        metric.setOpaque(false);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.Fonts.LABEL_BOLD);
        JLabel valueLabel = new JLabel("0.00");
        valueLabel.setFont(Theme.Fonts.LABEL_LARGE);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        metric.add(titleLabel, BorderLayout.WEST);
        metric.add(valueLabel, BorderLayout.EAST);
        add(metric);
        valueLabels.put(propertyKey, valueLabel);
    }

    private void updateStreakValue() {
        valueLabels.get("streakComposite").setText(consecutiveWins + " / " + consecutiveLosses);
    }
}
