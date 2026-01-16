package constants;

import java.awt.Font;
import java.awt.Color;
import java.text.DecimalFormat;

public class UIConstants {

    public static final Font TABLE_DATA_FONT = new Font("IBM Plex Mono", Font.BOLD, 10);
    public static final Font DEFAULT_LABEL_FONT = new Font("IBM Plex Mono", Font.PLAIN, 12);
    public static final Font DEFAULT_BUTTON_FONT = new Font("IBM Plex Mono", Font.BOLD, 16);
    public static final Font TABLE_HEADER_FONT = new Font("IBM Plex Mono", Font.BOLD, 12);
    public static final Font SESSION_NAME_LABEL_FONT = new Font("IBM Plex Mono", Font.BOLD, 18);
    public static final Font SESSION_WINRATE_LABEL_FONT = new Font("IBM Plex Mono", Font.BOLD, 18);
    public static final Color LOSS_RED_COLOR = new Color(204, 58, 35);
    public static final Color PROFIT_GREEN_COLOR = new Color(86, 179, 46);
    public static final DecimalFormat PROFIT_DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

}