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
        Map<TradingSession, Session> sessionStats
) {}
