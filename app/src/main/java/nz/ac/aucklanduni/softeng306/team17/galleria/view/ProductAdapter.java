package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List <ProductInfoDto> mProducts;
    Boolean mIsListViewEnabled = true;

    public ProductAdapter() {

    }

    public ProductAdapter(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

        if (mIsListViewEnabled) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.productImage.setImageResource(productInfoDto.getHeroImage());
            productViewHolder.productName.setText(productInfoDto.getName());
            productViewHolder.productDescription.setText((productInfoDto.getTagline()));
            String priceString = String.format("%.2f", (productInfoDto.getPrice())) + " " + productInfoDto.getCurrencyCode().toString();
            productViewHolder.productPrice.setText(priceString);
        } else {
            ProductGridViewHolder productGridViewHolder = (ProductGridViewHolder) holder;
            productGridViewHolder.productImageGrid.setImageResource(productInfoDto.getHeroImage());
            productGridViewHolder.productNameGrid.setText(productInfoDto.getName());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        if (viewType == R.layout.product_row) {
            View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_row, parent, false);
            ProductViewHolder productViewHolder = new ProductViewHolder(productView);
            return productViewHolder;
        } else {
            View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_grid, parent, false);
            ProductGridViewHolder productGridViewHolder = new ProductGridViewHolder(productView);
            return productGridViewHolder;
        }
    }

    public void toggleDisplayLayoutMode() {
        mIsListViewEnabled = !mIsListViewEnabled;
        notifyDataSetChanged();
    }

    public Boolean getmIsListViewEnabled() {
        return mIsListViewEnabled;
    }

    @Override
    public int getItemViewType(int position) {
        return mIsListViewEnabled ? R.layout.product_row : R.layout.product_grid;
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;

        public ProductViewHolder(View inputView) {
            super(inputView);
            productImage = inputView.findViewById(R.id.ProductImage);
            productName = inputView.findViewById(R.id.ProductName);
            productDescription = inputView.findViewById(R.id.ProductDescription);
            productPrice = inputView.findViewById(R.id.ProductPrice);
        }

        @Override
        public void onClick(View v) {
            // What to do when the view item is clicked
            ProductInfoDto clickedProduct = mProducts.get(getAbsoluteAdapterPosition());
            Toast.makeText(mContext, clickedProduct.getName() + " is clicked in position " + getAbsoluteAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

    }

    class ProductGridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImageGrid;
        TextView productNameGrid;

        public ProductGridViewHolder(View inputView) {
            super(inputView);
            productImageGrid = inputView.findViewById(R.id.ProductImageGrid);
            productNameGrid = inputView.findViewById(R.id.ProductNameGrid);
        }

        @Override
        public void onClick(View v) {
            // What to do when the view item is clicked
            ProductInfoDto clickedProduct = mProducts.get(getAbsoluteAdapterPosition());
            Toast.makeText(mContext, clickedProduct.getName() + " is clicked in position " + getAbsoluteAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

    }

}
