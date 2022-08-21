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

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class MainActivityResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ProductInfoDto> mProducts;

    public MainActivityResultAdapter() {
        mProducts = new ArrayList<>();
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.productImage.setImageBitmap(productInfoDto.getHeroImage());
            productViewHolder.productName.setText(productInfoDto.getName());
            productViewHolder.productDescription.setText((productInfoDto.getTagline()));
            productViewHolder.productPrice.setText(productInfoDto.getDisplayPrice());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_row, parent, false);
        MainActivityResultAdapter.ProductViewHolder productViewHolder = new MainActivityResultAdapter.ProductViewHolder(productView);
        return productViewHolder;
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

}
