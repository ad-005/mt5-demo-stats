package gui_elements.components.panels;

import constants.Theme;
import controllers.OverallStatsController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OverallGradePanel extends JPanel implements PropertyChangeListener {
    private final JLabel gradeLabel;
    private final JLabel scoreLabel;

    private String grade = "F";
    private int score = 1;

    public OverallGradePanel() {
        setLayout(new BorderLayout(12, 0));
        setBorder(new TitledBorder(Theme.Borders.panelBorder(), "Overall Grade", TitledBorder.CENTER, TitledBorder.TOP, Theme.Fonts.PANEL_TITLE));
        setOpaque(false);

        JPanel content = new JPanel(new GridLayout(1, 2, 16, 0));
        content.setOpaque(false);

        JPanel gradeContainer = buildValuePanel("Grade");
        JPanel scoreContainer = buildValuePanel("Points");
        gradeLabel = (JLabel) gradeContainer.getClientProperty("valueLabel");
        scoreLabel = (JLabel) scoreContainer.getClientProperty("valueLabel");

        content.add(gradeContainer);
        content.add(scoreContainer);
        add(content, BorderLayout.CENTER);

        render();
    }

    public void setController(OverallStatsController controller) {
        controller.getStatsModel().addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("overallGrade".equals(evt.getPropertyName()) && evt.getNewValue() instanceof String value) {
            grade = value;
            render();
            return;
        }
        if ("overallScore".equals(evt.getPropertyName()) && evt.getNewValue() instanceof Number value) {
            score = value.intValue();
            render();
        }
    }

    private JPanel buildValuePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(true);
        panel.setBackground(Theme.Colors.SURFACE_SECONDARY);
        panel.setBorder(Theme.Borders.panelBorderWithPadding(10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.Fonts.LABEL_BOLD);
        titleLabel.setForeground(Theme.Colors.TEXT_SECONDARY);

        JLabel valueLabel = new JLabel();
        valueLabel.setFont(Theme.Fonts.LABEL_HEADING);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.putClientProperty("valueLabel", valueLabel);
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.EAST);
        return panel;
    }

    private void render() {
        gradeLabel.setText(grade);
        gradeLabel.setForeground(gradeColor(grade));
        scoreLabel.setText(String.format("%d/100", Math.max(1, Math.min(100, score))));
    }

    private Color gradeColor(String letter) {
        return switch (letter) {
            case "A" -> Theme.Colors.GRADE_A;
            case "B" -> Theme.Colors.GRADE_B;
            case "C" -> Theme.Colors.GRADE_C;
            case "D" -> Theme.Colors.GRADE_D;
            default -> Theme.Colors.GRADE_F;
        };
    }
}
