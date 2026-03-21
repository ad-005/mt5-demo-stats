package gui_elements.components.pages;

import gui_elements.tables.ReportsTable;
import models.ReportDataModel;
import services.ReportManagementService;
import table_controllers.ReportsTableController;
import table_models.ReportsTableModel;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class ReportsPage extends JPanel {
    private final ReportsTable reportsTable;
    private final JButton deleteReportButton;
    private final ReportsTableController reportsTableController;

    public ReportsPage(ReportDataModel reportDataModel, ReportManagementService reportManagementService) {
        setLayout(new BorderLayout(0, 10));

        ReportsTableModel reportsTableModel = new ReportsTableModel();
        reportsTable = new ReportsTable();
        reportsTable.setTableModel(reportsTableModel);

        deleteReportButton = new JButton("Delete Selected Report");

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(deleteReportButton);

        add(reportsTable, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        reportsTableController = new ReportsTableController(
                reportDataModel,
                reportsTableModel,
                reportsTable.getTable(),
                deleteReportButton,
                reportManagementService
        );
    }

    public void refreshReports() {
        reportsTableController.refreshReports();
    }
}
