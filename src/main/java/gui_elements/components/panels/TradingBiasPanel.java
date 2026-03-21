/*
 * Created by JFormDesigner on Tue Jan 06 20:15:36 CET 2026
 */

package gui_elements.components.panels;

import constants.Theme;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.*;

import controllers.OverallStatsController;
import interfaces.ModelObserver;
import net.miginfocom.swing.*;

/**
 * @author root
 */
public class TradingBiasPanel extends JPanel implements PropertyChangeListener {
    private OverallStatsController controller;

    public TradingBiasPanel() {
        initComponents();
    }

    public TradingBiasPanel(OverallStatsController controller) {
        this();
        setController(controller);
    }

    public void setController(OverallStatsController controller) {
        this.controller = controller;
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        bearishLabel = new JLabel();
        neutralLabel = new JLabel();
        bullishLabel = new JLabel();
        progressBar1 = new JProgressBar();
        bullishTradesNumberLabel = new JLabel();
        bullishTradesPctLabel = new JLabel();
        bearishTradesNumberLabel = new JLabel();
        bearishTradesPctLabel = new JLabel();

        //======== this ========
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Trading Bias", TitledBorder.CENTER, TitledBorder.TOP,
            Theme.Fonts.PANEL_TITLE));
        setLayout(new MigLayout(
            "insets null null 15 null,hidemode 3,alignx center",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- bearishLabel ----
        bearishLabel.setText("Bullish");
        bearishLabel.setFont(Theme.Fonts.LABEL_XLARGE);
        bearishLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(bearishLabel, "cell 1 2,alignx center,growx 0");

        //---- neutralLabel ----
        neutralLabel.setText("Neutral");
        neutralLabel.setFont(Theme.Fonts.LABEL_XLARGE);
        add(neutralLabel, "cell 12 2,alignx center,growx 0");

        //---- bullishLabel ----
        bullishLabel.setText("Bearish");
        bullishLabel.setFont(Theme.Fonts.LABEL_XLARGE);
        add(bullishLabel, "cell 23 2,align center center,grow 0 0");

        //---- progressBar1 ----
        progressBar1.setValue(50);
        progressBar1.setBackground(Theme.Colors.PROGRESS_NEGATIVE);
        progressBar1.setForeground(Theme.Colors.PROGRESS_POSITIVE);
        add(progressBar1, "cell 1 4 23 1");

        //---- bullishTradesNumberLabel ----
        bullishTradesNumberLabel.setText("55");
        bullishTradesNumberLabel.setFont(Theme.Fonts.LABEL_LARGE);
        add(bullishTradesNumberLabel, "cell 1 5,alignx center,growx 0");

        //---- bullishTradesPctLabel ----
        bullishTradesPctLabel.setText("(55 %)");
        bullishTradesPctLabel.setFont(Theme.Fonts.LABEL_LARGE);
        add(bullishTradesPctLabel, "cell 1 5");

        //---- bearishTradesNumberLabel ----
        bearishTradesNumberLabel.setText("60");
        bearishTradesNumberLabel.setFont(Theme.Fonts.LABEL_LARGE);
        add(bearishTradesNumberLabel, "cell 23 5,alignx center,growx 0");

        //---- bearishTradesPctLabel ----
        bearishTradesPctLabel.setText("(60 %)");
        bearishTradesPctLabel.setFont(Theme.Fonts.LABEL_LARGE);
        add(bearishTradesPctLabel, "cell 23 5");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JLabel bearishLabel;
    private JLabel neutralLabel;
    private JLabel bullishLabel;
    private JProgressBar progressBar1;
    private JLabel bullishTradesNumberLabel;
    private JLabel bullishTradesPctLabel;
    private JLabel bearishTradesNumberLabel;
    private JLabel bearishTradesPctLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("totalTradesTaken".equals(event.getPropertyName())) {
            int totalTrades = (int) event.getNewValue();
            progressBar1.setMaximum(totalTrades);
        }

        if ("buyTrades".equals(event.getPropertyName())) {
            int buyTrades = (int) event.getNewValue();
            bullishTradesNumberLabel.setText(String.valueOf(buyTrades));
            progressBar1.setValue(buyTrades);
        }

        if ("sellTrades".equals(event.getPropertyName())) {
            int sellTrades = (int) event.getNewValue();
            bearishTradesNumberLabel.setText(String.valueOf(sellTrades));
        }

        if ("buyTradesPct".equals(event.getPropertyName())) {
            double buyTradesPct = (double) event.getNewValue();
            bullishTradesPctLabel.setText(String.format("(%.2f %%)", buyTradesPct));
        }

        if ("sellTradesPct".equals(event.getPropertyName())) {
            double sellTradesPct = (double) event.getNewValue();
            bearishTradesPctLabel.setText(String.format("(%.2f %%)", sellTradesPct));
        }
    }
}
