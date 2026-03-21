package data_structures;

import java.time.LocalDateTime;

public class ReportSnapshot {
    private String id;
    private LocalDateTime createdAt;
    private String accountLogin;
    private String accountName;
    private CachedTradeStatistics statistics;

    public ReportSnapshot() {
    }

    public ReportSnapshot(String id, LocalDateTime createdAt, String accountLogin, String accountName, TradeStatistics statistics) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Report id cannot be empty.");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Report timestamp cannot be null.");
        }
        if (statistics == null) {
            throw new IllegalArgumentException("Report statistics cannot be null.");
        }

        this.id = id;
        this.createdAt = createdAt;
        this.accountLogin = accountLogin;
        this.accountName = accountName;
        this.statistics = new CachedTradeStatistics(statistics);
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAccountLogin() {
        return accountLogin;
    }

    public String getAccountName() {
        return accountName;
    }

    public CachedTradeStatistics getStatistics() {
        return statistics;
    }

    public String getAccountLabel() {
        if (accountLogin == null || accountLogin.isBlank()) {
            return "All Accounts";
        }
        if (accountName == null || accountName.isBlank()) {
            return accountLogin;
        }
        return accountName + " (" + accountLogin + ")";
    }

    public TradeStatistics toTradeStatistics() {
        return statistics != null ? statistics.toTradeStatistics() : null;
    }
}
