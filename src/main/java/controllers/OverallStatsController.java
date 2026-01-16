package controllers;

import data_structures.Trade;
import models.OverallStatsModel;
import models.TradeDataModel;
import data_structures.TradeStatistics;
import services.TradeStatisticsService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class OverallStatsController implements PropertyChangeListener {
    private final OverallStatsModel statsModel;
    private final TradeStatisticsService statisticsService;

    public OverallStatsController(OverallStatsModel statsModel, TradeDataModel dataModel) {
        this.statsModel = statsModel;
        this.statisticsService = new TradeStatisticsService();
        dataModel.addPropertyChangeListener(TradeDataModel.TRADES_PROPERTY, this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TradeDataModel.TRADES_PROPERTY.equals(evt.getPropertyName())) {
            @SuppressWarnings("unchecked")
            List<Trade> trades = (List<Trade>) evt.getNewValue();
            TradeStatistics stats = statisticsService.calculateStats(trades);
            statsModel.updateFrom(stats);
        }
    }

    public OverallStatsModel getStatsModel() {
        return statsModel;
    }
}
