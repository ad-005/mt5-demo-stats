package gui_elements.components.panels;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import constants.Theme;
import data_structures.Account;
import gui_elements.components.elements.AppIcons;
import models.AccountSelectionModel;
import services.AccountManagementService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AccountSidebarPanel extends JPanel {
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final AccountSelectionModel accountSelectionModel;
    private final AccountManagementService accountManagementService;
    private final JPanel accountsContainer;

    public AccountSidebarPanel(AccountSelectionModel accountSelectionModel, AccountManagementService accountManagementService) {
        this.accountSelectionModel = accountSelectionModel;
        this.accountManagementService = accountManagementService;

        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(12, 10, 12, 10));
        setBackground(Theme.Colors.SURFACE_SECONDARY);
        setPreferredSize(new Dimension(280, 0));

        JPanel header = new JPanel(new BorderLayout(8, 0));
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("Accounts");
        titleLabel.setFont(Theme.Fonts.LABEL_XLARGE);
        titleLabel.setForeground(Theme.Colors.TEXT_PRIMARY);
        header.add(titleLabel, BorderLayout.WEST);

        JButton addButton = new JButton("+");
        addButton.setToolTipText("Add account");
        addButton.setFont(Theme.Fonts.LABEL_XLARGE);
        addButton.setFocusable(false);
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showAddAccountDialog());
        header.add(addButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        accountsContainer = new JPanel();
        accountsContainer.setLayout(new BoxLayout(accountsContainer, BoxLayout.Y_AXIS));
        accountsContainer.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(accountsContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        add(scrollPane, BorderLayout.CENTER);

        bindModelListeners();
        renderAccounts();
    }

    private void bindModelListeners() {
        accountSelectionModel.addPropertyChangeListener(AccountSelectionModel.AVAILABLE_ACCOUNTS_PROPERTY, evt -> renderAccounts());
        accountSelectionModel.addPropertyChangeListener(AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY, evt -> renderAccounts());
    }

    private void renderAccounts() {
        accountsContainer.removeAll();
        accountsContainer.add(createAllAccountsCard());
        accountsContainer.add(Box.createVerticalStrut(8));

        for (Account account : accountSelectionModel.getAccounts()) {
            accountsContainer.add(createAccountCard(account));
            accountsContainer.add(Box.createVerticalStrut(8));
        }

        if (accountSelectionModel.getAccounts().isEmpty()) {
            JLabel emptyLabel = new JLabel("No accounts yet");
            emptyLabel.setFont(Theme.Fonts.LABEL_DEFAULT);
            emptyLabel.setForeground(Theme.Colors.TEXT_SECONDARY);
            emptyLabel.setBorder(new EmptyBorder(8, 8, 8, 8));
            accountsContainer.add(emptyLabel);
        }

        accountsContainer.revalidate();
        accountsContainer.repaint();
    }

    private JPanel createAllAccountsCard() {
        boolean selected = accountSelectionModel.isAllAccountsSelected();
        JPanel card = createCardContainer(selected);

        JLabel title = new JLabel("All Accounts");
        title.setFont(Theme.Fonts.LABEL_BOLD);
        title.setForeground(Theme.Colors.TEXT_PRIMARY);

        JLabel subtitle = new JLabel("Use combined view");
        subtitle.setFont(Theme.Fonts.LABEL_DEFAULT);
        subtitle.setForeground(Theme.Colors.TEXT_SECONDARY);

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(title);
        text.add(Box.createVerticalStrut(4));
        text.add(subtitle);
        card.add(text, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountSelectionModel.setSelectedAccount(null);
            }
        });
        return card;
    }

    private JPanel createAccountCard(Account account) {
        boolean selected = account.equals(accountSelectionModel.getSelectedAccount());
        JPanel card = createCardContainer(selected);

        JLabel name = new JLabel(account.getName());
        name.setFont(Theme.Fonts.LABEL_BOLD);
        name.setForeground(Theme.Colors.TEXT_PRIMARY);

        JLabel login = new JLabel("Login: " + account.getLogin());
        login.setFont(Theme.Fonts.LABEL_DEFAULT);
        login.setForeground(Theme.Colors.TEXT_SECONDARY);

        JLabel addedAt = new JLabel("Added: " + formatAddedAt(account.getAddedAt()));
        addedAt.setFont(Theme.Fonts.LABEL_DEFAULT);
        addedAt.setForeground(Theme.Colors.TEXT_SECONDARY);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(name);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(login);
        textPanel.add(Box.createVerticalStrut(3));
        textPanel.add(addedAt);

        JButton deleteButton = new JButton(createTrashIcon());
        deleteButton.setToolTipText("Delete account");
        deleteButton.setFocusable(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(e -> confirmAndDeleteAccount(account));

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);
        right.add(deleteButton, BorderLayout.NORTH);

        card.add(textPanel, BorderLayout.CENTER);
        card.add(right, BorderLayout.EAST);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                accountSelectionModel.setSelectedAccount(account);
            }
        });

        return card;
    }

    private JPanel createCardContainer(boolean selected) {
        JPanel card = new JPanel(new BorderLayout(8, 0));
        card.setOpaque(true);
        card.setBackground(selected ? Theme.Colors.SURFACE_PRIMARY : new Color(0xECEFF3));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(selected ? Theme.Colors.ACCENT : Theme.Colors.BORDER_DEFAULT, 1, true),
                new EmptyBorder(10, 10, 10, 8)
        ));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return card;
    }

    private void showAddAccountDialog() {
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.getRemoveAccountButton().setVisible(false);

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Add Account", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setContentPane(loginPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        loginPanel.getAddAccountButton().addActionListener(e -> {
            try {
                Account account = loginPanel.createAccountFromFields();
                accountManagementService.addAccount(account);
                dialog.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    private void confirmAndDeleteAccount(Account account) {
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete account \"" + account.getLogin() + "\"?",
                "Delete Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            accountManagementService.removeAccount(account.getLogin());
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatAddedAt(String value) {
        try {
            return LocalDate.parse(value).format(DISPLAY_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            return value == null ? "" : value;
        }
    }

    private Icon createTrashIcon() {
        try {
            FlatSVGIcon icon = new FlatSVGIcon("icons/trash.svg", 16, 16);
            if (icon.hasFound()) {
                return icon;
            }
        } catch (Exception e) {
            // Fall back to painted icon below.
        }
        return AppIcons.trash(16, Theme.Colors.TEXT_SECONDARY);
    }
}
