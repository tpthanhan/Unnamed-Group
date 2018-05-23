package com.phanng.bkshop;

import android.content.Context;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.phanng.bkshop.model.Product;
import com.phanng.bkshop.restutil.RestServiceClient;
import com.squareup.picasso.Picasso;

import org.apache.commons.math3.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.felix.imagezoom.ImageZoom;
import pyxis.uzuki.live.naraeimagepicker.Constants;
import pyxis.uzuki.live.naraeimagepicker.NaraeImagePicker;
import pyxis.uzuki.live.naraeimagepicker.impl.OnPickResultListener;
import pyxis.uzuki.live.naraeimagepicker.item.PickerSettingItem;
import pyxis.uzuki.live.naraeimagepicker.item.enumeration.ViewMode;

public class AddProductActivity extends AppCompatActivity {

    private Context addProductActivity;

    private RecyclerView imagesRecyclerView;
    private RecyclerView.LayoutManager imagesLayoutManager;
    private RecyclerView.Adapter imagesAdapter;

    private Button addPicturesButton;

    //Images data
    ArrayList<String> imageListData;

    //Images uri data
    ArrayList<String> imageUriData = new ArrayList<>();

    List<String> categoryList;

    // Ref to all forms
    TextInputEditText productName;
    TextInputEditText productPrice;
    Spinner productCategory;
    TextInputEditText productTag;
    TextInputEditText productQuantity;
    TextInputEditText productDescription;

    // Add button
    Button addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setTitle("Add your product");

        addProductActivity = this;

        addPicturesButton = (Button) findViewById(R.id.button_picture_add_addproduct);
        addPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickerSettingItem item = new PickerSettingItem();
                item.setPickLimit(Constants.LIMIT_UNLIMITED);
                item.setViewMode(ViewMode.FolderView);
                item.setEnableUpInParentView(true);
                item.setDisableZoomMode(true);

                NaraeImagePicker.instance.start(AddProductActivity.this, item, new OnPickResultListener() {
                    @Override
                    public void onSelect(int resultCode, @NotNull ArrayList<String> imageList) {
                        if (resultCode == NaraeImagePicker.PICK_SUCCESS) {
                            imageListData = imageList;
                            imagesAdapter = new ImageAdapter(imageList);
                            imagesRecyclerView.setAdapter(imagesAdapter);
                            imagesLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                            imagesRecyclerView.setLayoutManager(imagesLayoutManager);
                        } else {
                            Toast.makeText(AddProductActivity.this, "No image chosen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        imagesRecyclerView = (RecyclerView) findViewById(R.id.images_rv_addproduct);

        // Get list of categories from the server
        getCategory();

        // Assign all forms references
        productName = (TextInputEditText) findViewById(R.id.product_name_addproduct);
        productCategory = (Spinner) findViewById(R.id.product_category_addproduct);
        productPrice = (TextInputEditText) findViewById(R.id.product_price_addproduct);
        productTag = (TextInputEditText) findViewById(R.id.product_tag_addproduct);
        productQuantity = (TextInputEditText) findViewById(R.id.product_quantity_addproduct);
        productDescription = (TextInputEditText) findViewById(R.id.product_description_addproduct);

        // Add product button
        addProductButton = (Button) findViewById(R.id.button_add_addproduct);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAll() == false) {
                    return;
                } else {
                    new AsyncTask<Void,Void,Boolean>() {
                        @Override
                        protected Boolean doInBackground(Void... voids) {

                            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            String token = sharedPref.getString("token", null);

                            RestServiceClient client = RestServiceClient.getInstance();
                            Pair<String,String> res = client.createProduct(token,createProductFromActivity());
                            if (res.getFirst().equals("FAIL")) {
                                return false;
                            }
                            return true;
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            if (aBoolean == false){
                                Toast.makeText(AddProductActivity.this,"Add product failed",Toast.LENGTH_LONG)
                                        .show();
                                return;
                            }
                            Toast.makeText(AddProductActivity.this,"Add product successful !",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }.execute();
                }


            }
        });
    }

    class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        ArrayList<String> dataset;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageZoom imageZoom;

            ViewHolder (View view) {
                super(view);
                this.imageZoom = view.findViewById(R.id.image_item_rv);
            }

            public void bind(String uri) {
                Picasso.get()
                        .load("file://"+uri)
                        .into(imageZoom);
            }
        }

        ImageAdapter(ArrayList<String> dataset){
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

    private void getCategory(){
        new AsyncTask<Void,Void,List<String>>(){
            @Override
            protected List<String> doInBackground(Void... voids) {
                RestServiceClient client = RestServiceClient.getInstance();
                Pair<String,List<String>> res = client.getCategoryList();
                client = null;
                if (!res.getFirst().equals("FAIL")){
                    return res.getSecond();
                } else {
                    return null;
                }

            }

            @Override
            protected void onPostExecute(List<String> res) {
                if (res == null) {
                    Toast.makeText(AddProductActivity.this,"Retrieving categories failed",Toast.LENGTH_LONG)
                            .show();
                }
                categoryList = res;
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddProductActivity.this,
                        android.R.layout.simple_spinner_item,categoryList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                productCategory.setAdapter(adapter);
            }
        }.execute();


    }

    private boolean validateAll() {
        boolean res = true;
        if (productName.getTextSize() == 0) {
            res = false;
            productName.setError("Cannot leave blank");
        }
        // TODO check spinner Category
        if (productPrice.getText().toString().equals("")) {
            res = false;
            productPrice.setError("Cannot leave blank");
        }
        if (productQuantity.getText().toString().equals("")) {
            res = false;
            productQuantity.setError("Cannot leave blank");
        }
        if (imageListData == null){
            res = false;
            Toast.makeText(AddProductActivity.this,"Please choose some pictures",
                    Toast.LENGTH_SHORT).show();
        } else if (imageListData.size() == 0){
            res = false;
            Toast.makeText(AddProductActivity.this,"Please choose some pictures",
                    Toast.LENGTH_SHORT).show();
        }
        if (productDescription.getText().toString().equals("")) {
            res = false;
            productDescription.setError("Cannot leave blank");
        }
        return res;
    }

    // Should only be called after validating all forms
    private Product createProductFromActivity () {
        String name = productName.getText().toString();
        int price = Integer.parseInt(productPrice.getText().toString());
        String category = productCategory.getSelectedItem().toString();
        String tag = productTag.getText().toString();
        int quantity = Integer.parseInt(productQuantity.getText().toString());
        String description = productDescription.getText().toString();

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString("token", null);
        RestServiceClient client = RestServiceClient.getInstance();
        for (String i : imageListData){
            Pair<String,String> res = client.uploadImage(token,client.encodeToString(i));
            imageUriData.add(res.getSecond());
        }

        List<String> tagList = new ArrayList<>();
        tagList.add(tag);
        return new Product(name,category,price,imageUriData,description,tagList,quantity);
    }

    private void uploadImages(){
         new AsyncTask<Void,Void,List<String>>(){
            @Override
            protected List<String> doInBackground(Void... voids) {
                List<String> result = new ArrayList<>();
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String token = sharedPref.getString("token", null);
                RestServiceClient client = RestServiceClient.getInstance();
                for (String i : imageListData){
                    Pair<String,String> res = client.uploadImage(token,client.encodeToString(i));
                    result.add(res.getSecond());
                    Log.d("UPLOAD",res.getSecond());
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                Log.d("UPLOAD","postExec" + strings);
                imageUriData = (ArrayList<String>) strings;
            }
        }.execute();

    }
}
