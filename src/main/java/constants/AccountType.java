package constants;

public enum AccountType {

    FOREX_HEDGING_EUR("Forex Hedging EUR"),
    FOREX_HEDGING_USD("Forex Hedging USD");

    public final String typeName;

    AccountType(String typeName) {
        this.typeName = typeName;
    }
}
