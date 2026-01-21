package constants;

import java.awt.Font;
import java.awt.Color;
import java.text.DecimalFormat;

/**
 * Legacy UI constants - delegates to Theme.java for centralized styling.
 *
 * @deprecated Use {@link Theme} instead for new code.
 *             This class is maintained for backward compatibility.
 */
@Deprecated
public class UIConstants {

    // Font constants - delegate to Theme.Fonts
    public static final Font TABLE_DATA_FONT = Theme.Fonts.TABLE_DATA;
    public static final Font DEFAULT_LABEL_FONT = Theme.Fonts.LABEL_DEFAULT;
    public static final Font DEFAULT_BUTTON_FONT = Theme.Fonts.BUTTON_DEFAULT;
    public static final Font TABLE_HEADER_FONT = Theme.Fonts.TABLE_HEADER;
    public static final Font SESSION_NAME_LABEL_FONT = Theme.Fonts.LABEL_HEADING;
    public static final Font SESSION_WINRATE_LABEL_FONT = Theme.Fonts.LABEL_HEADING;

    // Color constants - delegate to Theme.Colors
    public static final Color LOSS_RED_COLOR = Theme.Colors.DANGER;
    public static final Color PROFIT_GREEN_COLOR = Theme.Colors.SUCCESS;

    // Formatting - delegate to Theme.Formatting
    public static final DecimalFormat PROFIT_DECIMAL_FORMAT = Theme.Formatting.PROFIT;

}