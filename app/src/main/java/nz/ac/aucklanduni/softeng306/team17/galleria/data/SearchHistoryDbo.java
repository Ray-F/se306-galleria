package nz.ac.aucklanduni.softeng306.team17.galleria.data;


public class SearchHistoryDbo {
    private String id;
    private String name;
    private String searchTerm;

    public SearchHistoryDbo(String id, String name, String searchTerm) {
        this.id = id;
        this.name = name;
        this.searchTerm = searchTerm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
