package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import data_structures.CachedTradeStatistics;
import data_structures.TradeStatistics;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatsCacheService {
    private static final Path CACHE_PATH = Path.of(System.getProperty("user.home"),
            ".tradingapp_test",
            "stats_cache.json");

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public StatsCacheService() {
        try {
            Files.createDirectories(CACHE_PATH.getParent());
        } catch (IOException e) {
            System.err.println("Failed to create stats cache directory: " + e.getMessage());
        }
    }

    public synchronized Map<String, TradeStatistics> loadAll() {
        Map<String, CachedTradeStatistics> cached = loadRaw();
        Map<String, TradeStatistics> result = new LinkedHashMap<>();

        for (Map.Entry<String, CachedTradeStatistics> entry : cached.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            result.put(entry.getKey(), entry.getValue().toTradeStatistics());
        }

        return result;
    }

    public synchronized void upsert(String login, TradeStatistics stats) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Account login cannot be empty.");
        }
        if (stats == null) {
            throw new IllegalArgumentException("Trade statistics cannot be null.");
        }

        Map<String, CachedTradeStatistics> cache = loadRaw();
        cache.put(login, new CachedTradeStatistics(stats));
        saveRaw(cache);
    }

    public synchronized void remove(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Account login cannot be empty.");
        }

        Map<String, CachedTradeStatistics> cache = loadRaw();
        cache.remove(login);
        saveRaw(cache);
    }

    private Map<String, CachedTradeStatistics> loadRaw() {
        if (!Files.exists(CACHE_PATH)) {
            return new LinkedHashMap<>();
        }

        try {
            String json = Files.readString(CACHE_PATH);
            Type type = new TypeToken<Map<String, CachedTradeStatistics>>(){}.getType();
            Map<String, CachedTradeStatistics> map = GSON.fromJson(json, type);
            return map != null ? new LinkedHashMap<>(map) : new LinkedHashMap<>();
        } catch (IOException e) {
            System.err.println("Failed to read stats cache: " + e.getMessage());
            return new LinkedHashMap<>();
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid stats cache JSON format: " + e.getMessage());
            return new LinkedHashMap<>();
        }
    }

    private void saveRaw(Map<String, CachedTradeStatistics> cache) {
        if (cache.isEmpty()) {
            try {
                Files.deleteIfExists(CACHE_PATH);
            } catch (IOException e) {
                System.err.println("Failed to delete empty stats cache file: " + e.getMessage());
            }
            return;
        }

        try (Writer writer = Files.newBufferedWriter(CACHE_PATH)) {
            GSON.toJson(cache, writer);
        } catch (IOException e) {
            System.err.println("Failed to save stats cache: " + e.getMessage());
        }
    }
}
