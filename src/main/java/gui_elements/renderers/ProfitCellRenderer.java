package gui_elements.renderers;

import constants.Theme;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

import java.awt.*;

public class ProfitCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                              boolean hasFocus, int row, int column) {

        if (value instanceof Double) {
            double profit = (Double) value;
            String formattedProfit = Theme.Formatting.PROFIT.format(profit);

            Component cell = super.getTableCellRendererComponent(table, formattedProfit, isSelected, hasFocus, row, column);

            Font boldFont = table.getFont().deriveFont(Font.BOLD);
            cell.setFont(boldFont);

            if (profit < 0) {
                cell.setForeground(Theme.Colors.DANGER);
            } else if (profit > 0) {
                cell.setForeground(Theme.Colors.SUCCESS);
            } else {
                cell.setForeground(Theme.Colors.NEUTRAL);
            }

            return cell;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
