package gui_elements.renderers;

import data_structures.Account;

import javax.swing.*;
import java.awt.*;

public class AccountComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Account account) {
            setText(account.getLogin());
        } else if (value == null) {
            setText("All");
        }

        return this;
    }
}
