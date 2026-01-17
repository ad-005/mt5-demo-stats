/*
 * Created by JFormDesigner on Tue Jan 06 14:43:47 CET 2026
 */

package gui_elements.components.panels;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.awt.*;
import java.time.format.DateTimeParseException;
import javax.swing.*;
import javax.swing.border.*;

import data_structures.Account;
import gui_elements.renderers.AccountComboBoxRenderer;
import interfaces.ModelObserver;
import net.miginfocom.swing.*;

import java.util.List;

import java.time.format.DateTimeFormatter;

/**
 * @author root
 */
public class SearchFieldPanel extends JPanel implements ModelObserver {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DefaultComboBoxModel<Account> accountComboBoxModel;

    public SearchFieldPanel() {
        initComponents();
//        setAccountComboBoxProperties();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        accountComboBox = new JComboBox();
        label1 = new JLabel();
        searchByDateButton = new JButton();
        startDateTextField = new JTextField();
        endDateTextField = new JTextField();
        startDateLabel = new JLabel();
        endDateLabel = new JLabel();
        searchByDateLabel = new JLabel();
        tradesShownLabel = new JLabel();

        //======== this ========
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MigLayout(
            "btt,insets panel,hidemode 3,align center center,gap 5 5",
            // columns
            "[527,grow,fill]",
            // rows
            "[grow,fill]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- accountComboBox ----
        accountComboBox.setFont(new Font("IBM Plex Mono", Font.BOLD, 12));
        accountComboBox.setToolTipText("Pick an account for which to show trades.");
        add(accountComboBox, "pad 0,cell 0 2,alignx center,growx 0");

        //---- label1 ----
        label1.setText("Search by account");
        label1.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(label1, "cell 0 3,alignx center,growx 0");

        //---- searchByDateButton ----
        searchByDateButton.setText("Search");
        searchByDateButton.setFont(new Font("IBM Plex Mono", Font.BOLD, 14));
        searchByDateButton.setAutoscrolls(true);
        add(searchByDateButton, "cell 0 4,alignx center,growx 0,wmin 100");

        //---- startDateTextField ----
        startDateTextField.setFont(new Font("IBM Plex Mono", Font.PLAIN, 14));
        startDateTextField.setBackground(Color.white);
        startDateTextField.setText("DD-MM-YYYY");
        startDateTextField.setHorizontalAlignment(SwingConstants.CENTER);
        startDateTextField.setToolTipText("Format: DD-MM-YYYY");
        add(startDateTextField, "cell 0 5,dock center,wmin 220");

        //---- endDateTextField ----
        endDateTextField.setText("DD-MM-YYYY");
        endDateTextField.setFont(new Font("IBM Plex Mono", Font.PLAIN, 14));
        endDateTextField.setHorizontalAlignment(SwingConstants.CENTER);
        endDateTextField.setToolTipText("Format: DD-MM-YYYY");
        add(endDateTextField, "cell 0 5,dock center,wmin 220");

        //---- startDateLabel ----
        startDateLabel.setText("Start date");
        startDateLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 14));
        startDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(startDateLabel, "pad 0,cell 0 6,dock center");

        //---- endDateLabel ----
        endDateLabel.setText("End date");
        endDateLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 14));
        endDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(endDateLabel, "pad 0,cell 0 6,dock center");

        //---- searchByDateLabel ----
        searchByDateLabel.setText("Search by date");
        searchByDateLabel.setFont(new Font("IBM Plex Mono", Font.BOLD, 16));
        add(searchByDateLabel, "cell 0 7,alignx center,growx 0");

        //---- tradesShownLabel ----
        tradesShownLabel.setText("Showing n out of n trades");
        tradesShownLabel.setFont(new Font("IBM Plex Mono", Font.PLAIN, 12));
        add(tradesShownLabel, "cell 0 8");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private JComboBox accountComboBox;
    private JLabel label1;
    private JButton searchByDateButton;
    private JTextField startDateTextField;
    private JTextField endDateTextField;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private JLabel searchByDateLabel;
    private JLabel tradesShownLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on

    // START GETTERS
    public JTextField getStartDateField() {
        return startDateTextField;
    }

    public JTextField getEndDateField() {
        return endDateTextField;
    }

    public JButton getSearchByDateButton() {
        return searchByDateButton;
    }

    public JLabel getTradesShownLabel() {
        return tradesShownLabel;
    }

    public DefaultComboBoxModel<Account> getAccountComboBoxModel() {
        return accountComboBoxModel;
    }
    // END GETTERS

    // START SETTERS
    public void clearStartDateTextField() {
        startDateTextField.setText("DD-MM-YYYY");
    }

    public void clearEndDateTextField() {
        endDateTextField.setText("DD-MM-YYYY");
    }

    public void setTradesShownText(int shown, int total) {
        tradesShownLabel.setText("Showing " + shown + " out of " + total + " trades");

    }

    public void setAccountComboBoxModel(List<Account> accounts) {
        Account[] accountArray = accounts.toArray(new Account[0]);
        this.accountComboBoxModel = new DefaultComboBoxModel<>(accountArray);
    }
    // END SETTERS

    // Helper method for setting account combo box properties
//    private void setAccountComboBoxProperties() {
//        accountComboBox.setRenderer(new AccountComboBoxRenderer());
//        accountComboBox.setModel(accountComboBoxModel);
//    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent event) {

    }
}
