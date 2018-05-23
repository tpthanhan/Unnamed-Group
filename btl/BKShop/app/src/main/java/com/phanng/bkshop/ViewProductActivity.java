package com.phanng.bkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phanng.bkshop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.felix.imagezoom.ImageZoom;


public class ViewProductActivity extends AppCompatActivity {

    private Product shownProduct;

    private RecyclerView productImages_rv;
    private RecyclerView.Adapter productImageAdapter;
    private RecyclerView.LayoutManager productImageLayoutManager;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;

    private Button buyButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        productImages_rv = (RecyclerView) findViewById(R.id.images_view_viewproduct);
        productName = (TextView) findViewById(R.id.product_name_viewproduct);
        productPrice = (TextView) findViewById(R.id.product_price_viewproduct);
        productDescription = (TextView) findViewById(R.id.product_description_viewproduct);
        buyButton = (Button) findViewById(R.id.buy_product_action_viewproduct);

        Intent incomingIntent = getIntent();
        shownProduct = (Product) incomingIntent.getSerializableExtra("product");
        int type = incomingIntent.getIntExtra("type",0);
        switch (type) {
            case R.id.navigation_buy:
                buyButton.setVisibility(View.VISIBLE);
                break;
            case R.id.navigation_sell:
                buyButton.setVisibility(View.INVISIBLE);
                break;
            default:
                buyButton.setVisibility(View.INVISIBLE);
        }

        // Set content
        productName.setText(shownProduct.getProductName());
        productPrice.setText(Integer.toString(shownProduct.getProductPrice()));
        productDescription.setText(shownProduct.getProductDescription());

        // RecyclerView setup
        productImageLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        productImages_rv.setLayoutManager(productImageLayoutManager);
        productImageAdapter = new ImageAdapter(shownProduct.getProductImages());
        productImages_rv.setAdapter(productImageAdapter);


    }

    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        List<String> dataset;

        class ViewHolder extends RecyclerView.ViewHolder{
            ImageZoom imageZoom;

            ViewHolder (View view) {
                super(view);
                this.imageZoom = view.findViewById(R.id.image_item_rv);
            }

            void bind(String uri) {
                Picasso.get()
                        .load(uri) // TODO fix here if images loading go wrong
                        .into(imageZoom);
            }
        }

        ImageAdapter(List<String> dataset){
            this.dataset = dataset;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RelativeLayout relativeLayout
                    = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.image_item,parent,false);
            return new ViewHolder(relativeLayout);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(dataset.get(position));
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }
    }


}
