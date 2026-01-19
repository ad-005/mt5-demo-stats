package data;

import constants.AccountType;
import data_structures.Account;
import data_structures.Session;
import data_structures.Trade;
import constants.TradeSymbol;
import constants.TradeType;
import services.AccountFetchingService;
import services.AccountManagementService;
import services.RandomDataService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MockDataFactory {
    private static final Random random = new Random();
    private static final AccountFetchingService accountFetchingService = new AccountFetchingService();
    private static final AccountManagementService accountManagementService = new AccountManagementService(accountFetchingService);

    // Generate List of random Account
    public static List<Account> generateAccounts(int numberOfAccounts) {
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < numberOfAccounts; i++) {
            accounts.add(createRandomAccount());
        }

        return accounts;
    }

    // Create random Account
    public static Account createRandomAccount() {
        String name = "user_" + RandomDataService.generateRandomString(4);
        AccountType type = getRandomConst(AccountType.class);
        String server = "MetaQuotes-Demo";
        String login = RandomDataService.generateRandomString(10);
        String password = RandomDataService.generateRandomString(8);
        String investor = RandomDataService.generateRandomString(8);

        return new Account(name, type, server, login, password, investor);
    }

    // Create List of n random trades
    public static List<Trade> generateTrades(int numberOfTrades) {
        List<Trade> trades = new ArrayList<>();

        for (int i = 0; i < numberOfTrades; i++) {
            trades.add(createRandomTrade());
        }

        return trades;
    }

    // Create random Trade
    public static Trade createRandomTrade() {
        LocalDateTime openTime = createRandomDateTime(LocalDate.of(2025, 10, 1), LocalDate.now());
        TradeSymbol symbol = getRandomConst(TradeSymbol.class);
        String ticket = String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
        TradeType type = getRandomConst(TradeType.class);
        double volume = randomDouble(0.1, 5);
        double openPrice = randomDouble(0.9, 210);
        double stopLoss = calculateStopLoss(openPrice, type);
        double takeProfit = calculateTakeProfit(openPrice, type);
        LocalDateTime closeTime = createRandomDateTime(LocalDate.from(openTime), LocalDate.now());
        double closePrice = generateClosePrice(stopLoss, takeProfit);
        double profit = calculateProfit(openPrice, closePrice, volume, type);
        double change = calculateChange(type, openPrice, closePrice);
        String login = getRandomAccountLogin();

        return new Trade(openTime, symbol, ticket, type, volume, openPrice,
                stopLoss, takeProfit, closeTime, closePrice, profit, change, getRandomAccountLogin());
    }

    // Get random account login
    public static String getRandomAccountLogin() {
        List<Account> availableAccounts = accountManagementService.getAccounts();

        if (availableAccounts.isEmpty()) {
            return "GenericLogin";
        }

        int accountIndex = ThreadLocalRandom.current().nextInt(availableAccounts.size());
        return availableAccounts.get(accountIndex).getLogin();
    }

    // Calculate change (change in price from open to close)
    public static double calculateChange(TradeType type, double openPrice, double closePrice) {
        if (type.name().equals("BUY")) {
            return ((closePrice - openPrice) / openPrice) * 100;
        } else {
            return ((openPrice - closePrice) / openPrice) * 100;
        }
    }

    // Generate close price (random whether take profit got hit or not)
    public static double generateClosePrice(double stopLoss, double takeProfit) {
        boolean takeProfitHit = ThreadLocalRandom.current().nextBoolean();

        if (takeProfitHit) {
            return takeProfit;
        } else {
            return stopLoss;
        }

    }

    // Calculate take profit based on open price
    public static double calculateTakeProfit(double openPrice, TradeType type) {
        double tpPercentage = ThreadLocalRandom.current().nextDouble(0.02, 0.06);

        if (type.name().equals("BUY")) {
            return openPrice * (1 + tpPercentage);
        } else {
            return openPrice * (1 - tpPercentage);
        }
    }

    // Calculate stop loss based on open price
    public static double calculateStopLoss(double openPrice, TradeType type) {
        double slPercentage = ThreadLocalRandom.current().nextDouble(0.01, 0.02);

        if (type.name().equals("BUY")) {
            return openPrice * (1 - slPercentage);
        } else {
            return openPrice * (1 + slPercentage);
        }
    }

    // Calculate profit based on openPrice, closePrice, volume and type
    public static double calculateProfit(double openPrice, double closePrice, double volume, TradeType type) {
        double priceDifference;

        if (type.name().equals("BUY")) {
            priceDifference = closePrice - openPrice;
        } else {
            priceDifference = openPrice - closePrice;
        }

        return priceDifference * volume * 100000;
    }

    // Get random TradeSymbol
    public static <E extends Enum<E>> E getRandomConst(Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }

    // Create random LocalDateTime
    public static LocalDateTime createRandomDateTime(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        int hour = ThreadLocalRandom.current().nextInt(24);
        int minute = ThreadLocalRandom.current().nextInt(60);
        int second = ThreadLocalRandom.current().nextInt(60);

        return LocalDateTime.of(randomDate, LocalTime.of(hour, minute, second));
    }

//     Generate session data
    public static List<Session> generateSessionData() {
        List<Session> data = new ArrayList<>();

        data.add(createSession("Asian", 5, 20));
        data.add(createSession("Sydney", 0, 10));
        data.add(createSession("London", 40, 70));
        data.add(createSession("New York", 40, 70));

        return data;
    }

    // Create random session
    public static Session createSession(String sessionName, int minTradeCount, int maxTradeCount) {
        int tradeCount = randomInt(minTradeCount, maxTradeCount);

        int wins = randomInt(0, tradeCount);
        int losses = tradeCount - wins;

        return new Session(tradeCount, wins, losses, 80); // TODO Change winrate later, properly. (this is for testing purposes)
//        Session session = new Session(sessionName, tradeCount, wins, losses);

//        System.out.printf("%nSession Generated | " +
//                        "Session Name: %s | " +
//                        "Total Trades: %s | " +
//                        "Wins: %s | " +
//                        "Losses: %s | " +
//                        "Winrate: %s",
//                session.getSessionName(),
//                session.getTotalTrades(),
//                session.getWins(),
//                session.getLosses(),
//                session.getWinrate());
//        return session;
    }

    // Helper method for random integer generation
    private static int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    // Helper method for random double generation
    private static double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

}
