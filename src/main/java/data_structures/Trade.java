package data_structures;

import constants.TradeSymbol;
import constants.TradeType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Trade {
    // START ATTRIBUTES
    private LocalDateTime openTime;
    private TradeSymbol symbol;
    private String ticket;
    private TradeType type;
    private double volume;
    private double openPrice;
    private double stopLoss;
    private double takeProfit;
    private LocalDateTime closeTime;
    private double closePrice;
    private double profit;
    private double commission;
    private double swap;
    private double change;
    private String accountLogin;
    // END ATTRIBUTES

    // START TEST ATTRIBUTES
    private final ZoneId brokerTimeZone = ZoneId.of("UTC+3");
    // END TEST ATTRIBUTES

    // START CONSTRUCTOR
    public Trade(LocalDateTime openTime, TradeSymbol symbol, String ticket, TradeType type, double volume,
                 double openPrice, double stopLoss, double takeProfit, LocalDateTime closeTime, double closePrice,
                 double profit, double change, String accountLogin) {
        this(openTime, symbol, ticket, type, volume, openPrice, stopLoss, takeProfit, closeTime, closePrice,
                profit, 0.0, 0.0, change, accountLogin);
    }

    public Trade(LocalDateTime openTime, TradeSymbol symbol, String ticket, TradeType type, double volume,
                 double openPrice, double stopLoss, double takeProfit, LocalDateTime closeTime, double closePrice,
                 double profit, double commission, double swap, double change, String accountLogin) {
        this.openTime = openTime;
        this.symbol = symbol;
        this.ticket = ticket;
        this.type = type;
        this.volume = volume;
        this.openPrice = openPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.closeTime = closeTime;
        this.closePrice = closePrice;
        this.profit = profit;
        this.commission = commission;
        this.swap = swap;
        this.change = change;
        this.accountLogin = accountLogin;
    }
    // END CONSTRUCTOR

    // START GETTERS
    public LocalDateTime getOpenTime() { return openTime; }
    public TradeSymbol getSymbol() { return symbol; }
    public String getTicket() { return ticket; }
    public TradeType getType() { return type; }
    public double getVolume() { return volume; }
    public double getOpenPrice() { return openPrice; }
    public double getStopLoss() { return stopLoss; }
    public double getTakeProfit() { return takeProfit; }
    public LocalDateTime getCloseTime() { return closeTime; }
    public double getClosePrice() { return closePrice; }
    public double getProfit() { return profit; }
    public double getCommission() { return commission; }
    public double getSwap() { return swap; }
    public double getChange() { return change; }
    public String getAccountLogin() { return accountLogin; }
    // END GETTERS

    // START TEST ATTRIBUTE GETTERS
    public ZoneId getBrokerTimeZone() { return brokerTimeZone; }
    // END TEST ATTRIBUTE GETTERS

}
