package constants;

public enum ViewType {

    HOME("Home"),
    SEARCH("Search"),
    REPORTS("Reports"),
    ACCOUNTS("Accounts");

    public final String title;

    ViewType(String title) {
        this.title = title;
    }

}
