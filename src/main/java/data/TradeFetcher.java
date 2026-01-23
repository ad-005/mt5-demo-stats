package data;
import com.google.gson.reflect.TypeToken;
import data_structures.LocalDateTimeAdapter;
import data_structures.Trade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TradeFetcher {

    private final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public List<Trade> fetchTrades() {
        List<Trade> trades = new ArrayList<>();

        try {
            String projectRoot = System.getProperty("user.dir");
            String pythonExe = projectRoot + "/python_scripts/.venv/Scripts/python.exe";
            String scriptPath = projectRoot + "/python_scripts/fetch_deals.py";
            ProcessBuilder pb = new ProcessBuilder(pythonExe, scriptPath);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("{") && !line.contains("\"error\"")) {
                    Trade trade = GSON.fromJson(line, Trade.class);
                    if (trade != null && trade.getOpenTime() != null) {
                        trades.add(trade);
                    }
                }
            }

            process.waitFor();

            return trades;

        } catch (Exception e) {
            System.err.println("Error fetching trades: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
