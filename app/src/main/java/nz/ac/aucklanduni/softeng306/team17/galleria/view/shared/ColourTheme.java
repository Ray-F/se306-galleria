package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class ColourTheme {

    public final int dark;
    public final int normal;
    public final int light;

    private ColourTheme(int dark, int normal, int light) {
        this.dark = dark;
        this.normal = normal;
        this.light = light;
    }

    public static ColourTheme getThemeByCategory(Category category) {
        switch (category) {

            case ALBUM:
                return new ColourTheme(R.color.darkestShadeGray,
                                       R.color.darkestShadeGray,
                                       R.color.mediumShadeGray);
            case AI:
                return new ColourTheme(R.color.darkestShadeOrange,
                                       R.color.darkestShadeOrange,
                                       R.color.mediumShadeOrange);
            case PAINTING:
                return new ColourTheme(R.color.darkestShadeBlue,
                                       R.color.darkestShadeBlue,
                                       R.color.mediumShadeBlue);
            case PHOTOGRAPHIC:
            default:
                return new ColourTheme(R.color.darkestShadeGreen,
                                       R.color.darkestShadeGreen,
                                       R.color.mediumShadeGreen);
        }
    }
}
