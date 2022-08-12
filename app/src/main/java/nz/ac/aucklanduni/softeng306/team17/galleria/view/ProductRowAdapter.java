package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class ProductRowAdapter extends RecyclerView.Adapter<ProductRowAdapter.ProductViewHolder> {

    Context mContext;
    List <ProductInfoDto> mProducts;

    public ProductRowAdapter(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRowAdapter.ProductViewHolder holder, int position) {
        final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        ProductInfoDto productInfoDto = mProducts.get(position);
        productViewHolder.productImage.setImageResource(R.drawable.testRaccoon);
        productViewHolder.productName.setText(productInfoDto.getName());
        productViewHolder.productDescription.setText((productInfoDto.getTagline()));
        productViewHolder.productPrice.setText(productInfoDto.getPriceString());
    }

    @NonNull
    @Override
    public ProductRowAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View productView = LayoutInflater.from(this.mContext).inflate(R.layout.product_row, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(productView);
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
            ProductInfoDto clickedProduct = mProducts.get(getAdapterPosition());
            Toast.makeText(mContext, clickedProduct.getName() + " is clicked in position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

    }

}
