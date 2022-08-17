package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.list;

public class SearchHistoryDbo() {
    private String id;
    private String name;
    private String searchTerm;

    public SearchHistoryDbo(String id, String name, String searchTerm) {
        this.id = id;
        this.name = name;
        this.searchTerm = searchTerm;
    }

    public getId() { 
        return id;
    }

    public setId(String id) {
        this.id = id;
    }

    public getName() {
        return name;
    }

    public setName(String name) {
        this.name = name;
    }

    public getSearchTerm() {
        return searchTerm;
    }

    public setSearchTerm(String searchTerm) { 
        this.searchTerm = searchTerm;
    }
}
