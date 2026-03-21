# MT5 Demo Account Trading Statistics

![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3-C71A36?logo=apachemaven&logoColor=white)
![Python](https://img.shields.io/badge/Python-3.14-3776AB?logo=python&logoColor=white)
![uv](https://img.shields.io/badge/uv-package%20manager-DE5FE9?logo=astral&logoColor=white)
![FlatLaf](https://img.shields.io/badge/FlatLaf-3.7-4A90D9?logo=java&logoColor=white)
![MigLayout](https://img.shields.io/badge/MigLayout-11.3-6DB33F?logoColor=white)
![Gson](https://img.shields.io/badge/Gson-2.11-4285F4?logo=google&logoColor=white)
![MetaTrader5](https://img.shields.io/badge/MetaTrader5-API-1A1A2E?logoColor=white)

---

This project is a precursor to the more advanced
[MT5 Demo Account Statistics](https://github.com/ad-005/MT5-Demo-Account-Statistics) project also on my account.
Unlike that one, this application was written and architected by me from scratch.

Transparency note: The initial version of this project was fully created by me. Planned future improvements, refactorings, or additions may be implemented with assistance from AI agents (for example, GitHub Copilot). Any AI-generated code will be reviewed by me and clearly documented in commits or pull requests.

The motivation behind it is simple: MetaTrader 5's built-in reporting doesn't give you the kind of
breakdown that actually helps you improve. This app pulls your real closed-trade history straight from
a running MT5 terminal and presents it in a clean, navigable desktop interface so you can actually
analyse what's going on.

---

## Features

### Dashboard — Overall Statistics
The home screen gives you an at-a-glance summary of your trading performance:
- **Winrate** — the percentage of trades closed in profit across all selected data.
- **Trading bias** — shows whether you lean bullish (more BUY trades) or bearish (more SELL trades),
  expressed as a percentage split.
- **Session winrates** — your winrate broken down by the three major forex sessions
  (Asian, London, New York), so you can see where you perform best.
- **Per-account statistics** — filter all of the above by a specific demo account.

### Trade Search & Filtering
A dedicated search page lets you drill into your trade history with several filters:
- **Date range** — narrow results down to a custom start and end date.
- **Account** — view trades belonging to a specific demo account login.
- **Session** — isolate trades that were opened during a particular trading session.

All matching trades are displayed in a sortable table showing ticket, symbol, type, volume, open/close
prices, profit, change %, and open/close timestamps.

### Account Management
Add and remove MetaTrader 5 demo accounts from within the app itself. Accounts are stored locally and
persist between sessions, so you don't have to re-enter them every time you launch the app.

---

## Prerequisites

These steps are aimed at users who haven't run a Java/Python project before.

### 1. MetaTrader 5
You need **MetaTrader 5** installed and running with at least one demo account logged in.
The app connects to the terminal that is open on your machine to pull your trade history.
Download it from [metatrader5.com](https://www.metatrader5.com/en/download).

### 2. Java Development Kit (JDK) 25+
The application is built with Java. Install a JDK (version 25 or later) from
[Adoptium](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/).
Make sure the `java` command is available on your PATH by running `java -version` in a terminal.

### 3. Maven
Maven is used to build the project and download its dependencies automatically.
Install it from [maven.apache.org](https://maven.apache.org/download.cgi) and verify with `mvn -v`.

### 4. Python 3 & uv
The trade-fetching script is written in Python and uses **uv** to manage its dependencies.
- Install Python 3 from [python.org](https://www.python.org/downloads/).
- Install **uv** by following the instructions at [docs.astral.sh/uv](https://docs.astral.sh/uv/getting-started/installation/).
- From the `python_scripts/` directory, run `uv sync` once to create the virtual environment and
  install the required packages (including `MetaTrader5` and `pandas`).

### Running the App
Once the above are in place:
```bash
mvn compile exec:java
```
or open the project in your IDE of choice and run the `main` class directly.
