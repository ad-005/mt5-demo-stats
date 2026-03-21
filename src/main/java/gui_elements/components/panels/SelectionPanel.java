package gui_elements.components.panels;

import com.formdev.flatlaf.FlatClientProperties;
import constants.Theme;
import constants.ViewType;

import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.util.function.Consumer;

public class SelectionPanel extends JPanel {
    private Consumer<ViewType> navigationListener;

    // Constructors
    public SelectionPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 10, 10, 10));
    }

    // Helper method for initialization of panel elements
    private void initElements() {
        List<JToggleButton> selectionButtons = new ArrayList<>();

        JPanel selectionWrapper = new JPanel();
        selectionWrapper.setLayout(new GridLayout(1, 4, 10, 0));

        selectionButtons.add(createToggleButton("Home", ViewType.HOME));
        selectionButtons.add(createToggleButton("Search", ViewType.SEARCH));
        selectionButtons.add(createToggleButton("Reports", ViewType.REPORTS));
        selectionButtons.add(createToggleButton("Accounts", ViewType.ACCOUNTS));

        ButtonGroup selectionButtonGroup = groupToggleButtons(selectionButtons);

        selectionButtons.getFirst().setSelected(true);

        setupListeners(selectionButtons);
        addToggleButtons(selectionWrapper, selectionButtons);

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

}
