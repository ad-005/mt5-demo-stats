package table_controllers;

import data_structures.ReportSnapshot;
import models.ReportDataModel;
import services.ReportManagementService;
import table_models.ReportsTableModel;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ReportsTableController implements PropertyChangeListener {
    private final ReportDataModel reportDataModel;
    private final ReportsTableModel reportsTableModel;
    private final JTable reportsTable;
    private final JButton deleteButton;
    private final ReportManagementService reportManagementService;

    public ReportsTableController(ReportDataModel reportDataModel,
                                  ReportsTableModel reportsTableModel,
                                  JTable reportsTable,
                                  JButton deleteButton,
                                  ReportManagementService reportManagementService) {
        this.reportDataModel = reportDataModel;
        this.reportsTableModel = reportsTableModel;
        this.reportsTable = reportsTable;
        this.deleteButton = deleteButton;
        this.reportManagementService = reportManagementService;

        this.reportDataModel.addPropertyChangeListener(ReportDataModel.REPORTS_PROPERTY, this);
        initializeListeners();
        refreshReports();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent evt) {
        reportsTableModel.setReports((List<ReportSnapshot>) evt.getNewValue());
    }

    private void initializeListeners() {
        deleteButton.addActionListener(e -> handleDeleteReport());
    }

    private void handleDeleteReport() {
        int modelRow = reportsTable.getSelectedRow();
        if (modelRow < 0) {
            JOptionPane.showMessageDialog(reportsTable, "Please select a report to delete.", "No report selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int row = reportsTable.convertRowIndexToModel(modelRow);
        ReportSnapshot selected = reportDataModel.getReports().get(row);

        int result = JOptionPane.showConfirmDialog(
                reportsTable,
                "Delete selected report created at " + selected.getCreatedAt() + "?",
                "Delete report",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            reportManagementService.deleteReport(selected.getId());
            refreshReports();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(reportsTable, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshReports() {
        List<ReportSnapshot> reports = reportManagementService.getReports();
        if (SwingUtilities.isEventDispatchThread()) {
            reportDataModel.setReports(reports);
        } else {
            SwingUtilities.invokeLater(() -> reportDataModel.setReports(reports));
        }
    }
}
