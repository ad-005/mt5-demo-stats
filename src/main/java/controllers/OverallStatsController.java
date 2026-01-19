package controllers;

import data_structures.Account;
import data_structures.Trade;
import models.AccountSelectionModel;
import models.OverallStatsModel;
import models.TradeDataModel;
import data_structures.TradeStatistics;
import services.TradeStatisticsService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class OverallStatsController implements PropertyChangeListener {
    private final OverallStatsModel statsModel;
    private final TradeDataModel tradeDataModel;
    private final TradeStatisticsService statisticsService;
    private final AccountSelectionModel accountSelectionModel;

    public OverallStatsController(OverallStatsModel statsModel, TradeDataModel dataModel, AccountSelectionModel accountSelectionModel) {
        this.statsModel = statsModel;
        this.tradeDataModel = dataModel;
        this.statisticsService = new TradeStatisticsService();
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
        List<Trade> filteredTrades = getFilteredTrades();
        TradeStatistics stats = statisticsService.calculateStats(filteredTrades);
        statsModel.updateFrom(stats);
    }

    private List<Trade> getFilteredTrades() {
        List<Trade> allTrades = tradeDataModel.getTrades();

        if (accountSelectionModel.isAllAccountsSelected()) {
            return allTrades;
        }

        Account selected = accountSelectionModel.getSelectedAccount();
        return allTrades.stream().filter(trade -> trade.getAccountLogin().equals(selected.getLogin())).toList();
    }

    public OverallStatsModel getStatsModel() {
        return statsModel;
    }
}
