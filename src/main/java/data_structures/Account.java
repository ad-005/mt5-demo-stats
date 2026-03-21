package data_structures;

import constants.AccountType;
import java.time.LocalDate;

public class Account {
    private String name;
    private AccountType type;
    private String server;
    private String login;
    private String password;
    private String investor;
    private String addedAt;

    // Constructor
    public Account(String name, AccountType type, String server, String login, String password, String investor) {
        this(name, type, server, login, password, investor, null);
    }

    public Account(String name, AccountType type, String server, String login, String password, String investor, String addedAt) {
        this.name = name;
        this.type = type;
        this.server = server;
        this.login = login;
        this.password = password;
        this.investor = investor;
        this.addedAt = normalizeAddedAt(addedAt);
    }

    private String normalizeAddedAt(String value) {
        if (value == null || value.isBlank()) {
            return LocalDate.now().toString();
        }
        return value.trim();
    }

    // START GETTERS
    public String getName() { return name; }
    public AccountType getType() { return type; }
    public String getTypeName() {
        return (getType() == null) ? "Undefined" : type.typeName;
    }
    public String getServer() { return server; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getInvestor() { return investor; }
    public String getAddedAt() { return addedAt; }
    // END GETTERS
}
