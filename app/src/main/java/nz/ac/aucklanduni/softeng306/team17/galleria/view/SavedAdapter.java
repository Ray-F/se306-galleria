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

public class SavedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ProductInfoDto> mProducts;

    public SavedAdapter(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

            SavedAdapter.SavedProductViewHolder productViewHolder = (SavedAdapter.SavedProductViewHolder) holder;
            productViewHolder.productImage.setImageResource(productInfoDto.getHeroImage());
            productViewHolder.productName.setText(productInfoDto.getName());
            productViewHolder.productDescription.setText((productInfoDto.getTagline()));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        View productView = LayoutInflater.from(this.mContext).inflate(R.layout.saved_product, parent, false);
        SavedAdapter.SavedProductViewHolder productViewHolder = new SavedAdapter.SavedProductViewHolder(productView);
        return productViewHolder;
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    class SavedProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView productName;
        TextView productDescription;

        public SavedProductViewHolder(View inputView) {
            super(inputView);
            productImage = inputView.findViewById(R.id.ProductImage);
            productName = inputView.findViewById(R.id.ProductName);
            productDescription = inputView.findViewById(R.id.ProductDescription);
        }

        @Override
        public void onClick(View v) {
            // What to do when the view item is clicked
            ProductInfoDto clickedProduct = mProducts.get(getAbsoluteAdapterPosition());
            Toast.makeText(mContext, clickedProduct.getName() + " is clicked in position " + getAbsoluteAdapterPosition(), Toast.LENGTH_SHORT).show();
        }

    }
}
