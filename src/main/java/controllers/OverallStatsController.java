package controllers;

import data_structures.Account;
import data_structures.Trade;
import models.AccountSelectionModel;
import models.OverallStatsModel;
import models.TradeDataModel;
import data_structures.TradeStatistics;
import services.StatsCacheService;
import services.TradeStatisticsService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OverallStatsController implements PropertyChangeListener {
    private final OverallStatsModel statsModel;
    private final TradeDataModel tradeDataModel;
    private final TradeStatisticsService statisticsService;
    private final StatsCacheService statsCacheService;
    private final AccountSelectionModel accountSelectionModel;
    private TradeStatistics currentStatistics;

    public OverallStatsController(OverallStatsModel statsModel, TradeDataModel dataModel, AccountSelectionModel accountSelectionModel) {
        this.statsModel = statsModel;
        this.tradeDataModel = dataModel;
        this.statisticsService = new TradeStatisticsService();
        this.statsCacheService = new StatsCacheService();
        this.accountSelectionModel = accountSelectionModel;
        dataModel.addPropertyChangeListener(TradeDataModel.TRADES_PROPERTY, this);
        accountSelectionModel.addPropertyChangeListener(AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TradeDataModel.TRADES_PROPERTY.equals(evt.getPropertyName()) ||
            AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY.equals(evt.getPropertyName())) {
            refreshStatistics();
        }
    }

    public void refreshStatistics() {
        refreshCacheFromLoadedTrades();
        TradeStatistics stats = accountSelectionModel.isAllAccountsSelected()
                ? getAllAccountsAggregatedStats()
                : getSelectedAccountStats();
        currentStatistics = stats;
        statsModel.updateFrom(stats);
    }

    private TradeStatistics getSelectedAccountStats() {
        List<Trade> allTrades = tradeDataModel.getTrades();
        Account selected = accountSelectionModel.getSelectedAccount();
        if (selected == null) {
            return getAllAccountsAggregatedStats();
        }
        List<Trade> selectedTrades = allTrades.stream()
                .filter(trade -> trade.getAccountLogin().equals(selected.getLogin()))
                .toList();
        TradeStatistics stats = statisticsService.calculateStats(selectedTrades);
        statsCacheService.upsert(selected.getLogin(), stats);
        return stats;
    }

    private TradeStatistics getAllAccountsAggregatedStats() {
        Set<String> activeLogins = accountSelectionModel.getAccounts().stream()
                .map(Account::getLogin)
                .collect(Collectors.toSet());

        List<Trade> activeTrades = tradeDataModel.getTrades().stream()
                .filter(trade -> trade.getAccountLogin() != null && !trade.getAccountLogin().isBlank())
                .filter(trade -> activeLogins.contains(trade.getAccountLogin()))
                .toList();
        return statisticsService.calculateStats(activeTrades);
    }

    private void refreshCacheFromLoadedTrades() {
        Set<String> activeLogins = accountSelectionModel.getAccounts().stream()
                .map(Account::getLogin)
                .collect(Collectors.toSet());

        Map<String, List<Trade>> tradesByLogin = tradeDataModel.getTrades().stream()
                .filter(trade -> trade.getAccountLogin() != null && !trade.getAccountLogin().isBlank())
                .filter(trade -> activeLogins.contains(trade.getAccountLogin()))
                .collect(Collectors.groupingBy(Trade::getAccountLogin));

        for (Map.Entry<String, List<Trade>> entry : tradesByLogin.entrySet()) {
            TradeStatistics stats = statisticsService.calculateStats(entry.getValue());
            statsCacheService.upsert(entry.getKey(), stats);
        }
    }

    public OverallStatsModel getStatsModel() {
        return statsModel;
    }

    public TradeStatistics getCurrentStatistics() {
        return currentStatistics;
    }
}
