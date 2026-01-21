# GUI Styling Guide

## Overview

This application uses a two-tier styling architecture:
1. **FlatLaf Properties** - Component-level defaults applied globally
2. **Theme.java** - Application-specific semantic styles

## Architecture

### FlatLaf Properties (`src/main/resources/gui_elements/themes/`)

Global component defaults are defined in FlatLaf properties files:
- `FlatLaf.properties` - Base settings shared across all themes
- `FlatLightLaf.properties` - Light theme overrides (currently active)
- `FlatDarkLaf.properties` - Dark theme overrides (reserved for future use)

These properties are automatically loaded via `FlatLaf.registerCustomDefaultsSource("gui_elements/themes")` in `Main.java`.

#### What Goes in FlatLaf Properties?

- Global fonts for component types (Button.font, Label.font, etc.)
- Table styling (grid lines, header colors)
- Progress bar styling
- Border defaults
- Any component property that should be consistent across the entire application

**Example:**
```properties
# Global button font
Button.font = IBM Plex Mono bold 16

# Table header styling
TableHeader.font = IBM Plex Mono bold 12
TableHeader.separatorColor = #8c8c8c
```

### Theme.java (`src/main/java/constants/`)

Application-specific semantic styles organized into nested classes:

#### `Theme.Colors`
Semantic color constants for application logic:
```java
Theme.Colors.SUCCESS     // #56b32e - For profit/positive indicators
Theme.Colors.DANGER      // #cc3a23 - For loss/negative indicators
Theme.Colors.NEUTRAL     // Black - For neutral values
```

#### `Theme.Fonts`
Semantic font constants:
```java
Theme.Fonts.TABLE_DATA        // Small, bold - Table cell content
Theme.Fonts.TABLE_HEADER      // Medium, bold - Table headers
Theme.Fonts.LABEL_DEFAULT     // Medium, plain - Standard labels
Theme.Fonts.LABEL_HEADING     // XX-Large, bold - Panel titles
Theme.Fonts.BUTTON_DEFAULT    // X-Large, bold - Button text
```

#### `Theme.Borders`
Border factory methods:
```java
Theme.Borders.panelBorder()                    // Standard rounded border
Theme.Borders.panelBorderWithPadding(10)       // Border + inner padding
Theme.Borders.padding(5, 10, 5, 10)            // Custom padding
```

#### `Theme.Formatting`
Number formatters:
```java
Theme.Formatting.PROFIT      // "#,##0.00" - Currency format
Theme.Formatting.PERCENTAGE  // "0.00" - Percentage format
```

## Usage Guidelines

### ✅ When to Use FlatLaf Properties

Use FlatLaf properties for:
- Setting default fonts/colors for ALL instances of a component type
- Table styling that applies application-wide
- Component properties that should be theme-aware
- Eliminating repetition in JFormDesigner files

### ✅ When to Use Theme.java

Use Theme.java for:
- Runtime conditional styling (e.g., profit/loss colors)
- Application-specific semantic colors
- Custom borders with specific requirements
- Number formatting

### ✅ When to Use Inline Styles

Use inline styles in JFormDesigner/code for:
- One-off component-specific styling
- Layout-specific requirements
- Properties not suitable for centralization

## JFormDesigner Workflow

### Current Approach
1. **Component defaults** are set in FlatLaf.properties
2. **JFormDesigner files** can leave fonts/colors as default or null
3. **Post-initialization** methods apply Theme constants where needed

### Example Pattern

```java
public class MyPanel extends JPanel {
    public MyPanel() {
        initComponents();
        applyThemeStyles(); // Apply Theme constants after JFormDesigner init
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY
        // ...generated code with default/null fonts...
    }

    private void applyThemeStyles() {
        // Apply Theme constants to specific components
        profitLabel.setForeground(Theme.Colors.SUCCESS);
        lossLabel.setForeground(Theme.Colors.DANGER);
    }
}
```

## Migration from UIConstants.java

`UIConstants.java` is **deprecated** but maintained for backward compatibility. It delegates to `Theme.java`.

### For Existing Code
✅ Continue using `UIConstants` - it still works via delegation

### For New Code
✅ Use `Theme.java` directly for better semantic clarity:

```java
// Old style (still works)
color = UIConstants.PROFIT_GREEN_COLOR;

// New style (preferred)
color = Theme.Colors.SUCCESS;
```

## Examples

### Applying Profit/Loss Colors
```java
if (value > 0) {
    label.setForeground(Theme.Colors.SUCCESS);
} else if (value < 0) {
    label.setForeground(Theme.Colors.DANGER);
} else {
    label.setForeground(Theme.Colors.NEUTRAL);
}
```

### Creating a Styled Panel
```java
JPanel panel = new JPanel();
panel.setBorder(Theme.Borders.panelBorderWithPadding(10));
```

### Formatting Numbers
```java
String formatted = Theme.Formatting.PROFIT.format(profitValue);
```

### Setting Semantic Fonts
```java
titleLabel.setFont(Theme.Fonts.LABEL_HEADING);
dataLabel.setFont(Theme.Fonts.LABEL_DEFAULT);
```

## Adding New Styles

### Adding a Global Component Style
Edit `src/main/resources/gui_elements/themes/FlatLaf.properties`:
```properties
# Example: Change all ComboBox fonts
ComboBox.font = IBM Plex Mono 14
```

### Adding a Semantic Color
Edit `src/main/java/constants/Theme.java`:
```java
public static final class Colors {
    // ...existing colors...
    public static final Color WARNING = new Color(255, 165, 0); // Orange
}
```

### Adding a Semantic Font
Edit `src/main/java/constants/Theme.java`:
```java
public static final class Fonts {
    // ...existing fonts...
    public static final Font LABEL_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 10);
}
```

## Best Practices

1. **Use Semantic Names** - `Theme.Colors.SUCCESS` is better than `PROFIT_GREEN_COLOR`
2. **Leverage FlatLaf First** - Use properties for component defaults before adding Java constants
3. **Don't Over-Centralize** - One-off styles can stay inline
4. **Document Intent** - Add comments explaining why a style is centralized
5. **Test Theme Changes** - FlatLaf property changes affect the entire application

## Troubleshooting

### "My JFormDesigner component isn't using the default font"
- Check if a font is explicitly set in the .jfd file
- Set component font to null in JFormDesigner to use defaults

### "I updated FlatLaf.properties but nothing changed"
- Restart the application - properties are loaded at startup
- Verify the property name matches FlatLaf documentation

### "Should I modify JFormDesigner-generated code?"
- NO - Never edit between `//GEN-BEGIN` and `//GEN-END` markers
- Use post-initialization methods instead

## Future: Dark Theme Support

When adding dark theme support:
1. Expand `FlatDarkLaf.properties` with dark-appropriate colors
2. Theme.java semantic constants remain the same
3. Switch themes via `FlatDarkLaf.setup()` in Main.java
