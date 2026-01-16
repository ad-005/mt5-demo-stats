package constants;

public enum ViewType {

    HOME("Home"),
    SEARCH("Search"),
    STATS("Statistics"),
    ACCOUNTS("Accounts");

    public final String title;

    ViewType(String title) {
        this.title = title;
    }

}
