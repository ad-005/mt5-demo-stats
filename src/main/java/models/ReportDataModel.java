package models;

import data_structures.ReportSnapshot;

import javax.swing.SwingUtilities;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDataModel {
    public static final String REPORTS_PROPERTY = "reports";

    private final PropertyChangeSupport pcs;
    private List<ReportSnapshot> reports;

    public ReportDataModel() {
        this.pcs = new PropertyChangeSupport(this);
        this.reports = new ArrayList<>();
    }

    public void setReports(List<ReportSnapshot> reports) {
        if (reports == null) {
            throw new IllegalArgumentException("List of reports cannot be null.");
        }

        final List<ReportSnapshot> oldReports = this.reports;
        this.reports = new ArrayList<>(reports);

        if (SwingUtilities.isEventDispatchThread()) {
            pcs.firePropertyChange(REPORTS_PROPERTY, oldReports, this.reports);
        } else {
            SwingUtilities.invokeLater(() -> pcs.firePropertyChange(REPORTS_PROPERTY, oldReports, this.reports));
        }
    }

    public List<ReportSnapshot> getReports() {
        return Collections.unmodifiableList(reports);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }
}
