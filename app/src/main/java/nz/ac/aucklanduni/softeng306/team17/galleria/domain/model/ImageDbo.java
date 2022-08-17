package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.list;

public class ImageDbo() {
    private String object;
    private String caption;
    private String credit;

    public ImageDbo(String object, String caption, String credit) {
        this.object = object;
        this.caption = caption;
        this.credit = credit;
    }

    public getObject() { 
        return object;
    }

    public setObject(String object) {
        this.object = object;
    }

    public getCaption() {
        return caption;
    }

    public setCaption(String caption) {
        this.caption = caption;
    }

    public getCredit() {
        return credit;
    }

    public setCredit(String credit) { 
        this.credit = credit;
    }
}

