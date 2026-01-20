package models;

import constants.TradingSession;
import data_structures.Session;
import services.TradeStatisticsService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SessionSelectionModel {
    public final TradingSession ALL_SESSIONS = null;
    private final List<TradingSession> sessions = new ArrayList<>(Arrays.asList(TradingSession.values()));
    private TradingSession selectedSession = ALL_SESSIONS;

    public static final String SESSION_SELECTED_PROPERTY = "sessionSelected";
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public SessionSelectionModel() {

    }

    // START GETTERS
    public TradingSession getSelectedSession() { return selectedSession; }

    public boolean isAllSessionsSelected() { return selectedSession == ALL_SESSIONS; }

    public List<TradingSession> getSessions() { return sessions; }
    // END GETTERS

    // START SETTERS
    public void setSelectedSession(TradingSession session) {
        TradingSession oldValue = this.selectedSession;
        this.selectedSession = session;
        pcs.firePropertyChange(SESSION_SELECTED_PROPERTY, oldValue, session);
    }
    // END SETTERS

    // ADD/REMOVE LISTENER
    public void addPropertyChangeListener(String property, PropertyChangeListener l) { pcs.addPropertyChangeListener(property, l); }
    public void removePropertyChangeListener(String property, PropertyChangeListener l) { pcs.removePropertyChangeListener(property, l); }

}
