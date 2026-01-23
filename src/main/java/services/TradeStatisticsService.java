package services;

import constants.TradingSession;
import constants.TradeType;
import data_structures.Session;
import data_structures.Trade;
import data_structures.TradeStatistics;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.stream.Collectors;


import java.util.Map;
import java.util.List;

public class TradeStatisticsService {

    public TradeStatistics calculateStats(List<Trade> trades) {
        if (trades == null || trades.isEmpty()) {
            return new TradeStatistics(0, 0, 0, 0.0, 0, 0, 0, 0.0, 0.0, Map.of());
        }

        int totalTrades = trades.size();
        int tradesWon = (int) trades.stream().filter(t -> t.getProfit() > 0).count();
        int tradesLost = (int) trades.stream().filter(t -> t.getProfit() < 0).count();
        double winrate = tradesWon * 100.0 / totalTrades;
        int tradesBreakeven = (int) trades.stream().filter(t -> t.getProfit() == 0).count();
        int buyTrades = (int) trades.stream().filter(t -> t.getType() == TradeType.BUY).count();
        int sellTrades = (int) trades.stream().filter(t -> t.getType() == TradeType.SELL).count();
        double sellTradesPct = ((double) sellTrades / totalTrades) * 100;
        double buyTradesPct = ((double) buyTrades / totalTrades) * 100;
        Map<TradingSession, Session> sessionStats = calculateSessionStats(trades);

        return new TradeStatistics(totalTrades, tradesWon, tradesLost, winrate, tradesBreakeven, buyTrades, sellTrades, buyTradesPct, sellTradesPct, sessionStats);
    }

    private Map<TradingSession, Session> calculateSessionStats(List<Trade> trades) {
        Map<TradingSession, List<Trade>> tradesBySession = trades.stream().collect(Collectors.groupingBy(this::determineSession));

        Map<TradingSession, Session> result = new HashMap<>();
        for (var entry : tradesBySession.entrySet()) {
            List<Trade> sessionTrades = entry.getValue();
            int totalTrades = sessionTrades.size();
            int tradesWon = (int) sessionTrades.stream().filter(t -> t.getProfit() > 0).count();
            int tradesLost = (int) sessionTrades.stream().filter(t -> t.getProfit() < 0).count();
            double winrate = totalTrades > 0 ? (double) (tradesWon * 100) / totalTrades : 0.0;
            result.put(entry.getKey(), new Session(totalTrades, tradesWon, tradesLost, winrate));
        }

        return result;
    }

    public TradingSession determineSession(Trade trade) {
        if (trade.getOpenTime() == null || trade.getBrokerTimeZone() == null) {
            return TradingSession.ASIAN;
        }
        ZonedDateTime tradeTimeUTC = trade.getOpenTime().atZone(trade.getBrokerTimeZone()).withZoneSameInstant(ZoneId.of("UTC"));

        LocalTime utcTime = tradeTimeUTC.toLocalTime();

        if (utcTime.isBefore(LocalTime.of(7, 0))) {
            return TradingSession.ASIAN;
        } else if (utcTime.isBefore(LocalTime.of(12, 0))) {
            return TradingSession.LONDON;
        } else if (utcTime.isBefore(LocalTime.of(16, 0))) {
            return TradingSession.LONDON;
        } else if (utcTime.isBefore(LocalTime.of(21, 0))) {
            return TradingSession.NEW_YORK;
        } else {
            return TradingSession.SYDNEY;
        }
    }
}
