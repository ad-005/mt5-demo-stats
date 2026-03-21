package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import data_structures.LocalDateTimeAdapter;
import data_structures.ReportSnapshot;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportFetchingService {
    private static final Path REPORTS_PATH = Path.of(System.getProperty("user.home"),
            ".tradingapp_test",
            "reports.json");

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public ReportFetchingService() {
        try {
            Files.createDirectories(REPORTS_PATH.getParent());
        } catch (IOException e) {
            System.err.println("Failed to create reports directory: " + e.getMessage());
        }
    }

    public synchronized List<ReportSnapshot> loadReports() {
        if (!Files.exists(REPORTS_PATH)) {
            return new ArrayList<>();
        }

        try {
            String json = Files.readString(REPORTS_PATH);
            Type type = new TypeToken<List<ReportSnapshot>>() {
            }.getType();
            List<ReportSnapshot> reports = GSON.fromJson(json, type);
            return reports != null ? new ArrayList<>(reports) : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Failed to read reports: " + e.getMessage());
            return new ArrayList<>();
        } catch (JsonSyntaxException e) {
            System.err.println("Invalid reports JSON format: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public synchronized void saveReports(List<ReportSnapshot> reports) {
        if (reports == null || reports.isEmpty()) {
            try {
                Files.deleteIfExists(REPORTS_PATH);
            } catch (IOException e) {
                System.err.println("Failed to delete empty reports file: " + e.getMessage());
            }
            return;
        }

        try (Writer writer = Files.newBufferedWriter(REPORTS_PATH)) {
            GSON.toJson(reports, writer);
        } catch (IOException e) {
            System.err.println("Failed to save reports: " + e.getMessage());
        }
    }
}
