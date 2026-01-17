/*
 * Created by JFormDesigner on Sat Jan 17 21:51:29 CET 2026
 */

package gui_elements.components.panels;

import data_structures.Account;
import gui_elements.renderers.AccountComboBoxRenderer;
import models.AccountSelectionModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author root
 */
public class AccountSelectionPanel extends JPanel {
    private AccountSelectionModel model;

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
        purposeLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 12));
        purposeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        purposeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        purposeLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        purposeLabel.setBackground(Color.white);
        add(purposeLabel);

        //---- accountComboBox ----
        accountComboBox.setFont(new Font("IBM Plex Mono", Font.BOLD, 14));
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
        accountComboBox.removeAllItems();
        accountComboBox.addItem(null);

        for (Account account : model.getAccounts()) {
            accountComboBox.addItem(account);
        }
        accountComboBox.setSelectedItem(model.getSelectedAccount());
    }

    private void bindListeners() {
        accountComboBox.addActionListener(e -> {
            model.setSelectedAccount((Account) accountComboBox.getSelectedItem());
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
    }
}
