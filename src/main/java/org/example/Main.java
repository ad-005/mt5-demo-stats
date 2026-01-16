package org.example;
import gui_elements.MainFrame;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class Main {

    public static void main(String[] args) {
        FlatLaf.registerCustomDefaultsSource("gui_elements/themes");
        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> {
            MainFrame gui = new MainFrame();
            gui.setVisible(true);
        });
    }

}