/*
 * Created by JFormDesigner on Tue Jan 06 20:02:58 CET 2026
 */

package gui_elements.components.pages;

import constants.Theme;
import javax.swing.*;

import controllers.OverallStatsController;
import gui_elements.components.panels.*;
import net.miginfocom.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author root
 */
public class HomePage extends JPanel {
    private PerformanceOverviewPanel performanceOverviewPanel1;
    private DailyWinratesPanel dailyWinratesPanel1;
    private TopSymbolsPanel topSymbolsPanel1;
    private JScrollPane scrollPane;
    private JPanel contentContainer;
    private JButton saveAsReportButton;

    public HomePage() {
        initComponents();
        buildTieredLayout();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        accountSelectionPanel1 = new AccountSelectionPanel();
        tradingBiasPanel1 = new TradingBiasPanel();
        winratePanel1 = new WinratePanel();
        sessionWinratesCustomPanel1 = new SessionWinratesCustomPanel();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3,align center center",
            // columns
            "[]" +
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
            "[]"));
        add(accountSelectionPanel1, "cell 12 0");
        add(tradingBiasPanel1, "cell 12 1 1 2");
        add(winratePanel1, "cell 12 3");
        add(sessionWinratesCustomPanel1, "cell 12 4,growx");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private AccountSelectionPanel accountSelectionPanel1;
    private TradingBiasPanel tradingBiasPanel1;
    private WinratePanel winratePanel1;
    private SessionWinratesCustomPanel sessionWinratesCustomPanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    private void buildTieredLayout() {
        performanceOverviewPanel1 = new PerformanceOverviewPanel();
        dailyWinratesPanel1 = new DailyWinratesPanel();
        topSymbolsPanel1 = new TopSymbolsPanel();

        JPanel overviewRow = new JPanel(new GridLayout(1, 2, 10, 0));
        overviewRow.setOpaque(false);
        overviewRow.add(winratePanel1);
        overviewRow.add(tradingBiasPanel1);

        contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        contentContainer.setOpaque(false);
        contentContainer.add(accountSelectionPanel1);
        contentContainer.add(Box.createVerticalStrut(10));
        saveAsReportButton = new JButton("Save as Report");
        saveAsReportButton.setFont(Theme.Fonts.BUTTON_DEFAULT);
        saveAsReportButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentContainer.add(saveAsReportButton);
        contentContainer.add(Box.createVerticalStrut(10));
        contentContainer.add(overviewRow);
        contentContainer.add(Box.createVerticalStrut(10));
        contentContainer.add(sessionWinratesCustomPanel1);
        contentContainer.add(Box.createVerticalStrut(10));
        contentContainer.add(performanceOverviewPanel1);
        contentContainer.add(Box.createVerticalStrut(10));
        contentContainer.add(dailyWinratesPanel1);
        contentContainer.add(Box.createVerticalStrut(10));
        contentContainer.add(topSymbolsPanel1);

        scrollPane = new JScrollPane(contentContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);

        removeAll();
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void setStatsController(OverallStatsController controller) {
        winratePanel1.setController(controller);
        tradingBiasPanel1.setController(controller);
        sessionWinratesCustomPanel1.setController(controller);
        performanceOverviewPanel1.setController(controller);
        dailyWinratesPanel1.setController(controller);
        topSymbolsPanel1.setController(controller);
    }
    
    public AccountSelectionPanel getAccountSelectionPanel() { return accountSelectionPanel1; }

    public void setSaveAsReportListener(ActionListener listener) {
        if (saveAsReportButton != null) {
            for (ActionListener existing : saveAsReportButton.getActionListeners()) {
                saveAsReportButton.removeActionListener(existing);
            }
            saveAsReportButton.addActionListener(listener);
        }
    }
}
