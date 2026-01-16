/*
 * Created by JFormDesigner on Tue Jan 06 20:28:50 CET 2026
 */

package gui_elements.components.panels;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.*;
import com.formdev.flatlaf.*;
import controllers.OverallStatsController;
import interfaces.ModelObserver;
import net.miginfocom.swing.*;

/**
 * @author root
 */
public class WinratePanel extends JPanel implements PropertyChangeListener {
    private OverallStatsController controller;

    public WinratePanel() {
        initComponents();
    }

    public WinratePanel(OverallStatsController controller) {
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
        tradesWonLabel = new JLabel();
        progressBar1 = new JProgressBar();
        tradesLostLabel = new JLabel();
        totalTradesTextLabel = new JLabel();
        totalTradesLabel = new JLabel();

        //======== this ========
        setBorder(new TitledBorder(new LineBorder(Color.black, 2, true), "Winrate: ", TitledBorder.CENTER, TitledBorder.TOP,
            new Font("IBM Plex Mono", Font.BOLD, 16)));
        setLayout(new MigLayout(
            "hidemode 3,align center top",
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
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- tradesWonLabel ----
        tradesWonLabel.setText("Won: 44.3% (51)");
        tradesWonLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(tradesWonLabel, "cell 9 3");

        //---- progressBar1 ----
        progressBar1.setValue(44);
        progressBar1.putClientProperty(FlatClientProperties.PROGRESS_BAR_SQUARE, true);
        progressBar1.putClientProperty(FlatClientProperties.PROGRESS_BAR_LARGE_HEIGHT, true);
        progressBar1.setBackground(new Color(0xff3333));
        progressBar1.setForeground(new Color(0x56b32e));
        add(progressBar1, "cell 15 3");

        //---- tradesLostLabel ----
        tradesLostLabel.setText("Lost 55.7% (64)");
        tradesLostLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(tradesLostLabel, "cell 21 3");

        //---- totalTradesTextLabel ----
        totalTradesTextLabel.setText("Total trades taken");
        totalTradesTextLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(totalTradesTextLabel, "cell 15 4,alignx center,growx 0");

        //---- totalTradesLabel ----
        totalTradesLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(totalTradesLabel, "cell 15 5,alignx center,growx 0");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JLabel tradesWonLabel;
    private JProgressBar progressBar1;
    private JLabel tradesLostLabel;
    private JLabel totalTradesTextLabel;
    private JLabel totalTradesLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if ("totalTradesTaken".equals(event.getPropertyName())) {
            int totalTrades = (Integer) event.getNewValue();
            totalTradesLabel.setText(String.valueOf(totalTrades));
            progressBar1.setMaximum(totalTrades);
        }

        if ("tradesWon".equals(event.getPropertyName())) {
            int tradesWon = (Integer) event.getNewValue();
            tradesWonLabel.setText("Won: " + String.valueOf(tradesWon));
            progressBar1.setValue(tradesWon);
        }

        if ("tradesLost".equals(event.getPropertyName())) {
            int tradesLost = (Integer) event.getNewValue();
            tradesLostLabel.setText("Lost: " + String.valueOf(tradesLost));
        }

        if ("winrate".equals(event.getPropertyName())) {
            double winrate = (Double) event.getNewValue();
            TitledBorder border = (TitledBorder) this.getBorder();
            border.setTitle("Winrate: " + String.format("%.2f %%", winrate));
        }
    }
}
