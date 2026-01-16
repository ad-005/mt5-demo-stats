package data_structures;

import constants.TradeSymbol;
import constants.TradeType;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
    private double change;
    // END ATTRIBUTES

    // START TEST ATTRIBUTES
    private ZoneId brokerTimeZone;
    // END TEST ATTRIBUTES

    // START CONSTRUCTOR
    public Trade(LocalDateTime openTime, TradeSymbol symbol, String ticket, TradeType type, double volume,
                 double openPrice, double stopLoss, double takeProfit, LocalDateTime closeTime, double closePrice,
                 double profit, double change) {
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
        this.change = change;
        this.brokerTimeZone = ZoneId.of("UTC+3");
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
    public double getChange() { return change; }
    // END GETTERS

    // START TEST ATTRIBUTE GETTERS
    public ZoneId getBrokerTimeZone() { return brokerTimeZone; }
    // END TEST ATTRIBUTE GETTERS

}
