package nz.ac.aucklanduni.softeng306.team17.galleria.comparators;

import java.util.Comparator;

import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class NameDescendingComparator implements Comparator<ProductInfoDto> {

    public int compare(ProductInfoDto p1, ProductInfoDto p2) {
        String name1 = p1.getName();
        String name2 = p2.getName();

        boolean firstBigger = true;
        int minLength = name1.length() < name2.length() ? name1.length() : name2.length();

        for (int i = 0; i < minLength; i++) {
            if ( ((int) name1.charAt(i)) < ((int) name2.charAt(i)) ) {
                firstBigger = false;
                break;
            } else if ( ((int) name1.charAt(i)) > ((int) name2.charAt(i)) ) {
                firstBigger = true;
                break;
            }
        }

        if (firstBigger) {
            return -1;
        } else {
            return 1;
        }

    }
}
