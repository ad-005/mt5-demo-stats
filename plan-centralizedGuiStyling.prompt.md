## Plan: Centralized GUI Styling Architecture for Java Swing with JFormDesigner

This plan establishes a structured approach for centralizing GUI styling in a FlatLaf-based Swing application. Currently, styling is fragmented: some constants exist in [UIConstants.java](D:\DemoAnalysis\demoanalysis_testing\src\main\java\constants\UIConstants.java), some FlatLaf customizations in properties files, and many hardcoded values inline in JFormDesigner-generated code. The recommended architecture leverages FlatLaf's properties-based theming system for component-level defaults, while expanding `UIConstants` into a semantic theme API for application-specific styles.

### Current State Analysis

| Area | Location | Issue |
|------|----------|-------|
| Fonts | Hardcoded in 14 `.jfd` files and generated Java code | Same font "IBM Plex Mono" duplicated 20+ times with varying sizes |
| Colors | Mixed in `UIConstants`, `.jfd` files, and inline code | Progress bar colors duplicated, semantic colors underutilized |
| FlatLaf | [themes/FlatLaf.properties](D:\DemoAnalysis\demoanalysis_testing\src\main\resources\gui_elements\themes\FlatLaf.properties) | Only minimal customization (ToggleButton styles), largely unused |
| Renderers | [ProfitCellRenderer.java](D:\DemoAnalysis\demoanalysis_testing\src\main\java\gui_elements\renderers\ProfitCellRenderer.java) | Uses `UIConstants` correctly – good pattern to follow |

### Steps

1. **Expand FlatLaf properties files** in [themes/](D:\DemoAnalysis\demoanalysis_testing\src\main\resources\gui_elements\themes) to define global component defaults (fonts, table header colors, progress bar styles, panel borders) – this eliminates repetition in `.jfd` files.

2. **Restructure [UIConstants.java](D:\DemoAnalysis\demoanalysis_testing\src\main\java\constants\UIConstants.java)** into a `Theme.java` class with nested categories (`Theme.Font`, `Theme.Color`, `Theme.Border`) for semantic application-specific styles not covered by FlatLaf.

3. **Configure JFormDesigner** to use client properties and reference centralized styles – set component fonts/colors to `null` or default in `.jfd`, then apply semantic styles via FlatLaf `[style]` rules or post-initialization in `initCustomStyles()` methods.

4. **Migrate hardcoded values** from generated `initComponents()` code to FlatLaf properties where possible, and to `Theme.java` constants for application-specific semantic values (e.g., `PROFIT_GREEN_COLOR`, `SESSION_LABEL_FONT`).

5. **Create style-applying utility methods** in a new `StyleUtils.java` class for patterns that require runtime logic (e.g., applying conditional styles, creating styled borders).

### Further Considerations

1. **JFormDesigner workflow**: Should fonts/colors be removed from `.jfd` files entirely (FlatLaf-first), or kept for visual design preview? *Recommend: Use FlatLaf defaults, add `[style]` rules for specific components, document workaround for designer preview.*

2. **Dark theme support**: Expand to [FlatDarkLaf.properties](D:\DemoAnalysis\demoanalysis_testing\src\main\resources\gui_elements\themes\FlatDarkLaf.properties) now, or defer? *Recommend: Define light theme first, then duplicate/adjust for dark.*

3. **Semantic vs. component naming**: Should `Theme.java` use component names (`TABLE_HEADER_FONT`) or semantic names (`HEADING_FONT`)? *Recommend: Semantic naming for flexibility – `Fonts.HEADING_MEDIUM`, `Colors.SUCCESS`, etc.*
