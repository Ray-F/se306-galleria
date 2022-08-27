package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterItemDecoration {

    public static class ColumnModeItemDecoration extends RecyclerView.ItemDecoration {

        private final Context context;
        private final int nColumns;
        private final int spacingDp;

        public ColumnModeItemDecoration(Context context, int nColumns, int spacingDp) {
            this.context = context;
            this.nColumns = nColumns;
            this.spacingDp = spacingDp;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int spacing = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    spacingDp,
                    context.getResources().getDisplayMetrics()
            );

            int position = parent.getChildAdapterPosition(view);

            int column = position % nColumns;

            outRect.left = column * spacing / nColumns;
            outRect.right = spacing - (column + 1) * spacing / nColumns;
            if (position >= nColumns) {
                outRect.top = spacing;
            }
        }
    }

    public static class ListModeItemDecoration extends RecyclerView.ItemDecoration {

        private final Context context;
        private final int spacingDp;

        public ListModeItemDecoration(Context context, int spacingDp) {
            this.context = context;
            this.spacingDp = spacingDp;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int spacing = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    spacingDp,
                    context.getResources().getDisplayMetrics()
            );

            int position = parent.getChildAdapterPosition(view);

            if (position != 0) {
                outRect.top = spacing / 2;
            }
            outRect.bottom = spacing / 2;
        }
    }
}
