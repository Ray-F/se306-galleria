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
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;

public class SavedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<ProductInfoDto> mProducts = new ArrayList<ProductInfoDto>();
    ArrayList<Intent> navigationHistory;
    SavedProductsViewModel viewModel;

    public SavedAdapter(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    public SavedAdapter() {

    }

    public void setNavigationHistory(ArrayList<Intent> navHistory) {
        navigationHistory = navHistory;
    }

    public void setViewModel(SavedProductsViewModel vm) {
        this.viewModel = vm;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductInfoDto productInfoDto = mProducts.get(position);

            SavedAdapter.SavedProductViewHolder productViewHolder = (SavedAdapter.SavedProductViewHolder) holder;
            productViewHolder.productImage.setImageBitmap(productInfoDto.getHeroImage());
            productViewHolder.savedProductName.setText(productInfoDto.getName());
            productViewHolder.savedProductDescription.setText((productInfoDto.getTagline()));

            ((SavedProductViewHolder) holder).textArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Clicked on a single product from SAVED VIEW");

                    Intent returnIntent = new Intent(mContext, SavedProductsActivity.class);
                    navigationHistory.add(returnIntent);

                    Intent productIntent = new Intent(mContext, ProductDetailsActivity.class);
                    productIntent.putExtra("productId", mProducts.get(holder.getAbsoluteAdapterPosition()).getId());
                    productIntent.putExtra("NAVIGATION", navigationHistory);

                    mContext.startActivity(productIntent);
                }
            });

        ((SavedProductViewHolder) holder).unsaveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.GalleriaAlert);
                builder.setCancelable(true);
                builder.setTitle("CONFIRM ACTION");
                builder.setMessage("Are you sure you want to unsave this artwork?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                viewModel.unsaveProduct(mProducts.get(holder.getAbsoluteAdapterPosition()).getId());

                                mProducts.remove(holder.getAbsoluteAdapterPosition());
                                System.out.println("SAVED PRODUCT REMOVED");
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("SAVED PRODUCT NOT REMOVED");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();

        View productView = LayoutInflater.from(this.mContext).inflate(R.layout.saved_product, parent, false);
        SavedAdapter.SavedProductViewHolder productViewHolder = new SavedAdapter.SavedProductViewHolder(productView);
        return productViewHolder;
    }

    public void setProducts(List<ProductInfoDto> products) {
        this.mProducts = products;
    }

    @Override
    public int getItemCount() {
        return this.mProducts.size();
    }

    class SavedProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
