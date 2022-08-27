package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

public enum Category {

    PHOTOGRAPHIC("Photographic Art"),
    AI("AI Art"),
    ALBUM("Album Art"),
    PAINTING("Paintings");

    public final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }
}
