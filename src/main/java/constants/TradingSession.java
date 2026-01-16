package constants;

public enum TradingSession {

    ASIAN("Asian"),
    SYDNEY("Sydney"),
    LONDON("London"),
    NEW_YORK("New York");

    public final String sessionName;

    TradingSession(String sessionName) {
        this.sessionName = sessionName;
    }

}
