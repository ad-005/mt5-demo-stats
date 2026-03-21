package gui_elements.components.panels;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import constants.Theme;
import constants.ViewType;
import gui_elements.components.elements.AppIcons;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.function.Consumer;

public class SelectionPanel extends JPanel {
    private Consumer<ViewType> navigationListener;
    private Runnable accountSidebarToggleListener;
    private JButton accountSidebarButton;

    // Constructors
    public SelectionPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 10, 10, 10));
    }

    // Helper method for initialization of panel elements
    private void initElements() {
        List<JToggleButton> selectionButtons = new ArrayList<>();

        JPanel selectionWrapper = new JPanel();
        selectionWrapper.setLayout(new GridLayout(1, 3, 10, 0));

        selectionButtons.add(createToggleButton("Home", ViewType.HOME));
        selectionButtons.add(createToggleButton("Search", ViewType.SEARCH));
        selectionButtons.add(createToggleButton("Reports", ViewType.REPORTS));

        ButtonGroup selectionButtonGroup = groupToggleButtons(selectionButtons);

        selectionButtons.getFirst().setSelected(true);

        setupListeners(selectionButtons);
        addToggleButtons(selectionWrapper, selectionButtons);

        accountSidebarButton = new JButton(createAccountSidebarIcon());
        accountSidebarButton.setToolTipText("Toggle accounts sidebar");
        accountSidebarButton.setFocusPainted(false);
        accountSidebarButton.setBorder(Theme.Borders.padding(8));
        accountSidebarButton.setContentAreaFilled(false);
        accountSidebarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        accountSidebarButton.addActionListener(e -> {
            if (accountSidebarToggleListener != null) {
                accountSidebarToggleListener.run();
            }
        });

        JPanel leftWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftWrapper.setOpaque(false);
        leftWrapper.add(accountSidebarButton);

        this.add(leftWrapper, BorderLayout.WEST);
        this.add(selectionWrapper, BorderLayout.CENTER);
    }

    // Helper method for grouping of toggle buttons
    private ButtonGroup groupToggleButtons(List<JToggleButton> toggleButtons) {
        ButtonGroup group = new ButtonGroup();

        for (JToggleButton toggleButton : toggleButtons) {
            group.add(toggleButton);
        }

        return group;
    }

    // Helper method for button creation
    private JToggleButton createToggleButton(String buttonText, ViewType viewType) {
        JToggleButton button = new JToggleButton(buttonText);

        button.setFont(Theme.Fonts.BUTTON_DEFAULT);
        button.putClientProperty(FlatClientProperties.STYLE_CLASS, "selectionButton");
        button.putClientProperty("viewType", viewType);

        return button;
    }

    // Helper method for adding created toggle buttons to wrapper panel
    private void addToggleButtons(JPanel toggleWrapper, List<JToggleButton> toggleButtons) {
        for (JToggleButton toggleButton : toggleButtons) {
            toggleWrapper.add(toggleButton);
        }
    }

    // Helper method for setting up the action listeners
    private void setupListeners(List<JToggleButton> selectionButtons) {
        for (JToggleButton toggleButton : selectionButtons) {
            ViewType viewType = (ViewType) toggleButton.getClientProperty("viewType");
            toggleButton.addActionListener(e -> navigationListener.accept(viewType));
        }

    }

    // Navigation listener setter + panel element initialization
    public void setNavigationListener(Consumer<ViewType> listener) {
        this.navigationListener = listener;
        initElements();
    }

    public void setAccountSidebarToggleListener(Runnable listener) {
        this.accountSidebarToggleListener = listener;
    }

    private Icon createAccountSidebarIcon() {
        try {
            FlatSVGIcon icon = new FlatSVGIcon("icons/account.svg", 20, 20);
            if (icon.hasFound()) {
                return icon;
            }
        } catch (Exception e) {
            // Fall back to painted icon below.
        }
        return AppIcons.account(20, Theme.Colors.TEXT_PRIMARY);
    }

}
