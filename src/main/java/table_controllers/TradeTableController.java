package table_controllers;

import constants.TradingSession;
import data_structures.Trade;
import gui_elements.components.panels.SearchFieldPanel;
import models.AccountSelectionModel;
import models.SessionSelectionModel;
import models.TradeDataModel;
import services.TradeStatisticsService;
import table_models.TradeTableModel;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TradeTableController implements PropertyChangeListener {
    private final JTable tablePanel;
    private final TradeTableModel tableModel;
    private final TradeDataModel tradeDataModel;
    private final SearchFieldPanel filterPanel;
    private final AccountSelectionModel accountSelectionModel;
    private final SessionSelectionModel sessionSelectionModel;
    private final TradeStatisticsService tradeStatisticsService = new TradeStatisticsService();

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String DATE_PLACEHOLDER = "DD-MM-YYYY";

    // Current filter state
    private LocalDate currentStartDate = null;
    private LocalDate currentEndDate = null;

    public TradeTableController(JTable tablePanel, TradeTableModel tableModel,
                                TradeDataModel tradeDataModel, SearchFieldPanel filterPanel,
                                AccountSelectionModel accountSelectionModel, SessionSelectionModel sessionSelectionModel) {
        this.tablePanel = tablePanel;
        this.tableModel = tableModel;
        this.tradeDataModel = tradeDataModel;
        this.filterPanel = filterPanel;
        this.accountSelectionModel = accountSelectionModel;
        this.sessionSelectionModel = sessionSelectionModel;

        tradeDataModel.addPropertyChangeListener(TradeDataModel.TRADES_PROPERTY, this);
        accountSelectionModel.addPropertyChangeListener(AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY, this);
        sessionSelectionModel.addPropertyChangeListener(SessionSelectionModel.SESSION_SELECTED_PROPERTY, this);

        initializeFilterListeners();
        applyFilters();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (TradeDataModel.TRADES_PROPERTY.equals(event.getPropertyName()) ||
            AccountSelectionModel.ACCOUNT_SELECTED_PROPERTY.equals(event.getPropertyName()) ||
            SessionSelectionModel.SESSION_SELECTED_PROPERTY.equals(event.getPropertyName())) {
            applyFilters();
        }
    }

    private void initializeFilterListeners() {
        filterPanel.getSearchByDateButton().addActionListener(e -> applyFilters());
    }

    // Apply filtering
    private void applyFilters() {
        List<Trade> trades = tradeDataModel.getTrades();

        List<Trade> accountFiltered = filterByAccount(trades);

        currentStartDate = getDateFromField(filterPanel.getStartDateField());
        currentEndDate = getDateFromField(filterPanel.getEndDateField());

        List<Trade> dateFiltered = accountFiltered.stream().filter(this::matchesDateRangeFilter).toList();
        List<Trade> filteredTrades = filterBySession(dateFiltered);

        tableModel.setTrades(filteredTrades);
        filterPanel.setTradesShownText(filteredTrades.size(), trades.size());

    }

    // Helper method for returning a list matching the session
    private List<Trade> filterBySession(List<Trade> trades) {
        if (sessionSelectionModel.isAllSessionsSelected()) {
            return trades;
        }

        TradingSession selectedSession = sessionSelectionModel.getSelectedSession();
        return trades.stream().filter(trade -> tradeStatisticsService.determineSession(trade).equals(selectedSession)).toList();
    }

    // Helper method for returning a list matching the account
    private List<Trade> filterByAccount(List<Trade> trades) {
        if (accountSelectionModel.isAllAccountsSelected()) {
            return trades;
        }

        String selectedLogin = accountSelectionModel.getSelectedAccount().getLogin();
        return trades.stream().filter(trade -> trade.getAccountLogin().equals(selectedLogin)).toList();
    }

    private boolean matchesDateRangeFilter(Trade trade) {
        LocalDate tradeDate = trade.getOpenTime().toLocalDate();

        if (currentStartDate != null && tradeDate.isBefore(currentStartDate)) {
            return false;
        }

        if (currentEndDate != null && tradeDate.isAfter(currentEndDate)) {
            return false;
        }

        return true;
    }

    // Reset all filters
    public void resetFilters() {
        currentStartDate = null;
        currentEndDate = null;

        filterPanel.clearStartDateTextField();
        filterPanel.clearEndDateTextField();

        applyFilters();
    }

    // Helper method for parsing date from JTextField
    private LocalDate getDateFromField(JTextField dateField) {
        String text = dateField.getText();

        if (text.isEmpty() || DATE_PLACEHOLDER.equalsIgnoreCase(text)) {
            return null;
        }

        try {
            return LocalDate.parse(text, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(filterPanel, "Invalid date format.", "Date formatting error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
