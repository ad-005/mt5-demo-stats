package gui_elements.components.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public final class AppIcons {
    private AppIcons() {
    }

    public static Icon account(int size, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(color);
                    g2.setStroke(new BasicStroke(Math.max(1f, size / 12f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    int headSize = Math.max(4, size / 3);
                    int headX = x + (size - headSize) / 2;
                    int headY = y + Math.max(1, size / 10);
                    g2.drawOval(headX, headY, headSize, headSize);

                    int bodyY = headY + headSize + Math.max(1, size / 10);
                    int bodyHeight = Math.max(4, size / 3);
                    int bodyWidth = Math.max(8, (int) (size * 0.75));
                    int bodyX = x + (size - bodyWidth) / 2;
                    Shape body = new RoundRectangle2D.Float(bodyX, bodyY, bodyWidth, bodyHeight, bodyHeight, bodyHeight);
                    g2.draw(body);
                } finally {
                    g2.dispose();
                }
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    public static Icon trash(int size, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(color);
                    g2.setStroke(new BasicStroke(Math.max(1f, size / 11f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    int lidY = y + Math.max(1, size / 6);
                    int canTopY = y + Math.max(2, size / 3);
                    int canLeftX = x + Math.max(2, size / 4);
                    int canWidth = Math.max(6, size / 2);
                    int canHeight = Math.max(6, size / 2);

                    g2.drawLine(canLeftX, canTopY, canLeftX + canWidth, canTopY);
                    g2.drawLine(canLeftX + Math.max(1, size / 8), lidY, canLeftX + canWidth - Math.max(1, size / 8), lidY);
                    g2.drawLine(x + size / 2, y + Math.max(1, size / 10), x + size / 2, lidY);

                    g2.draw(new RoundRectangle2D.Float(canLeftX, canTopY, canWidth, canHeight, 3, 3));

                    int line1 = canLeftX + canWidth / 3;
                    int line2 = canLeftX + (2 * canWidth) / 3;
                    int top = canTopY + Math.max(1, size / 8);
                    int bottom = canTopY + canHeight - Math.max(1, size / 8);
                    g2.drawLine(line1, top, line1, bottom);
                    g2.drawLine(line2, top, line2, bottom);
                } finally {
                    g2.dispose();
                }
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
}
