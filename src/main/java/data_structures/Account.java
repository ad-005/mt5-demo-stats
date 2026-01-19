package data_structures;

import constants.AccountType;

public class Account {
    private String name;
    private AccountType type;
    private String server;
    private String login;
    private String password;
    private String investor;

    // Constructor
    public Account(String name, AccountType type, String server, String login, String password, String investor) {
        this.name = name;
        this.type = type;
        this.server = server;
        this.login = login;
        this.password = password;
        this.investor = investor;
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
    // END GETTERS
}
