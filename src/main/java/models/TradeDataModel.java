package models;

import data.MockDataFactory;
import data_structures.Trade;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TradeDataModel {
    private final PropertyChangeSupport pcs;
    private List<Trade> trades;

    public static final String TRADES_PROPERTY = "trades";

    public TradeDataModel() {
        this.pcs = new PropertyChangeSupport(this);
        this.trades = new ArrayList<>();
    }

    public void setTrades(List<Trade> trades) {
        if (trades == null) {
            throw new IllegalArgumentException("List of trades cannot be null.");
        }

        final List<Trade> oldTrades = this.trades;
        this.trades = new ArrayList<>(trades);

        if (SwingUtilities.isEventDispatchThread()) {
            pcs.firePropertyChange(TRADES_PROPERTY, oldTrades, this.trades);
        } else {
            SwingUtilities.invokeLater(() ->
                    pcs.firePropertyChange(TRADES_PROPERTY, oldTrades, this.trades)
            );
        }
    }

    public List<Trade> getTrades() {
        return Collections.unmodifiableList(trades);
    }

    public int getTradeCount() {
        return trades.size();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }
}
