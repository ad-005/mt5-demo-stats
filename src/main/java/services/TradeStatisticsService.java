package services;

import constants.TradingSession;
import constants.TradeType;
import data_structures.Session;
import data_structures.SymbolStatistics;
import data_structures.Trade;
import data_structures.TradeStatistics;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TradeStatisticsService {

    private static final List<String> DAY_ORDER = List.of(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );

    public TradeStatistics calculateStats(List<Trade> trades) {
        if (trades == null || trades.isEmpty()) {
            return emptyStatistics();
        }

        int totalTrades = trades.size();

        List<Double> netProfits = trades.stream()
                .map(this::netProfit)
                .toList();

        int tradesWon = (int) netProfits.stream().filter(p -> p > 0).count();
        int tradesLost = (int) netProfits.stream().filter(p -> p < 0).count();
        int tradesBreakeven = totalTrades - tradesWon - tradesLost;

        int buyTrades = (int) trades.stream().filter(t -> t.getType() == TradeType.BUY).count();
        int sellTrades = (int) trades.stream().filter(t -> t.getType() == TradeType.SELL).count();
        double winrate = percentage(tradesWon, totalTrades);
        double buyTradesPct = percentage(buyTrades, totalTrades);
        double sellTradesPct = percentage(sellTrades, totalTrades);

        List<Double> winners = netProfits.stream().filter(p -> p > 0).toList();
        List<Double> losers = netProfits.stream().filter(p -> p <= 0).toList();
        double totalProfit = round2(winners.stream().mapToDouble(Double::doubleValue).sum());
        double totalLoss = round2(Math.abs(losers.stream().mapToDouble(Double::doubleValue).sum()));
        double netProfit = round2(netProfits.stream().mapToDouble(Double::doubleValue).sum());
        double averageProfit = round2(winners.isEmpty() ? 0.0 : totalProfit / winners.size());
        double averageLoss = round2(losers.isEmpty() ? 0.0 : totalLoss / losers.size());
        double largestWin = round2(netProfits.stream().mapToDouble(Double::doubleValue).max().orElse(0.0));
        double largestLoss = round2(netProfits.stream().mapToDouble(Double::doubleValue).min().orElse(0.0));
        double profitFactor = round2(totalLoss > 0 ? totalProfit / totalLoss : 0.0);

        double[] returns = netProfits.stream().mapToDouble(Double::doubleValue).toArray();
        double sharpeRatio = round2(calculateSharpe(returns));
        double sortinoRatio = round2(calculateSortino(returns));

        DrawdownStats drawdown = calculateDrawdown(returns);
        int consecutiveWins = calculateConsecutiveStreak(netProfits, true);
        int consecutiveLosses = calculateConsecutiveStreak(netProfits, false);
        double averageTrade = round2(netProfit / totalTrades);
        double expectancy = round2((winrate / 100.0 * averageProfit) - ((1 - winrate / 100.0) * averageLoss));
        double riskRewardRatio = round2(averageLoss > 0 ? averageProfit / averageLoss : 0.0);

        Map<TradingSession, Session> sessionStats = calculateSessionStats(trades);
        Map<String, Double> dailyWinRates = calculateDailyWinRates(trades);
        Map<String, SymbolStatistics> symbolBreakdown = calculateSymbolBreakdown(trades);

        return new TradeStatistics(
                totalTrades,
                tradesWon,
                tradesLost,
                round2(winrate),
                tradesBreakeven,
                buyTrades,
                sellTrades,
                round2(buyTradesPct),
                round2(sellTradesPct),
                sessionStats,
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
                round2(drawdown.maxDrawdown),
                round2(drawdown.maxDrawdownPct),
                averageTrade,
                expectancy,
                consecutiveWins,
                consecutiveLosses,
                riskRewardRatio,
                dailyWinRates,
                symbolBreakdown
        );
    }

    private Map<TradingSession, Session> calculateSessionStats(List<Trade> trades) {
        Map<TradingSession, List<Trade>> tradesBySession = trades.stream()
                .collect(Collectors.groupingBy(this::determineSession));

        Map<TradingSession, Session> result = new EnumMap<>(TradingSession.class);
        for (TradingSession session : TradingSession.values()) {
            List<Trade> sessionTrades = tradesBySession.getOrDefault(session, List.of());
            int total = sessionTrades.size();
            int wins = (int) sessionTrades.stream().map(this::netProfit).filter(p -> p > 0).count();
            int losses = (int) sessionTrades.stream().map(this::netProfit).filter(p -> p < 0).count();
            double winrate = total > 0 ? (wins * 100.0) / total : 0.0;
            result.put(session, new Session(total, wins, losses, round2(winrate)));
        }
        return result;
    }

    public TradingSession determineSession(Trade trade) {
        if (trade.getOpenTime() == null || trade.getBrokerTimeZone() == null) {
            return TradingSession.ASIAN;
        }
        ZonedDateTime tradeTimeUTC = trade.getOpenTime()
                .atZone(trade.getBrokerTimeZone())
                .withZoneSameInstant(ZoneId.of("UTC"));

        LocalTime utcTime = tradeTimeUTC.toLocalTime();

        if (utcTime.isBefore(LocalTime.of(9, 0))) {
            return TradingSession.ASIAN;
        } else if (utcTime.isBefore(LocalTime.of(13, 0))) {
            return TradingSession.LONDON;
        } else if (utcTime.isBefore(LocalTime.of(21, 0))) {
            // Hybrid mapping decision: NY + NY PM are merged in current UI.
            return TradingSession.NEW_YORK;
        } else {
            return TradingSession.SYDNEY;
        }
    }

    public TradeStatistics aggregateStatistics(List<TradeStatistics> statsList) {
        if (statsList == null || statsList.isEmpty()) {
            return emptyStatistics();
        }

        int totalTrades = 0;
        int tradesWon = 0;
        int tradesLost = 0;
        int tradesBreakeven = 0;
        int buyTrades = 0;
        int sellTrades = 0;
        double totalProfit = 0.0;
        double totalLoss = 0.0;
        double netProfit = 0.0;

        Map<TradingSession, SessionAccumulator> sessionAccumulators = new EnumMap<>(TradingSession.class);
        for (TradingSession session : TradingSession.values()) {
            sessionAccumulators.put(session, new SessionAccumulator());
        }

        Map<String, DayAccumulator> dayAccumulators = new LinkedHashMap<>();
        for (String day : DAY_ORDER) {
            dayAccumulators.put(day, new DayAccumulator());
        }

        Map<String, SymbolAccumulator> symbolAccumulators = new LinkedHashMap<>();

        for (TradeStatistics stats : statsList) {
            if (stats == null) {
                continue;
            }

            totalTrades += stats.totalTradesTaken();
            tradesWon += stats.tradesWon();
            tradesLost += stats.tradesLost();
            tradesBreakeven += stats.tradesBreakeven();
            buyTrades += stats.buyTrades();
            sellTrades += stats.sellTrades();

            totalProfit += stats.totalProfit();
            totalLoss += stats.totalLoss();
            netProfit += stats.netProfit();

            for (Map.Entry<TradingSession, Session> entry : stats.sessionStats().entrySet()) {
                Session session = entry.getValue();
                SessionAccumulator acc = sessionAccumulators.computeIfAbsent(entry.getKey(), ignored -> new SessionAccumulator());
                acc.totalTrades += session.getTotalTrades();
                acc.wins += session.getWins();
                acc.losses += session.getLosses();
            }

            for (Map.Entry<String, Double> entry : stats.dailyWinRates().entrySet()) {
                String day = entry.getKey();
                if (!dayAccumulators.containsKey(day)) {
                    dayAccumulators.put(day, new DayAccumulator());
                }
                DayAccumulator acc = dayAccumulators.get(day);
                int inferredTrades = inferTradeCountFromPercent(entry.getValue());
                int inferredWins = inferWins(inferredTrades, entry.getValue());
                acc.totalTrades += inferredTrades;
                acc.wins += inferredWins;
            }

            for (Map.Entry<String, SymbolStatistics> entry : stats.symbolBreakdown().entrySet()) {
                SymbolStatistics symbolStats = entry.getValue();
                SymbolAccumulator acc = symbolAccumulators.computeIfAbsent(entry.getKey(), ignored -> new SymbolAccumulator());
                acc.trades += symbolStats.trades();
                acc.pnl += symbolStats.pnl();
                acc.wins += inferWins(symbolStats.trades(), symbolStats.winRate());
            }
        }

        if (totalTrades == 0) {
            return emptyStatistics();
        }

        double winrate = percentage(tradesWon, totalTrades);
        double buyTradesPct = percentage(buyTrades, totalTrades);
        double sellTradesPct = percentage(sellTrades, totalTrades);

        double averageProfit = tradesWon > 0 ? totalProfit / tradesWon : 0.0;
        int nonWinningTrades = tradesLost + tradesBreakeven;
        double averageLoss = nonWinningTrades > 0 ? totalLoss / nonWinningTrades : 0.0;
        double profitFactor = totalLoss > 0 ? totalProfit / totalLoss : 0.0;
        double averageTrade = netProfit / totalTrades;
        double expectancy = (winrate / 100.0 * averageProfit) - ((1 - winrate / 100.0) * averageLoss);
        double riskRewardRatio = averageLoss > 0 ? averageProfit / averageLoss : 0.0;

        Map<TradingSession, Session> sessionStats = new EnumMap<>(TradingSession.class);
        for (Map.Entry<TradingSession, SessionAccumulator> entry : sessionAccumulators.entrySet()) {
            SessionAccumulator acc = entry.getValue();
            double sessionWinrate = acc.totalTrades > 0 ? acc.wins * 100.0 / acc.totalTrades : 0.0;
            sessionStats.put(entry.getKey(), new Session(acc.totalTrades, acc.wins, acc.losses, round2(sessionWinrate)));
        }

        Map<String, Double> dailyWinRates = new LinkedHashMap<>();
        for (String day : DAY_ORDER) {
            DayAccumulator acc = dayAccumulators.get(day);
            if (acc == null || acc.totalTrades == 0) {
                continue;
            }
            dailyWinRates.put(day, round2(acc.wins * 100.0 / acc.totalTrades));
        }

        Map<String, SymbolStatistics> symbolBreakdown = new LinkedHashMap<>();
        for (Map.Entry<String, SymbolAccumulator> entry : symbolAccumulators.entrySet()) {
            SymbolAccumulator acc = entry.getValue();
            double symbolWinrate = acc.trades > 0 ? acc.wins * 100.0 / acc.trades : 0.0;
            symbolBreakdown.put(entry.getKey(), new SymbolStatistics(acc.trades, round2(symbolWinrate), round2(acc.pnl)));
        }

        return new TradeStatistics(
                totalTrades,
                tradesWon,
                tradesLost,
                round2(winrate),
                tradesBreakeven,
                buyTrades,
                sellTrades,
                round2(buyTradesPct),
                round2(sellTradesPct),
                sessionStats,
                round2(totalProfit),
                round2(totalLoss),
                round2(netProfit),
                round2(averageProfit),
                round2(averageLoss),
                0.0,
                0.0,
                round2(profitFactor),
                0.0,
                0.0,
                0.0,
                0.0,
                round2(averageTrade),
                round2(expectancy),
                0,
                0,
                round2(riskRewardRatio),
                dailyWinRates,
                symbolBreakdown
        );
    }

    private Map<String, Double> calculateDailyWinRates(List<Trade> trades) {
        Map<String, List<Double>> pnlsByDay = new LinkedHashMap<>();
        for (String day : DAY_ORDER) {
            pnlsByDay.put(day, new ArrayList<>());
        }

        for (Trade trade : trades) {
            if (trade.getOpenTime() == null) {
                continue;
            }
            DayOfWeek dayOfWeek;
            if (trade.getBrokerTimeZone() != null) {
                dayOfWeek = trade.getOpenTime()
                        .atZone(trade.getBrokerTimeZone())
                        .withZoneSameInstant(ZoneId.of("UTC"))
                        .getDayOfWeek();
            } else {
                dayOfWeek = trade.getOpenTime().getDayOfWeek();
            }
            String day = dayOfWeek != null ? toDayLabel(dayOfWeek) : null;
            if (day == null) {
                continue;
            }
            pnlsByDay.get(day).add(netProfit(trade));
        }

        Map<String, Double> result = new LinkedHashMap<>();
        for (String day : DAY_ORDER) {
            List<Double> pnls = pnlsByDay.get(day);
            if (pnls == null || pnls.isEmpty()) {
                continue;
            }
            int wins = (int) pnls.stream().filter(p -> p > 0).count();
            result.put(day, round2((wins * 100.0) / pnls.size()));
        }
        return result;
    }

    private Map<String, SymbolStatistics> calculateSymbolBreakdown(List<Trade> trades) {
        Map<String, SymbolAccumulator> accumulators = new LinkedHashMap<>();
        for (Trade trade : trades) {
            String symbol = trade.getSymbol() != null ? trade.getSymbol().name() : "UNKNOWN";
            SymbolAccumulator acc = accumulators.computeIfAbsent(symbol, ignored -> new SymbolAccumulator());
            double pnl = netProfit(trade);
            acc.trades++;
            acc.pnl += pnl;
            if (pnl > 0) {
                acc.wins++;
            }
        }

        Map<String, SymbolStatistics> result = new LinkedHashMap<>();
        for (Map.Entry<String, SymbolAccumulator> entry : accumulators.entrySet()) {
            SymbolAccumulator acc = entry.getValue();
            double winrate = acc.trades > 0 ? (acc.wins * 100.0) / acc.trades : 0.0;
            result.put(entry.getKey(), new SymbolStatistics(acc.trades, round2(winrate), round2(acc.pnl)));
        }
        return result;
    }

    private DrawdownStats calculateDrawdown(double[] returns) {
        if (returns.length == 0) {
            return new DrawdownStats(0.0, 0.0);
        }
        double cumulative = 0.0;
        double runningMax = 0.0;
        double maxDrawdown = 0.0;
        for (double value : returns) {
            cumulative += value;
            runningMax = Math.max(runningMax, cumulative);
            maxDrawdown = Math.max(maxDrawdown, runningMax - cumulative);
        }
        double maxDrawdownPct = runningMax > 0 ? (maxDrawdown / runningMax) * 100.0 : 0.0;
        return new DrawdownStats(maxDrawdown, maxDrawdownPct);
    }

    private double calculateSharpe(double[] returns) {
        if (returns.length <= 1) {
            return 0.0;
        }
        double std = stdDev(returns);
        if (std == 0.0) {
            return 0.0;
        }
        return (mean(returns) / std) * Math.sqrt(252);
    }

    private double calculateSortino(double[] returns) {
        if (returns.length <= 1) {
            return 0.0;
        }
        double[] downside = java.util.Arrays.stream(returns).filter(v -> v < 0).toArray();
        if (downside.length == 0) {
            return 0.0;
        }
        double downsideStd = stdDev(downside);
        if (downsideStd == 0.0) {
            return 0.0;
        }
        return (mean(returns) / downsideStd) * Math.sqrt(252);
    }

    private int calculateConsecutiveStreak(List<Double> profits, boolean wins) {
        int maxStreak = 0;
        int current = 0;
        for (double value : profits) {
            boolean matches = wins ? value > 0 : value <= 0;
            if (matches) {
                current++;
                maxStreak = Math.max(maxStreak, current);
            } else {
                current = 0;
            }
        }
        return maxStreak;
    }

    private double mean(double[] values) {
        if (values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private double stdDev(double[] values) {
        if (values.length <= 1) {
            return 0.0;
        }
        double avg = mean(values);
        double variance = 0.0;
        for (double value : values) {
            double delta = value - avg;
            variance += delta * delta;
        }
        variance /= values.length;
        return Math.sqrt(variance);
    }

    private double netProfit(Trade trade) {
        return trade.getProfit() + trade.getCommission() + trade.getSwap();
    }

    private TradeStatistics emptyStatistics() {
        Map<TradingSession, Session> emptySessions = new EnumMap<>(TradingSession.class);
        for (TradingSession session : TradingSession.values()) {
            emptySessions.put(session, new Session(0, 0, 0, 0.0));
        }

        return new TradeStatistics(
                0, 0, 0, 0.0, 0, 0, 0, 0.0, 0.0,
                emptySessions,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0, 0, 0.0,
                Map.of(),
                Map.of()
        );
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private double percentage(int part, int total) {
        if (total <= 0) {
            return 0.0;
        }
        return part * 100.0 / total;
    }

    private String toDayLabel(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "Monday";
            case TUESDAY -> "Tuesday";
            case WEDNESDAY -> "Wednesday";
            case THURSDAY -> "Thursday";
            case FRIDAY -> "Friday";
            case SATURDAY -> "Saturday";
            case SUNDAY -> "Sunday";
        };
    }

    private int inferTradeCountFromPercent(double percent) {
        if (percent <= 0.0) {
            return 0;
        }
        return 1;
    }

    private int inferWins(int trades, double winRate) {
        if (trades <= 0) {
            return 0;
        }
        return (int) Math.round((winRate / 100.0) * trades);
    }

    private static final class SessionAccumulator {
        int totalTrades;
        int wins;
        int losses;
    }

    private static final class DayAccumulator {
        int totalTrades;
        int wins;
    }

    private static final class SymbolAccumulator {
        int trades;
        int wins;
        double pnl;
    }

    private static final class DrawdownStats {
        private final double maxDrawdown;
        private final double maxDrawdownPct;

        private DrawdownStats(double maxDrawdown, double maxDrawdownPct) {
            this.maxDrawdown = maxDrawdown;
            this.maxDrawdownPct = maxDrawdownPct;
        }
    }
}
