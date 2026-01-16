/*
 * Created by JFormDesigner on Tue Jan 06 21:43:39 CET 2026
 */

package gui_elements.components.panels;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import gui_elements.tables.*;
import net.miginfocom.swing.*;

/**
 * @author root
 */
public class SessionWinratesPanel extends JPanel {
    public SessionWinratesPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Antonije Dragicevic
        sessionWinratesTable1 = new SessionWinratesTable();

        //======== this ========
        setLayout(new BorderLayout());

        //======== sessionWinratesTable1 ========
        {
            sessionWinratesTable1.setBorder(new CompoundBorder(
                new LineBorder(Color.black, 2, true),
                new EmptyBorder(10, 10, 10, 10)));
        }
        add(sessionWinratesTable1, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Antonije Dragicevic
    private SessionWinratesTable sessionWinratesTable1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
