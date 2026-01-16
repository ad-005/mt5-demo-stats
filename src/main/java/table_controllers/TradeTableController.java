package table_controllers;

import data_structures.Trade;
import gui_elements.components.panels.SearchFieldPanel;
import gui_elements.tables.TradeTable;
import models.TradeDataModel;
import table_models.TradeTableModel;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.time.LocalDateTime;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TradeTableController implements PropertyChangeListener {
    private final JTable tablePanel;
    private final TradeTableModel tableModel;
    private final TradeDataModel tradeDataModel;
    private final SearchFieldPanel filterPanel;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final String DATE_PLACEHOLDER = "DD-MM-YYYY";

    // Current filter state
    private LocalDate currentStartDate = null;
    private LocalDate currentEndDate = null;

    public TradeTableController(JTable tablePanel, TradeTableModel tableModel,
                                TradeDataModel tradeDataModel, SearchFieldPanel filterPanel) {
        this.tablePanel = tablePanel;
        this.tableModel = tableModel;
        this.tradeDataModel = tradeDataModel;
        this.filterPanel = filterPanel;

        tradeDataModel.addPropertyChangeListener(TradeDataModel.TRADES_PROPERTY, this);

        initializeFilterListeners();

        applyFilters();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (TradeDataModel.TRADES_PROPERTY.equals(event.getPropertyName())) {
            applyFilters();
        }
    }

    private void initializeFilterListeners() {
        filterPanel.getSearchByDateButton().addActionListener(e -> applyFilters());
    }

    // Apply all used filters
    private void applyFilters() {
        List<Trade> trades = tradeDataModel.getTrades();
        currentStartDate = getDateFromField(filterPanel.getStartDateField());
        currentEndDate = getDateFromField(filterPanel.getEndDateField());

        List<Trade> filteredTrades = trades.stream().filter(this::matchesDateRangeFilter).toList();
        tableModel.setTrades(filteredTrades);
        filterPanel.setTradesShownText(filteredTrades.size(), trades.size());
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
