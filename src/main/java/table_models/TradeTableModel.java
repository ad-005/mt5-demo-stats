package table_models;

import data.MockDataFactory;
import data_structures.Trade;
import constants.TradeSymbol;
import constants.TradeType;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TradeTableModel extends AbstractTableModel {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public enum TradeColumn {

        OPEN_TIME("Open Time", String.class, t -> formatDateTime(t.getOpenTime())),
        SYMBOL("Symbol", TradeSymbol.class, Trade::getSymbol),
        TICKET("Ticket", String.class, Trade::getTicket),
        TYPE("Type", TradeType.class, Trade::getType),
        VOLUME("Volume", Double.class, Trade::getVolume),
        OPEN_PRICE("Open Price", Double.class, Trade::getOpenPrice),
        STOP_LOSS("S/L", Double.class, Trade::getStopLoss),
        TAKE_PROFIT("T/P", Double.class, Trade::getTakeProfit),
        CLOSE_TIME("Close Time", String.class, t -> formatDateTime(t.getCloseTime())),
        CLOSE_PRICE("Close Price", Double.class, Trade::getClosePrice),
        PROFIT("Profit", Double.class, Trade::getProfit),
        CHANGE("Change", Double.class, Trade::getChange);

        private final String name;
        private final Class<?> type;
        private final Function<Trade, Object> extractor;

        TradeColumn(String name, Class<?> type, Function<Trade, Object> extractor) {
            this.name = name;
            this.type = type;
            this.extractor = extractor;
        }

        public Object getValue(Trade trade) {
            return extractor.apply(trade);
        }
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

//    private final String[] columns = {"Open Time", "Symbol", "Ticket", "Type", "Volume", "Open Price", "S/L", "T/P",
//    "Close Time", "Close Price", "Profit", "Change"};
    private List<Trade> trades;
    private static final TradeColumn[] COLUMNS = TradeColumn.values();

    public TradeTableModel() {
        this.trades = new ArrayList<>();
    }

    // START SETTERS
    public void setTrades(List<Trade> trades) {
        this.trades = new ArrayList<>(trades);
        fireTableDataChanged();
    }
    // END SETTERS

    // START GETTERS
    public List<Trade> getTrades() {
        return new ArrayList<>(trades);
    }
    // END GETTERS

    @Override
    public int getRowCount() {
        return trades.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return COLUMNS[columnIndex].getValue(trades.get(rowIndex));
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column].name;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return COLUMNS[column].type;
    }
}
