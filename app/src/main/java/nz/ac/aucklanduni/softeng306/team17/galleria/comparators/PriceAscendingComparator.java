package nz.ac.aucklanduni.softeng306.team17.galleria.comparators;

import java.util.Comparator;

import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class PriceAscendingComparator implements Comparator<ProductInfoDto> {

    public int compare(ProductInfoDto p1, ProductInfoDto p2) {
        Long price1 = Long.parseLong(p1.getPrice().substring(1, p1.getPrice().length()));
        Long price2 = Long.parseLong(p2.getPrice().substring(1, p2.getPrice().length()));
        if (price1 == price2) {
            return 0;
        } else if (price1 > price2) {
            return 1;
        } else {
            return -1;
        }
    }
}
