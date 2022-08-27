package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductClickListener;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;

public class SavedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ProductInfoDto> mProducts = new ArrayList<>();

    private ProductClickListener onUnsaveClickListener;
    private ProductClickListener onItemClickListener;

    public void setOnUnsaveClickListener(ProductClickListener listener) {
        this.onUnsaveClickListener = listener;
    }

    public void setOnItemClickListener(ProductClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

            SavedAdapter.SavedProductViewHolder productViewHolder = (SavedAdapter.SavedProductViewHolder) holder;
            productViewHolder.productImage.setImageBitmap(productInfoDto.getHeroImage());
            productViewHolder.savedProductName.setText(productInfoDto.getName());
            productViewHolder.savedProductDescription.setText((productInfoDto.getTagline()));

            ((SavedProductViewHolder) holder).textArea.setOnClickListener(view -> {
                System.out.println("Clicked on a single product from SAVED VIEW");
                String productId = mProducts.get(holder.getAbsoluteAdapterPosition()).getId();

                onItemClickListener.onClick(productId);
            });

        ((SavedProductViewHolder) holder).unsaveIcon.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.GalleriaAlert);
            builder.setCancelable(true);
            builder.setTitle("CONFIRM ACTION");
            builder.setMessage("Are you sure you want to unsave this artwork?");
            builder.setPositiveButton("Confirm",
                                      (dialog, which) -> {
                                          String productId = mProducts.get(holder.getAbsoluteAdapterPosition()).getId();
                                          onUnsaveClickListener.onClick(productId);
                                      });
            builder.setNegativeButton(android.R.string.cancel,
                                      (dialog, which) -> System.out.println("SAVED PRODUCT NOT REMOVED"));
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        View productView = LayoutInflater.from(this.mContext).inflate(R.layout.saved_product, parent, false);
        return new SavedProductViewHolder(productView);
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    static class SavedProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImage;
        TextView savedProductName;
        TextView savedProductDescription;
        LinearLayout textArea;
        ImageView unsaveIcon;

        public SavedProductViewHolder(View inputView) {
            super(inputView);
            productImage = inputView.findViewById(R.id.ProductImage);
            savedProductName = inputView.findViewById(R.id.SavedProductName);
            savedProductDescription = inputView.findViewById(R.id.SavedProductDescription);
            unsaveIcon = inputView.findViewById(R.id.UnsaveHeart);
            textArea = inputView.findViewById(R.id.savedTextArea);

        }

        @Override
        public void onClick(View v) {
            // Ignore click
        }

    }
}
