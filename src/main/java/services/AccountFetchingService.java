package services;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import data_structures.Account;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountFetchingService {
    private static final Path ACCOUNTS_PATH = Path.of(System.getProperty("user.home"),
            ".tradingapp_test",
            "accounts.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public AccountFetchingService() {
        try {
            Files.createDirectories(ACCOUNTS_PATH.getParent());
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

            Type listType = new TypeToken<List<Account>>(){}.getType();
            List<Account> accounts = GSON.fromJson(jsonContent, listType);

            return accounts != null ? new ArrayList<>(accounts) : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Failed to read accounts file: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid JSON format: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveAccounts(List<Account> accounts) {
        try {
            Writer writer = Files.newBufferedWriter(ACCOUNTS_PATH);
            GSON.toJson(accounts, writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("Error saving account: " + e.getMessage());
        }
    }

}
