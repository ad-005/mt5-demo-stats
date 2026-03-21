package data_structures;

import constants.TradingSession;

import java.util.EnumMap;
import java.util.LinkedHashMap;
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
    private double totalProfit;
    private double totalLoss;
    private double netProfit;
    private double averageProfit;
    private double averageLoss;
    private double largestWin;
    private double largestLoss;
    private double profitFactor;
    private double sharpeRatio;
    private double sortinoRatio;
    private double maxDrawdown;
    private double maxDrawdownPct;
    private double averageTrade;
    private double expectancy;
    private int consecutiveWins;
    private int consecutiveLosses;
    private double riskRewardRatio;
    private Map<String, Double> dailyWinRates = new LinkedHashMap<>();
    private Map<String, SymbolStatistics> symbolBreakdown = new LinkedHashMap<>();

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
        this.totalProfit = stats.totalProfit();
        this.totalLoss = stats.totalLoss();
        this.netProfit = stats.netProfit();
        this.averageProfit = stats.averageProfit();
        this.averageLoss = stats.averageLoss();
        this.largestWin = stats.largestWin();
        this.largestLoss = stats.largestLoss();
        this.profitFactor = stats.profitFactor();
        this.sharpeRatio = stats.sharpeRatio();
        this.sortinoRatio = stats.sortinoRatio();
        this.maxDrawdown = stats.maxDrawdown();
        this.maxDrawdownPct = stats.maxDrawdownPct();
        this.averageTrade = stats.averageTrade();
        this.expectancy = stats.expectancy();
        this.consecutiveWins = stats.consecutiveWins();
        this.consecutiveLosses = stats.consecutiveLosses();
        this.riskRewardRatio = stats.riskRewardRatio();
        if (stats.dailyWinRates() != null) {
            this.dailyWinRates.putAll(stats.dailyWinRates());
        }
        if (stats.symbolBreakdown() != null) {
            this.symbolBreakdown.putAll(stats.symbolBreakdown());
        }
    }

    public TradeStatistics toTradeStatistics() {
        Map<TradingSession, Session> statsBySession = new EnumMap<>(TradingSession.class);
        if (sessionStats != null) {
            statsBySession.putAll(sessionStats);
        }
        Map<String, Double> dailyStats = new LinkedHashMap<>();
        if (dailyWinRates != null) {
            dailyStats.putAll(dailyWinRates);
        }
        Map<String, SymbolStatistics> symbols = new LinkedHashMap<>();
        if (symbolBreakdown != null) {
            symbols.putAll(symbolBreakdown);
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
                statsBySession,
                totalProfit,
                totalLoss,
                netProfit,
                averageProfit,
                averageLoss,
                largestWin,
                largestLoss,
                profitFactor,
                sharpeRatio,
                sortinoRatio,
                maxDrawdown,
                maxDrawdownPct,
                averageTrade,
                expectancy,
                consecutiveWins,
                consecutiveLosses,
                riskRewardRatio,
                dailyStats,
                symbols
        );
    }
}
