package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class SimpleListInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ProductInfoDto> mProducts = new ArrayList<>();

    // Default layout to list view
    ListViewLayoutMode listViewLayoutMode = ListViewLayoutMode.LIST;

    // Default listener which does nothing
    OnItemClickListener itemClickListener = (id) -> {};

    public void setProducts(List<ProductInfoDto> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        notifyDataSetChanged();
    }

    public void setLayoutMode(ListViewLayoutMode mode) {
        this.listViewLayoutMode = mode;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

        boolean noSpecialTextAvailable = productInfoDto.getSpecialText().isEmpty();

        switch (listViewLayoutMode) {
            case GRID:
                ProductGridViewHolder productGridViewHolder = (ProductGridViewHolder) holder;
                productGridViewHolder.productImageGrid.setImageBitmap(productInfoDto.getHeroImage());
                productGridViewHolder.productNameGrid.setText(productInfoDto.getName());
                productGridViewHolder.specialTextGrid.setText(noSpecialTextAvailable ? productInfoDto.getTagline() : productInfoDto.getSpecialText());

                break;

            case LIST:
            default:
                ProductViewHolder productViewHolder = (ProductViewHolder) holder;
                productViewHolder.productImage.setImageBitmap(productInfoDto.getHeroImage());
                productViewHolder.productName.setText(productInfoDto.getName());
                productViewHolder.productDescription.setText((productInfoDto.getTagline()));
                productViewHolder.productPrice.setText(productInfoDto.getDisplayPrice());
                productViewHolder.specialTextRow.setText(productInfoDto.getSpecialText());

                if (noSpecialTextAvailable) {
                    productViewHolder.specialTextRow.setVisibility(View.GONE);
                }
                break;
        }

        holder.itemView.setOnClickListener(view -> {
            String productId = mProducts.get(holder.getAbsoluteAdapterPosition()).getId();
            itemClickListener.onClick(productId);
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        if (viewType == R.layout.product_row) {
            View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_row, parent, false);
            return new ProductViewHolder(productView);
        } else {
            View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_grid, parent, false);
            return new ProductGridViewHolder(productView);
        }
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.listViewLayoutMode == ListViewLayoutMode.LIST ? R.layout.product_row : R.layout.product_grid;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        TextView specialTextRow;

        public ProductViewHolder(View inputView) {
            super(inputView);
            productImage = inputView.findViewById(R.id.ProductImage);
            productName = inputView.findViewById(R.id.ProductName);
            productDescription = inputView.findViewById(R.id.ProductDescription);
            productPrice = inputView.findViewById(R.id.ProductPrice);
            specialTextRow = inputView.findViewById(R.id.RowSpecialText);
        }

    }

    static class ProductGridViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageGrid;
        TextView productNameGrid;
        TextView specialTextGrid;

        public ProductGridViewHolder(View inputView) {
            super(inputView);
            productImageGrid = inputView.findViewById(R.id.ProductImageGrid);
            productNameGrid = inputView.findViewById(R.id.ProductNameGrid);
            specialTextGrid = inputView.findViewById(R.id.GridSpecialText);
        }

    }

    /**
     * Listener for item button click.
     */
    static interface OnItemClickListener {
        void onClick(String productId);
    }

}
