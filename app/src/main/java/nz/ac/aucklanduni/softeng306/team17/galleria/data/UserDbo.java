package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;

public class UserDbo {

    public final static String EMAIL_KEY = "email";

    public String id;
    public String name;
    public String email;

    // Empty constructor required for Firestore mapping
    public UserDbo() {}

    public UserDbo(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User toModel() {
        return new User(this.id, this.name, this.email);
    }

    public static UserDbo fromModel(User user) {
        return new UserDbo(user.getId(), user.getName(), user.getEmail());
    }

}

