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
        pcs.firePropertyChange("totalProfit", null, stats.totalProfit());
        pcs.firePropertyChange("totalLoss", null, stats.totalLoss());
        pcs.firePropertyChange("netProfit", null, stats.netProfit());
        pcs.firePropertyChange("averageProfit", null, stats.averageProfit());
        pcs.firePropertyChange("averageLoss", null, stats.averageLoss());
        pcs.firePropertyChange("largestWin", null, stats.largestWin());
        pcs.firePropertyChange("largestLoss", null, stats.largestLoss());
        pcs.firePropertyChange("profitFactor", null, stats.profitFactor());
        pcs.firePropertyChange("sharpeRatio", null, stats.sharpeRatio());
        pcs.firePropertyChange("sortinoRatio", null, stats.sortinoRatio());
        pcs.firePropertyChange("maxDrawdown", null, stats.maxDrawdown());
        pcs.firePropertyChange("maxDrawdownPct", null, stats.maxDrawdownPct());
        pcs.firePropertyChange("averageTrade", null, stats.averageTrade());
        pcs.firePropertyChange("expectancy", null, stats.expectancy());
        pcs.firePropertyChange("consecutiveWins", null, stats.consecutiveWins());
        pcs.firePropertyChange("consecutiveLosses", null, stats.consecutiveLosses());
        pcs.firePropertyChange("riskRewardRatio", null, stats.riskRewardRatio());
        pcs.firePropertyChange("dailyWinRates", null, stats.dailyWinRates());
        pcs.firePropertyChange("symbolBreakdown", null, stats.symbolBreakdown());
    }
}
