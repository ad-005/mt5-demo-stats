package gui_elements.renderers;

import constants.UIConstants;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;

import java.awt.*;

public class ProfitCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                              boolean hasFocus, int row, int column) {

        if (value instanceof Double) {
            double profit = (Double) value;
            String formattedProfit = UIConstants.PROFIT_DECIMAL_FORMAT.format(profit);

            Component cell = super.getTableCellRendererComponent(table, formattedProfit, isSelected, hasFocus, row, column);

            Font boldFont = table.getFont().deriveFont(Font.BOLD);
            cell.setFont(boldFont);

            if (profit < 0) {
                cell.setForeground(UIConstants.LOSS_RED_COLOR);
            } else if (profit > 0) {
                cell.setForeground(UIConstants.PROFIT_GREEN_COLOR);
            } else {
                cell.setForeground(Color.BLACK);
            }

            return cell;
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
