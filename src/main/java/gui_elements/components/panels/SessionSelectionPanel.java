/*
 * Created by JFormDesigner on Tue Jan 20 13:47:59 GMT+01:00 2026
 */

package gui_elements.components.panels;

import constants.TradingSession;
import constants.Theme;
import gui_elements.renderers.SessionComboBoxRenderer;
import models.SessionSelectionModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author root
 */
public class SessionSelectionPanel extends JPanel {
    private SessionSelectionModel sessionSelectionModel;
    private boolean isUpdating = false;

    public SessionSelectionPanel() {
        initComponents();
        sessionComboBox.setRenderer(new SessionComboBoxRenderer());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        purposeLabel = new JLabel();
        sessionComboBox = new JComboBox();

        //======== this ========
        setLayout(new GridLayout(2, 1));

        //---- purposeLabel ----
        purposeLabel.setText("Choose a session to display information for");
        purposeLabel.setFont(Theme.Fonts.LABEL_BOLD);
        purposeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        purposeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        purposeLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        purposeLabel.setBackground(Theme.Colors.SURFACE_PRIMARY);
        add(purposeLabel);

        //---- sessionComboBox ----
        sessionComboBox.setFont(Theme.Fonts.LABEL_LARGE);
        add(sessionComboBox);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JLabel purposeLabel;
    private JComboBox sessionComboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    public JComboBox getSessionComboBox() {
        return sessionComboBox;
    }

    private void populateComboBox() {
        isUpdating = true;
        try {
            TradingSession currentSelection = sessionSelectionModel.getSelectedSession();

            sessionComboBox.removeAllItems();
            sessionComboBox.addItem(null);

            for (TradingSession session : sessionSelectionModel.getSessions()) {
                sessionComboBox.addItem(session);
            }

            if (currentSelection == null && sessionSelectionModel.getSessions().contains(currentSelection)) {
                sessionComboBox.setSelectedItem(currentSelection);
            } else {
                sessionComboBox.setSelectedItem(null);
                sessionSelectionModel.setSelectedSession(null);
            }
        } finally {
            isUpdating = false;
        }
    }

    private void bindListeners() {
        sessionComboBox.addActionListener(e -> {
            if (sessionSelectionModel != null && !isUpdating) {
                TradingSession selectedSession = (TradingSession) sessionComboBox.getSelectedItem();
                sessionSelectionModel.setSelectedSession(selectedSession);
            }
        });

        sessionSelectionModel.addPropertyChangeListener(SessionSelectionModel.SESSION_SELECTED_PROPERTY
                , event -> {
                    TradingSession newSelection = (TradingSession) event.getNewValue();
                    if (sessionComboBox.getSelectedItem() != newSelection) {
                        sessionComboBox.setSelectedItem(newSelection);
                    }
        });
    }

    public void setSessionSelectionModel(SessionSelectionModel sessionSelectionModel) {
        this.sessionSelectionModel = sessionSelectionModel;
        populateComboBox();
        bindListeners();
    }
}
