package data_structures;

import java.util.List;
import java.util.ArrayList;

public class Session {
//    private String sessionName;
    private int totalTrades;
    private int wins;
    private int losses;
    private double winrate;

    // Constructor
    public Session(int totalTrades, int wins, int losses, double winrate) {
//        this.sessionName = sessionName;
        this.totalTrades = totalTrades;
        this.wins = wins;
        this.losses = losses;
        this.winrate = winrate;
//        this.winrate = (totalTrades > 0) ? (wins * 100.0 / totalTrades) : 0.0;
    }

//    // START GETTERS
    public String getSessionName(){return "PLACEHOLDER SESSION NAME";}
    public int getTotalTrades(){return totalTrades;}
    public int getWins(){return wins;}
    public int getLosses(){return losses;}
    public double getWinrate(){return winrate;}
    // END GETTERS
}
