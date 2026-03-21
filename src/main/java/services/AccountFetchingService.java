package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import constants.AccountType;
import data_structures.Account;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountFetchingService {
    private static final Path ACCOUNTS_PATH = Path.of(System.getProperty("user.home"),
            ".tradingapp_test",
            "accounts.json");
    private static final String EMPTY_SECRET = "";

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public AccountFetchingService() {
        try {
            Files.createDirectories(ACCOUNTS_PATH.getParent());
            applyOwnerOnlyPermissions(ACCOUNTS_PATH.getParent(), true);
        } catch (IOException e) {
            System.err.println("Failed to create config directory: " + e.getMessage());
        }
    }

    public List<Account> loadAccounts() {
        if (!Files.exists(ACCOUNTS_PATH)) {
            return new ArrayList<>();
        }

        try {
            String jsonContent = Files.readString(ACCOUNTS_PATH);

            Type listType = new TypeToken<List<StoredAccount>>() {}.getType();
            List<StoredAccount> accounts = GSON.fromJson(jsonContent, listType);
            return mapStoredToAccounts(accounts);

        } catch (IOException e) {
            System.err.println("Failed to read accounts file: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON format: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAccounts(List<Account> accounts) {
        List<StoredAccount> storedAccounts = mapAccountsForStorage(accounts);
        try (Writer writer = Files.newBufferedWriter(
                ACCOUNTS_PATH,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
        )) {
            GSON.toJson(storedAccounts, writer);
            applyOwnerOnlyPermissions(ACCOUNTS_PATH, false);
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

    private List<Account> mapStoredToAccounts(List<StoredAccount> storedAccounts) {
        if (storedAccounts == null) {
            return new ArrayList<>();
        }

        List<Account> accounts = new ArrayList<>(storedAccounts.size());
        for (StoredAccount stored : storedAccounts) {
            if (stored == null) {
                continue;
            }
            accounts.add(new Account(
                    stored.name,
                    stored.type,
                    stored.server,
                    stored.login,
                    EMPTY_SECRET,
                    EMPTY_SECRET,
                    normalizeAddedAt(stored.addedAt)
            ));
        }
        return accounts;
    }

    private List<StoredAccount> mapAccountsForStorage(List<Account> accounts) {
        if (accounts == null) {
            return new ArrayList<>();
        }

        List<StoredAccount> stored = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            if (account == null) {
                continue;
            }
            stored.add(new StoredAccount(
                    account.getName(),
                    account.getType(),
                    account.getServer(),
                    account.getLogin(),
                    account.getAddedAt()
            ));
        }
        return stored;
    }

    private String normalizeAddedAt(String value) {
        if (value == null || value.isBlank()) {
            return LocalDate.now().toString();
        }
        return value.trim();
    }

    private void applyOwnerOnlyPermissions(Path path, boolean directory) {
        File file = path.toFile();
        if (directory) {
            file.setExecutable(true, true);
        } else {
            file.setExecutable(false, false);
        }
        file.setReadable(true, true);
        file.setWritable(true, true);
    }

    private static class StoredAccount {
        private String name;
        private AccountType type;
        private String server;
        private String login;
        private String addedAt;

        private StoredAccount(String name, AccountType type, String server, String login, String addedAt) {
            this.name = name;
            this.type = type;
            this.server = server;
            this.login = login;
            this.addedAt = addedAt;
        }
    }

}
