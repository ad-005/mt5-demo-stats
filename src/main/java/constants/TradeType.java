package constants;

public enum TradeType {
    BUY("Buy"),
    SELL("Sell");

    public final String type;

    TradeType(String type) {
        this.type = type;
    }

}
