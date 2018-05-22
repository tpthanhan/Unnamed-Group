package com.phanng.bkshop;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phanng.bkshop.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> dataset;

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView productName;
            TextView productPrice;

        ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            this.imageView = linearLayout.findViewById(R.id.product_image_listitem);
            this.productName = linearLayout.findViewById(R.id.product_name_listitem);
            this.productPrice = linearLayout.findViewById(R.id.product_price_listitem);

        }


        }

    ProductAdapter(List<Product> dataset){
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.product_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(linearLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = dataset.get(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(Integer.toString(product.getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
