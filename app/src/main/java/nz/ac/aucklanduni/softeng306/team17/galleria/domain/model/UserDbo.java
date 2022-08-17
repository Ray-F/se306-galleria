package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.list;

public class UserDbo() {
    private String id;
    private String name;
    private String email;

    public UserDbo(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    public getEmail() {
        return email;
    }

    public setEmail(String email) { 
        this.email = email;
    }
}

