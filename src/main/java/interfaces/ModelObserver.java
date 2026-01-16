package interfaces;

import java.beans.PropertyChangeEvent;

public interface ModelObserver {
    void modelPropertyChange(PropertyChangeEvent event);
}
