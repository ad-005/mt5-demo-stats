/*
 * Created by JFormDesigner on Sat Jan 17 21:51:29 CET 2026
 */

package gui_elements.components.panels;

import data_structures.Account;
import gui_elements.renderers.AccountComboBoxRenderer;
import models.AccountSelectionModel;
import constants.Theme;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author root
 */
public class AccountSelectionPanel extends JPanel {
    private AccountSelectionModel model;
    private boolean isUpdating = false;

    public AccountSelectionPanel() {
        initComponents();
        accountComboBox.setRenderer(new AccountComboBoxRenderer());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        purposeLabel = new JLabel();
        accountComboBox = new JComboBox();

        //======== this ========
        setLayout(new GridLayout(2, 1));

        //---- purposeLabel ----
        purposeLabel.setText("Choose an account to display information for");
        purposeLabel.setFont(Theme.Fonts.LABEL_BOLD);
        purposeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        purposeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        purposeLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        purposeLabel.setBackground(Theme.Colors.SURFACE_PRIMARY);
        add(purposeLabel);

        //---- accountComboBox ----
        accountComboBox.setFont(Theme.Fonts.LABEL_LARGE);
        add(accountComboBox);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JLabel purposeLabel;
    private JComboBox accountComboBox;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // START GETTERS
    public JComboBox getAccountComboBox() { return accountComboBox; }
    // END GETTERS

    public void setModel(AccountSelectionModel model) {
        this.model = model;
        populateComboBox();
        bindListeners();
    }

    private void populateComboBox() {
        isUpdating = true;
        try {
            Account currentSelection = model.getSelectedAccount();

            accountComboBox.removeAllItems();
            accountComboBox.addItem(null);

            for (Account account : model.getAccounts()) {
                accountComboBox.addItem(account);
            }

            if (currentSelection != null && model.getAccounts().contains(currentSelection)) {
                accountComboBox.setSelectedItem(currentSelection);
            } else {
                accountComboBox.setSelectedItem(null);
                model.setSelectedAccount(null);
            }
        } finally {
            isUpdating = false;
        }
    }

    private void bindListeners() {
        accountComboBox.addActionListener(e -> {
            if (model != null && !isUpdating) {
                Account selected = (Account) accountComboBox.getSelectedItem();
//                System.out.println("Account selected: " + (selected == null ? "All" : selected.getLogin()));
                model.setSelectedAccount(selected);
            }
        });

        model.addPropertyChangeListener(
                AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY,
                evt -> {
                    Account newSelection = (Account) evt.getNewValue();
                    if (accountComboBox.getSelectedItem() != newSelection) {
                        accountComboBox.setSelectedItem(newSelection);
                    }
                }
        );

        model.addPropertyChangeListener(AccountSelectionModel.AVAILABLE_ACCOUNTS_PROPERTY,
                evt -> {
                    populateComboBox();
                }
        );
    }
}
