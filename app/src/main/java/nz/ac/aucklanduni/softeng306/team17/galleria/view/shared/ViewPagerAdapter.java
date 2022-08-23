package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;


import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

public class ViewPagerAdapter extends PagerAdapter {

    private final int mainImageViewId;
    private final int layout;
    private Context context;

    private LayoutInflater mLayoutInflater;

    private List<Bitmap> images;

    public ViewPagerAdapter(Context context, List<Bitmap> images, int layout, int mainImageViewId) {
        this.context = context;
        this.images = images;
        this.layout = layout;
        this.mainImageViewId = mainImageViewId;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setImages(List<Bitmap> bitmaps) {

        this.images = bitmaps;
    }

    @Override
    public int getItemPosition(Object object) {
        // Overriding this method forces adapter to reload views when dataset changed. Requires further testing.
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        // return the number of images
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        // inflates activity slideshow
        View itemView = mLayoutInflater.inflate(layout, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(mainImageViewId);

        imageView.setImageBitmap(images.get(position));

        imageView.setAdjustViewBounds(false);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }


    @NonNull
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
