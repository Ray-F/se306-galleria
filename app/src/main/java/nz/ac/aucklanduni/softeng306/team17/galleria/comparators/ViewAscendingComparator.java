package nz.ac.aucklanduni.softeng306.team17.galleria.comparators;

import java.util.Comparator;

import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class ViewAscendingComparator implements Comparator<ProductInfoDto> {

    public int compare(ProductInfoDto p1, ProductInfoDto p2) {

        int views1 = p1.getViews();
        int views2 = p2.getViews();

        if (views1 == views2) {
            return 0;
        } else if (views1 > views2) {
            return -1;
        } else {
            return 1;
        }

    }

}
