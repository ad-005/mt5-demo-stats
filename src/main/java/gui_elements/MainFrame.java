package gui_elements;

import constants.Theme;
import gui_elements.components.MainViewPanel;

import javax.swing.JFrame;

import java.awt.*;


public class MainFrame extends JFrame {

    // Constructor
    public MainFrame() {
        setTitle("MT5 Demo Account Stats");
        setSize(1200, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Theme.Colors.APP_BACKGROUND);
        setLocationRelativeTo(null);

        initGUI();
    }

    // Helper method for initializing GUI elements
    private void initGUI() {
        setContentPane(new MainViewPanel());
        pack();

        // Previous code
//        JPanel mainContentPanel = new JPanel(cardLayout);
//        mainContentPanel.setLayout(new GridBagLayout());
//
//        JLabel testLabel = new JLabel("TABLE PLACEHOLDER");
//        testLabel.setFont(UIConstants.DEFAULT_LABEL_FONT);
//
//        mainContentPanel.add(testLabel);
//
//        // Add test test table panel (JFormDesigner first try) to mainContentPanel TODO
//
//        mainContentPanel.setBackground(new Color(198, 198, 198));
//        this.add(mainContentPanel, BorderLayout.CENTER);
//
//        // Add selection panel to JFrame
//        SelectionPanel selectionPanel = new SelectionPanel();
//        this.add(selectionPanel, BorderLayout.NORTH);
    }

}
