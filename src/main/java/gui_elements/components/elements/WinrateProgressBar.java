package gui_elements.components.elements;

import constants.UIConstants;


import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WinrateProgressBar extends JProgressBar {

    public WinrateProgressBar() {
        super(0, 100);
        setStringPainted(false);

        getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                determineColorBasedOnValue();
            }
        });
    }

    public WinrateProgressBar(int value) {
        this();
        setValue(value);
    }

    private void determineColorBasedOnValue() {
        setForeground((this.getValue() > 50) ? UIConstants.PROFIT_GREEN_COLOR : UIConstants.LOSS_RED_COLOR);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        determineColorBasedOnValue();
    }
}