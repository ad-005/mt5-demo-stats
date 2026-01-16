package services;

import data_structures.Account;

import java.util.List;

public class AccountManagementService {
    private final AccountFetchingService fetchingService;

    public AccountManagementService(AccountFetchingService fetchingService) {
        this.fetchingService = fetchingService;
    }

    public List<Account> getAccounts() {
        return fetchingService.loadAccounts();
    }

    public void addAccount(Account account) {
        List<Account> accounts = getAccounts();

        boolean exists = accounts.stream().anyMatch(acc -> acc.getLogin().equals(account.getLogin()));

        if (exists) {
            throw new IllegalArgumentException("Account with Login \"" + account.getLogin() + "\" already exists.");
        }

        accounts.add(account);
        fetchingService.saveAccounts(accounts);
    }

}
