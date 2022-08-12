package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class ProductRowAdapter extends RecyclerView.Adapter<ProductRowAdapter.ProductViewHolder> {

    Context Context;
    List <ProductInfoDto> Products;
    OnElementClickListener ElementClickListener;

    public ProductRowAdapter(Context context, List<ProductInfoDto> products, OnElementClickListener onElementClickListener) {
        this.Context = context;
        this.Products = products;
        this.ElementClickListener = onElementClickListener
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRowAdapter.ProductViewHolder holder, int position) {
        final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        ProductInfoDto productInfoDto = Products.get(position);
        productViewHolder.productImage.setImageResource(productInfoDto.getHeroImage());
        productViewHolder.productName.setText(productInfoDto.getName());
        productViewHolder.productDescription.setText((productInfoDto.getTagline()));
        productViewHolder.productPrice.setText(productInfoDto.getPriceString());
    }

    @NonNull
    @Override
    public ProductRowAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // ProductViewHolder productViewHolder = null;
        View productView = LayoutInflater.from(this.Context).inflate(R.layout.product_row, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(productView);
        return productViewHolder;
    }

    @Override
    public int getItemCount() {
        return this.Products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
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

    }

}
