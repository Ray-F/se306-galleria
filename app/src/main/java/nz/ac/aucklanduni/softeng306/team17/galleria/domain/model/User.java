package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

/**
 * An end user in our system.
 */
public class User implements DomainModel {

    /**
     * Unique ID of the user in our system.
     */
    private final String id;

    /**
     * Public display name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;


    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
