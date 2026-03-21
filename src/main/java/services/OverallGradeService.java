package services;

import data_structures.OverallGrade;
import data_structures.TradeStatistics;

public class OverallGradeService {
    private static final int MIN_SCORE = 1;
    private static final int MAX_SCORE = 100;
    private static final int MIN_CONFIDENT_TRADES = 30;
    private static final int LOW_SAMPLE_SCORE_CAP = 75;

    public OverallGrade calculateGrade(TradeStatistics stats) {
        if (stats == null || stats.totalTradesTaken() == 0) {
            return new OverallGrade(MIN_SCORE, "F");
        }

        double weightedScore =
                20.0 * normalizeProfitFactor(stats.profitFactor()) +
                20.0 * normalizeDrawdownPct(stats.maxDrawdownPct()) +
                15.0 * normalizeExpectancy(stats.expectancy()) +
                15.0 * normalizeSharpe(stats.sharpeRatio()) +
                10.0 * normalizeSortino(stats.sortinoRatio()) +
                15.0 * normalizeWinrate(stats.winrate()) +
                 5.0 * normalizeRiskReward(stats.riskRewardRatio());

        int score = clampToScoreRange((int) Math.round(weightedScore));
        if (stats.totalTradesTaken() < MIN_CONFIDENT_TRADES) {
            score = Math.min(score, LOW_SAMPLE_SCORE_CAP);
        }
        String letter = toLetter(score);
        return new OverallGrade(score, letter);
    }

    private int clampToScoreRange(int score) {
        if (score < MIN_SCORE) {
            return MIN_SCORE;
        }
        if (score > MAX_SCORE) {
            return MAX_SCORE;
        }
        return score;
    }

    private String toLetter(int score) {
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    private double normalizeProfitFactor(double profitFactor) {
        if (profitFactor <= 0) {
            return 0.0;
        }
        return clamp01(profitFactor / 2.5);
    }

    private double normalizeDrawdownPct(double maxDrawdownPct) {
        if (maxDrawdownPct <= 0) {
            return 1.0;
        }
        if (maxDrawdownPct >= 50.0) {
            return 0.0;
        }
        return clamp01(1.0 - (maxDrawdownPct / 50.0));
    }

    private double normalizeExpectancy(double expectancy) {
        if (expectancy <= 0) {
            return 0.0;
        }
        return clamp01(expectancy / 100.0);
    }

    private double normalizeSharpe(double sharpeRatio) {
        if (sharpeRatio <= 0) {
            return 0.0;
        }
        return clamp01(sharpeRatio / 2.0);
    }

    private double normalizeSortino(double sortinoRatio) {
        if (sortinoRatio <= 0) {
            return 0.0;
        }
        return clamp01(sortinoRatio / 3.0);
    }

    private double normalizeWinrate(double winrate) {
        return clamp01(winrate / 100.0);
    }

    private double normalizeRiskReward(double riskRewardRatio) {
        if (riskRewardRatio <= 0) {
            return 0.0;
        }
        return clamp01(riskRewardRatio / 3.0);
    }

    private double clamp01(double value) {
        if (value < 0.0) {
            return 0.0;
        }
        if (value > 1.0) {
            return 1.0;
        }
        return value;
    }
}
