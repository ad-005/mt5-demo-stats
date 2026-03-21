package data_structures;

import constants.TradingSession;

import java.util.EnumMap;
import java.util.Map;

public class CachedTradeStatistics {
    private int totalTradesTaken;
    private int tradesWon;
    private int tradesLost;
    private double winrate;
    private int tradesBreakeven;
    private int buyTrades;
    private int sellTrades;
    private double buyTradesPct;
    private double sellTradesPct;
    private Map<TradingSession, Session> sessionStats = new EnumMap<>(TradingSession.class);

    public CachedTradeStatistics() {
    }

    public CachedTradeStatistics(TradeStatistics stats) {
        this.totalTradesTaken = stats.totalTradesTaken();
        this.tradesWon = stats.tradesWon();
        this.tradesLost = stats.tradesLost();
        this.winrate = stats.winrate();
        this.tradesBreakeven = stats.tradesBreakeven();
        this.buyTrades = stats.buyTrades();
        this.sellTrades = stats.sellTrades();
        this.buyTradesPct = stats.buyTradesPct();
        this.sellTradesPct = stats.sellTradesPct();
        this.sessionStats = new EnumMap<>(TradingSession.class);
        if (stats.sessionStats() != null) {
            this.sessionStats.putAll(stats.sessionStats());
        }
    }

    public TradeStatistics toTradeStatistics() {
        Map<TradingSession, Session> statsBySession = new EnumMap<>(TradingSession.class);
        if (sessionStats != null) {
            statsBySession.putAll(sessionStats);
        }

        return new TradeStatistics(
                totalTradesTaken,
                tradesWon,
                tradesLost,
                winrate,
                tradesBreakeven,
                buyTrades,
                sellTrades,
                buyTradesPct,
                sellTradesPct,
                statsBySession
        );
    }
}
