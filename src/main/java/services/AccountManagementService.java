package services;

import data_structures.Account;
import models.AccountSelectionModel;

import java.util.List;
import java.util.Optional;

public class AccountManagementService {
    private final AccountFetchingService fetchingService;
    private AccountSelectionModel accountSelectionModel;

    public AccountManagementService(AccountFetchingService fetchingService, AccountSelectionModel accountSelectionModel) {
        this.fetchingService = fetchingService;
        this.accountSelectionModel = accountSelectionModel;
    }

    public AccountManagementService(AccountFetchingService accountFetchingService) {
        this(accountFetchingService, null);
    }

    // Get all accounts
    public List<Account> getAccounts() {
        return fetchingService.loadAccounts();
    }

    // Add an account
    public void addAccount(Account account) {
        List<Account> accounts = getAccounts();

        boolean exists = accounts.stream().anyMatch(acc -> acc.getLogin().equals(account.getLogin()));

        if (exists) {
            throw new IllegalArgumentException("Account with Login \"" + account.getLogin() + "\" already exists.");
        }

        accounts.add(account);
        fetchingService.saveAccounts(accounts);
        accountSelectionModel.setAccounts(accounts);
    }

    // Remove an account
    public void removeAccount(String login) {
        List<Account> accounts = getAccounts();
        Account account = getAccountByLogin(accounts, login);

        boolean exists = accounts.stream().anyMatch(acc -> acc.getLogin().equals(account.getLogin()));

        if (!exists) {
            throw new IllegalArgumentException("Account with Login \"" + account.getLogin() + "\" doesn't exist.");
        }

        accounts.remove(account);
        fetchingService.saveAccounts(accounts);
        accountSelectionModel.setAccounts(accounts);
    }

    // Get an Account by its Login
    private Account getAccountByLogin(List<Account> accounts, String login) {
        return accounts.stream().filter(account -> account.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    public void setSelectionModel(AccountSelectionModel accountSelectionModel) {
        this.accountSelectionModel = accountSelectionModel;
    }
}
