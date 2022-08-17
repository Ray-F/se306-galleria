package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

public class ImageDbo {
    private String object;
    private String caption;
    private String credit;

    public ImageDbo(String object, String caption, String credit) {
        this.object = object;
        this.caption = caption;
        this.credit = credit;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}

