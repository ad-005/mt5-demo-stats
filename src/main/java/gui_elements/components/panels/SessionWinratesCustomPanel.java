package gui_elements.components.panels;

import constants.TradingSession;
import controllers.OverallStatsController;
import data.MockDataFactory;
import data_structures.Session;
import data_structures.Trade;
import gui_elements.components.elements.WinrateProgressBar;
import constants.UIConstants;
import interfaces.ModelObserver;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionWinratesCustomPanel extends JPanel implements PropertyChangeListener {
    private List<Session> sessions = MockDataFactory.generateSessionData();
    private JLabel asianSessionLabel;
    private JLabel sydneySessionLabel;
    private JLabel londonSessionLabel;
    private JLabel newyorkSessionLabel;

    private JLabel asianWinrateLabel;
    private JLabel sydneyWinrateLabel;
    private JLabel londonWinrateLabel;
    private JLabel newyorkWinrateLabel;

    private JProgressBar asianSessionBar;
    private JProgressBar sydneySessionBar;
    private JProgressBar londonSessionBar;
    private JProgressBar newyorkSessionBar;

    private OverallStatsController controller;

    public SessionWinratesCustomPanel() {
        setLayout(new GridLayout(4, 3, 10, 20));
        setOpaque(false);
        setBorder(new CompoundBorder(new TitledBorder(new LineBorder(Color.BLACK,
                2, true),
                "Session Winrates",
                TitledBorder.CENTER,
                TitledBorder.TOP, UIConstants.DEFAULT_BUTTON_FONT),
                new EmptyBorder(10, 10, 10, 10)));

        initElements();
    }

    public SessionWinratesCustomPanel(OverallStatsController controller) {
        this();
        setController(controller);
    }

    // Set controller
    public void setController(OverallStatsController controller) {
        this.controller = controller;
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    // Helper method for element initialization
    private void initElements() {
        asianSessionLabel = new JLabel();
        sydneySessionLabel = new JLabel();
        londonSessionLabel = new JLabel();
        newyorkSessionLabel = new JLabel();

        asianWinrateLabel = new JLabel();
        sydneyWinrateLabel = new JLabel();
        londonWinrateLabel = new JLabel();
        newyorkWinrateLabel = new JLabel();

        asianSessionBar = new WinrateProgressBar();
        sydneySessionBar = new WinrateProgressBar();
        londonSessionBar = new WinrateProgressBar();
        newyorkSessionBar = new WinrateProgressBar();

        asianSessionLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        sydneySessionLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        londonSessionLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        newyorkSessionLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);

        asianWinrateLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        sydneyWinrateLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        londonWinrateLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);
        newyorkWinrateLabel.setFont(UIConstants.SESSION_WINRATE_LABEL_FONT);

        this.add(asianSessionLabel);
        this.add(asianSessionBar);
        this.add(asianWinrateLabel);

        this.add(sydneySessionLabel);
        this.add(sydneySessionBar);
        this.add(sydneyWinrateLabel);

        this.add(londonSessionLabel);
        this.add(londonSessionBar);
        this.add(londonWinrateLabel);

        this.add(newyorkSessionLabel);
        this.add(newyorkSessionBar);
        this.add(newyorkWinrateLabel);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void propertyChange(PropertyChangeEvent event) {
        if ("sessionStats".equals(event.getPropertyName())) {
            Map<TradingSession, Session> sessionMap = (HashMap<TradingSession, Session>) event.getNewValue();

            Session asianData = sessionMap.getOrDefault(TradingSession.ASIAN, new Session(0, 0, 0, 0.0));
            asianSessionLabel.setText(TradingSession.ASIAN.sessionName);
            asianSessionBar.setValue((int) asianData.getWinrate());
            asianWinrateLabel.setText(String.format("%.2f %%", asianData.getWinrate()));

            Session sydneyData = sessionMap.getOrDefault(TradingSession.SYDNEY, new Session(0, 0, 0, 0.0));
            sydneySessionLabel.setText(TradingSession.SYDNEY.sessionName);
            sydneySessionBar.setValue((int) sydneyData.getWinrate());
            sydneyWinrateLabel.setText(String.format("%.2f %%", sydneyData.getWinrate()));

            Session londonData = sessionMap.getOrDefault(TradingSession.LONDON, new Session(0, 0, 0, 0.0));
            londonSessionLabel.setText(TradingSession.LONDON.sessionName);
            londonSessionBar.setValue((int) londonData.getWinrate());
            londonWinrateLabel.setText(String.format("%.2f %%", londonData.getWinrate()));

            Session newyorkData = sessionMap.getOrDefault(TradingSession.NEW_YORK, new Session(0, 0, 0, 0.0));
            newyorkSessionLabel.setText(TradingSession.NEW_YORK.sessionName);
            newyorkSessionBar.setValue((int) newyorkData.getWinrate());
            newyorkWinrateLabel.setText(String.format("%.2f %%", newyorkData.getWinrate()));
        }
    }
}
