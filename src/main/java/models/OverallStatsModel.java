package models;

import data_structures.TradeStatistics;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class OverallStatsModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

    public void updateFrom(TradeStatistics stats) {
        pcs.firePropertyChange("totalTradesTaken", null, stats.totalTradesTaken());
        pcs.firePropertyChange("tradesWon", null, stats.tradesWon());
        pcs.firePropertyChange("tradesLost", null, stats.tradesLost());
        pcs.firePropertyChange("winrate", null, stats.winrate());
        pcs.firePropertyChange("tradesBreakeven", null, stats.tradesBreakeven());
        pcs.firePropertyChange("buyTrades", null, stats.buyTrades());
        pcs.firePropertyChange("sellTrades", null, stats.sellTrades());
        pcs.firePropertyChange("buyTradesPct", null, stats.buyTradesPct());
        pcs.firePropertyChange("sellTradesPct", null, stats.sellTradesPct());
        pcs.firePropertyChange("sessionStats", null, stats.sessionStats());
    }
}
