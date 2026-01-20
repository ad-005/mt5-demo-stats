package gui_elements.renderers;

import constants.TradingSession;

import javax.swing.*;
import java.awt.*;

public class SessionComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof TradingSession session) {
            setText(session.sessionName);
        } else if (value == null) {
            setText("All");
        }

        return this;
    }
}
