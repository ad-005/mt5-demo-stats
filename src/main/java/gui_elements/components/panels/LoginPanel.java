/*
 * Created by JFormDesigner on Tue Jan 06 00:04:32 CET 2026
 */

package gui_elements.components.panels;

import constants.AccountType;
import constants.Theme;
import data_structures.Account;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author root
 */
public class LoginPanel extends JPanel {
    public LoginPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        addAccountLabel = new JLabel();
        accountNameField = new JFormattedTextField();
        accountTypeField = new JFormattedTextField();
        accountServerField = new JFormattedTextField();
        accountLoginField = new JFormattedTextField();
        accountPasswordField = new JFormattedTextField();
        accountInvestorField = new JFormattedTextField();
        addAccountButton = new JButton();
        removeAccountButton = new JButton();

        //======== this ========
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridLayout(9, 1, 0, 10));

        //---- addAccountLabel ----
        addAccountLabel.setText("Add Account");
        addAccountLabel.setFont(Theme.Fonts.LABEL_HEADING);
        addAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addAccountLabel.setForeground(Theme.Colors.TEXT_PRIMARY);
        addAccountLabel.setBorder(null);
        add(addAccountLabel);

        //---- accountNameField ----
        accountNameField.setHorizontalAlignment(SwingConstants.CENTER);
        accountNameField.setText("Name");
        accountNameField.setFont(Theme.Fonts.LABEL_HEADING);
        accountNameField.setPreferredSize(null);
        accountNameField.setBorder(null);
        add(accountNameField);

        //---- accountTypeField ----
        accountTypeField.setHorizontalAlignment(SwingConstants.CENTER);
        accountTypeField.setText("Type");
        accountTypeField.setFont(Theme.Fonts.LABEL_HEADING);
        accountTypeField.setPreferredSize(null);
        accountTypeField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        accountTypeField.setBorder(null);
        add(accountTypeField);

        //---- accountServerField ----
        accountServerField.setHorizontalAlignment(SwingConstants.CENTER);
        accountServerField.setText("Server");
        accountServerField.setFont(Theme.Fonts.LABEL_HEADING);
        accountServerField.setPreferredSize(null);
        accountServerField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        accountServerField.setBorder(null);
        add(accountServerField);

        //---- accountLoginField ----
        accountLoginField.setHorizontalAlignment(SwingConstants.CENTER);
        accountLoginField.setText("Login");
        accountLoginField.setFont(Theme.Fonts.LABEL_HEADING);
        accountLoginField.setPreferredSize(null);
        accountLoginField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        accountLoginField.setBorder(null);
        add(accountLoginField);

        //---- accountPasswordField ----
        accountPasswordField.setHorizontalAlignment(SwingConstants.CENTER);
        accountPasswordField.setText("Password");
        accountPasswordField.setFont(Theme.Fonts.LABEL_HEADING);
        accountPasswordField.setPreferredSize(null);
        accountPasswordField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        accountPasswordField.setBorder(null);
        add(accountPasswordField);

        //---- accountInvestorField ----
        accountInvestorField.setHorizontalAlignment(SwingConstants.CENTER);
        accountInvestorField.setText("Investor");
        accountInvestorField.setFont(Theme.Fonts.LABEL_HEADING);
        accountInvestorField.setPreferredSize(null);
        accountInvestorField.setFocusLostBehavior(JFormattedTextField.PERSIST);
        accountInvestorField.setBorder(null);
        add(accountInvestorField);

        //---- addAccountButton ----
        addAccountButton.setText("Add");
        addAccountButton.setFont(Theme.Fonts.LABEL_HEADING);
        addAccountButton.setBackground(Theme.Colors.SURFACE_SECONDARY);
        addAccountButton.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        add(addAccountButton);

        //---- removeAccountButton ----
        removeAccountButton.setText("Remove an Account");
        removeAccountButton.setFont(Theme.Fonts.LABEL_HEADING);
        add(removeAccountButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JLabel addAccountLabel;
    private JFormattedTextField accountNameField;
    private JFormattedTextField accountTypeField;
    private JFormattedTextField accountServerField;
    private JFormattedTextField accountLoginField;
    private JFormattedTextField accountPasswordField;
    private JFormattedTextField accountInvestorField;
    private JButton addAccountButton;
    private JButton removeAccountButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // START GETTERS
    public JTextField getNameField() {
        return accountNameField;
    }

    public JTextField getTypeField() {
        return accountTypeField;
    }

    public JTextField getServerField() {
        return accountServerField;
    }

    public JTextField getLoginField() {
        return accountLoginField;
    }

    public JTextField getPasswordField() {
        return accountPasswordField;
    }

    public JTextField getInvestorField() {
        return accountInvestorField;
    }

    public JButton getAddAccountButton() {
        return addAccountButton;
    }
    
    public JButton getRemoveAccountButton() { return removeAccountButton; }
    // END GETTERS

    // Determine account type
    private AccountType determineType() {
        return switch (accountTypeField.getText()) {
            case "Forex Hedging USD" -> AccountType.FOREX_HEDGING_USD;
            case "Forex Hedging EUR" -> AccountType.FOREX_HEDGING_EUR;
            default -> null;
        };

    }

    // Method for account formation from input fields
    public Account createAccountFromFields() {
        return new Account(
                accountNameField.getText(),
                determineType(),
                accountServerField.getText(),
                accountLoginField.getText(),
                accountPasswordField.getText(),
                accountInvestorField.getText()
        );
    }

    public void clearInputs() {
        accountNameField.setText("Name");
        accountServerField.setText("Server");
        accountTypeField.setText("Type");
        accountLoginField.setText("Login");
        accountPasswordField.setText("Password");
        accountInvestorField.setText("Investor");
    }
}
