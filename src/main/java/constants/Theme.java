package constants;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;

/**
 * Centralized theme configuration for the application.
 * Contains semantic styling constants organized by category.
 *
 * FlatLaf handles component-level defaults via properties files.
 * This class provides application-specific semantic styles.
 */
public final class Theme {

    private Theme() {
        // Prevent instantiation
    }

    // ===== FONT FAMILY =====
    public static final String FONT_FAMILY = "IBM Plex Mono";

    // ===== COLORS =====
    public static final class Colors {
        private Colors() {}

        // Base surfaces for light, macOS-like appearance
        public static final Color APP_BACKGROUND = new Color(0xF5F6F8);
        public static final Color SURFACE_PRIMARY = Color.WHITE;
        public static final Color SURFACE_SECONDARY = new Color(0xF0F2F5);
        public static final Color TEXT_PRIMARY = new Color(0x1F2937);
        public static final Color TEXT_SECONDARY = new Color(0x6B7280);
        public static final Color ACCENT = new Color(0x0A84FF);

        // Semantic colors for profit/loss indicators
        public static final Color SUCCESS = new Color(86, 179, 46);      // #56b32e - profit green
        public static final Color DANGER = new Color(204, 58, 35);       // #cc3a23 - loss red
        public static final Color NEUTRAL = TEXT_PRIMARY;

        // Progress bar colors (winrate/bias bars)
        public static final Color PROGRESS_POSITIVE = SUCCESS;           // foreground for positive
        public static final Color PROGRESS_NEGATIVE = new Color(0xff3333); // background for negative

        // Border colors
        public static final Color BORDER_DEFAULT = new Color(0xD2D8E0);
        public static final Color TABLE_HEADER_SEPARATOR = new Color(0x8c8c8c);
    }

    // ===== FONTS =====
    public static final class Fonts {
        private Fonts() {}

        // Base font sizes
        private static final int SIZE_SMALL = 10;
        private static final int SIZE_MEDIUM = 12;
        private static final int SIZE_LARGE = 14;
        private static final int SIZE_XLARGE = 16;
        private static final int SIZE_XXLARGE = 18;

        // Table fonts
        public static final Font TABLE_DATA = new Font(FONT_FAMILY, Font.BOLD, SIZE_SMALL);
        public static final Font TABLE_HEADER = new Font(FONT_FAMILY, Font.BOLD, SIZE_MEDIUM);
        public static final Font TABLE_DATA_LARGE = new Font(FONT_FAMILY, Font.BOLD, SIZE_XLARGE);

        // Label fonts
        public static final Font LABEL_DEFAULT = new Font(FONT_FAMILY, Font.PLAIN, SIZE_MEDIUM);
        public static final Font LABEL_BOLD = new Font(FONT_FAMILY, Font.BOLD, SIZE_MEDIUM);
        public static final Font LABEL_LARGE = new Font(FONT_FAMILY, Font.BOLD, SIZE_LARGE);
        public static final Font LABEL_XLARGE = new Font(FONT_FAMILY, Font.BOLD, SIZE_XLARGE);
        public static final Font LABEL_HEADING = new Font(FONT_FAMILY, Font.BOLD, SIZE_XXLARGE);

        // Button fonts
        public static final Font BUTTON_DEFAULT = new Font(FONT_FAMILY, Font.BOLD, SIZE_XLARGE);

        // Panel title fonts
        public static final Font PANEL_TITLE = new Font(FONT_FAMILY, Font.BOLD, SIZE_XLARGE);
    }

    // ===== BORDERS =====
    public static final class Borders {
        private Borders() {}

        // Standard panel border
        public static final int DEFAULT_THICKNESS = 1;
        public static final boolean DEFAULT_ROUNDED = true;

        /**
         * Creates a standard rounded line border.
         */
        public static Border panelBorder() {
            return new LineBorder(Colors.BORDER_DEFAULT, DEFAULT_THICKNESS, DEFAULT_ROUNDED);
        }

        /**
         * Creates a panel border with inner padding.
         */
        public static Border panelBorderWithPadding(int padding) {
            return new CompoundBorder(
                panelBorder(),
                new EmptyBorder(padding, padding, padding, padding)
            );
        }

        /**
         * Creates a standard padding border.
         */
        public static Border padding(int size) {
            return new EmptyBorder(size, size, size, size);
        }

        /**
         * Creates an asymmetric padding border.
         */
        public static Border padding(int top, int left, int bottom, int right) {
            return new EmptyBorder(top, left, bottom, right);
        }
    }

    // ===== FORMATTING =====
    public static final class Formatting {
        private Formatting() {}

        public static final DecimalFormat PROFIT = new DecimalFormat("#,##0.00");
        public static final DecimalFormat PERCENTAGE = new DecimalFormat("0.00");
    }
}
