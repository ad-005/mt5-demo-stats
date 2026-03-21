# Copilot instructions for `demoanalysis_testing`

## Build, run, and test commands

- Install Python dependencies for MT5 trade fetching (from repo root):  
  `cd python_scripts && uv sync`

- Run the desktop app (Java Swing):  
  `mvn compile exec:java`

- Compile only (no tests):  
  `mvn -DskipTests compile`

- Run full Java tests (when present):  
  `mvn test`

- Run a single Java test class:  
  `mvn -Dtest=ClassNameTest test`

- Run a single Java test method:  
  `mvn -Dtest=ClassNameTest#methodName test`

Linting:

- No dedicated lint/checkstyle/spotbugs/pmd command is configured in `pom.xml`.

## High-level architecture

This is a Java Swing desktop application with a Python bridge for MT5 trade history:

- App startup is `org.example.Main` -> `gui_elements.MainFrame` -> `gui_elements.components.MainViewPanel`.
- `MainViewPanel` owns shared state and wiring:
  - `TradeDataModel` (all trades),
  - `AccountSelectionModel` (selected account / available accounts),
  - `AccountManagementService` + `AccountFetchingService` (persistent account CRUD),
  - page composition via `CardLayout` (`HOME`, `SEARCH`, `ACCOUNTS`).
- `SearchPage` fetches trades through `data.TradeFetcher`, puts them into shared `TradeDataModel`, and `TradeTableController` applies filters (account, session, date range) into `TradeTableModel`.
- `HomePage` receives `OverallStatsController`, which listens to trade/account selection changes, computes `TradeStatistics` via `TradeStatisticsService`, then pushes stats into panels (`WinratePanel`, `TradingBiasPanel`, `SessionWinratesCustomPanel`) through model property events.
- Account persistence is local JSON at:  
  `%USERPROFILE%\.tradingapp_test\accounts.json`  
  (resolved via `System.getProperty("user.home")` in `AccountFetchingService`).
- Trade ingestion path:
  - Java `TradeFetcher` runs `python_scripts\.venv\Scripts\python.exe python_scripts\fetch_deals.py`,
  - Python script uses `MetaTrader5` + `pandas`,
  - script prints one JSON object per trade line,
  - Java parses each line with Gson + `LocalDateTimeAdapter`.

## Key repository conventions

- **Model-driven UI updates use `PropertyChangeSupport`**: models fire named property events; controllers/panels subscribe to specific property names rather than polling.
- **“All” selection is represented by `null`** in account/session models (`ALL_ACCOUNTS` / `ALL_SESSIONS`), and combo-box renderers display `null` as `"All"`.
- **Threading convention for model updates**: `TradeDataModel` and `AccountDataModel` fire updates on Swing EDT (`SwingUtilities.invokeLater` when needed).
- **JFormDesigner-managed UI blocks**: many Swing classes contain `//GEN-BEGIN` / `//GEN-END` sections marked `DO NOT MODIFY`; place custom logic outside generated regions.
- **Table model pattern**: table columns are declared via enum metadata (`name`, `type`, extractor) in `table_models/*TableModel.java`.
- **Session classification source of truth**: `TradeStatisticsService.determineSession(...)` maps trade open time (converted to UTC from `Trade.brokerTimeZone`) into `TradingSession`.
- **Styling convention**: prefer `constants.Theme` for new styling constants; `UIConstants` is deprecated and currently delegates to `Theme`.
- **Date filter format is fixed**: search UI expects `dd-MM-yyyy` text fields with placeholder `DD-MM-YYYY`.
