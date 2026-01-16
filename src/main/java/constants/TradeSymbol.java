package constants;

public enum TradeSymbol {
    GBPJPY("GBPJPY"),
    USDJPY("USDJPY"),
    XAUUSD("XAUUSD");

    public final String symbolName;

    TradeSymbol(String symbolName) {
        this.symbolName = symbolName;
    }
}
