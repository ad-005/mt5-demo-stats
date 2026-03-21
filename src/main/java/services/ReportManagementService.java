package services;

import data_structures.ReportSnapshot;
import data_structures.TradeStatistics;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class ReportManagementService {
    private final ReportFetchingService reportFetchingService;

    public ReportManagementService(ReportFetchingService reportFetchingService) {
        this.reportFetchingService = reportFetchingService;
    }

    public List<ReportSnapshot> getReports() {
        return sortByNewest(reportFetchingService.loadReports());
    }

    public ReportSnapshot createReport(String accountLogin, String accountName, TradeStatistics statistics) {
        if (statistics == null) {
            throw new IllegalArgumentException("Trade statistics cannot be null.");
        }

        ReportSnapshot report = new ReportSnapshot(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                accountLogin,
                accountName,
                statistics
        );

        List<ReportSnapshot> reports = reportFetchingService.loadReports();
        reports.add(report);
        reportFetchingService.saveReports(reports);
        return report;
    }

    public void deleteReport(String reportId) {
        if (reportId == null || reportId.isBlank()) {
            throw new IllegalArgumentException("Report id cannot be empty.");
        }

        List<ReportSnapshot> reports = reportFetchingService.loadReports();
        boolean removed = reports.removeIf(report -> reportId.equals(report.getId()));

        if (!removed) {
            throw new IllegalArgumentException("Report not found.");
        }

        reportFetchingService.saveReports(reports);
    }

    private List<ReportSnapshot> sortByNewest(List<ReportSnapshot> reports) {
        return reports.stream()
                .sorted(Comparator.comparing(ReportSnapshot::getCreatedAt).reversed())
                .toList();
    }
}
