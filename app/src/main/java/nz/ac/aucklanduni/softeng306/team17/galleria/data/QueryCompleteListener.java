package nz.ac.aucklanduni.softeng306.team17.galleria.data;


import nz.ac.aucklanduni.softeng306.team17.galleria.view.ProductRowAdapter;

public interface QueryCompleteListener<T> {
    void onComplete(T complete, ProductRowAdapter adapter);
}
