package constants;

public enum ViewType {

    HOME("Home"),
    SEARCH("Search"),
    REPORTS("Reports");

    public final String title;

    ViewType(String title) {
        this.title = title;
    }

}
