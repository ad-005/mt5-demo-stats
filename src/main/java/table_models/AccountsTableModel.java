package table_models;

import constants.AccountType;
import data.MockDataFactory;
import data_structures.Account;
import data_structures.Trade;

import javax.swing.table.AbstractTableModel;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public class AccountsTableModel extends AbstractTableModel {
    public enum TradeColumn {
        NAME("Name", String.class, Account::getName),
        TYPE("Type", String.class, Account::getTypeName),
        SERVER("Server", String.class, Account::getServer),
        LOGIN("Login", String.class, Account::getLogin),
        PASSWORD("Password", String.class, Account::getPassword),
        INVESTOR("Investor", String.class, Account::getInvestor);

        private final String name;
        private final Class<?> type;
        private final Function<Account, Object> extractor;

        TradeColumn(String name, Class<?> type, Function<Account, Object> extractor) {
            this.name = name;
            this.type = type;
            this.extractor = extractor;
        }

        public Object getValue(Account account) {
            return extractor.apply(account);
        }
    }

    private final TradeColumn[] COLUMNS = TradeColumn.values();
    private List<Account> accounts = new ArrayList<>();

    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return COLUMNS[columnIndex].getValue(accounts.get(rowIndex));
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column].name;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return COLUMNS[column].type;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        fireTableDataChanged();
    }
}
