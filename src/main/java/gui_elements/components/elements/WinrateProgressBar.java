package gui_elements.components.elements;

import constants.Theme;

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
        setForeground((this.getValue() > 50) ? Theme.Colors.SUCCESS : Theme.Colors.DANGER);
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        determineColorBasedOnValue();
    }
}