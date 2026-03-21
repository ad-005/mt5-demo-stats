package data_structures;

import constants.TradingSession;

import java.util.Map;

public record TradeStatistics(
        int totalTradesTaken,
        int tradesWon,
        int tradesLost,
        double winrate,
        int tradesBreakeven,
        int buyTrades,
        int sellTrades,
        double buyTradesPct,
        double sellTradesPct,
        Map<TradingSession, Session> sessionStats,
        double totalProfit,
        double totalLoss,
        double netProfit,
        double averageProfit,
        double averageLoss,
        double largestWin,
        double largestLoss,
        double profitFactor,
        double sharpeRatio,
        double sortinoRatio,
        double maxDrawdown,
        double maxDrawdownPct,
        double averageTrade,
        double expectancy,
        int consecutiveWins,
        int consecutiveLosses,
        double riskRewardRatio,
        Map<String, Double> dailyWinRates,
        Map<String, SymbolStatistics> symbolBreakdown
) {}
