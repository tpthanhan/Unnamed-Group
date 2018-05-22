package com.phanng.bkshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Button;

import com.phanng.bkshop.model.Order;
import com.phanng.bkshop.model.Product;
import com.phanng.bkshop.restutil.RestServiceClient;

import org.apache.commons.math3.util.Pair;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mainActivity = this;

    private SearchView searchBarView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FloatingActionButton addProductButton;
    private FloatingActionButton.OnClickListener onFABClickListener
            = new FloatingActionButton.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mainActivity,AddProductActivity.class);
            startActivity(intent);
        }
    };


    //Store data
    private List<Product> productData = null;
    private List<Product> myProductData = null;
    private List<Order> orderData;

    private int lastNavigationItemSelected = R.id.navigation_buy;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_buy:
                    if (lastNavigationItemSelected != R.id.navigation_buy) {
                        searchBarView.setVisibility(View.VISIBLE);
                        addProductButton.setVisibility(View.INVISIBLE);
                        showProduct();
                    }
                    lastNavigationItemSelected = R.id.navigation_buy;
                    return true;
                case R.id.navigation_sell:
                    if (lastNavigationItemSelected != R.id.navigation_sell) {
                        searchBarView.setVisibility(View.VISIBLE);
                        addProductButton.setVisibility(View.VISIBLE);

                        showMyProduct();
                    }
                    lastNavigationItemSelected = R.id.navigation_sell;
                    return true;
                case R.id.navigation_purchased:
                    if (lastNavigationItemSelected != R.id.navigation_purchased) {
                        searchBarView.setVisibility(View.VISIBLE);
                        addProductButton.setVisibility(View.INVISIBLE);
                    }
                    lastNavigationItemSelected = R.id.navigation_purchased;
                    return true;

            }
            return false;
        }
    };

    private Button refreshButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addProductButton = (FloatingActionButton) findViewById(R.id.add_product_button);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Use a layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // create an adapter
        // (done in getProductListAsyncTask)

        addProductButton.setVisibility(View.INVISIBLE);
        showProduct();

        addProductButton.setOnClickListener(onFABClickListener);


    }

    private void showProduct() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        if (productData == null) {
            AsyncTask<String,Void,List<Product>> task = new GetProductListAsyncTask();
            task.execute("get-product");
        } else {
            adapter = new ProductAdapter(productData);
            recyclerView.setAdapter(adapter);
        }

    }

    private void showMyProduct() {
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        if (myProductData == null) {
            AsyncTask<String,Void,List<Product>> task = new GetMyProductAsyncTask();
            task.execute("get-my-product");
        } else {

            adapter = new ProductAdapter(myProductData);
            recyclerView.setAdapter(adapter);
        }
    }

    private void showOrder() {

    }

    public class GetProductListAsyncTask extends AsyncTask<String,Void,List<Product>> {

        @Override
        protected List<Product> doInBackground(String... strings) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String token = sharedPref.getString("token", null);

            RestServiceClient client = RestServiceClient.getInstance();
            Pair<String,List<Product>> res;

            res = client.getProductList(token);


            return res.getSecond();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            if (products != null) {
                productData = products;
                adapter = new ProductAdapter(productData);
                recyclerView.setAdapter(adapter);
            } else {
                // Error
            }
        }
    }

    public class GetMyProductAsyncTask extends AsyncTask<String,Void,List<Product>> {

        @Override
        protected List<Product> doInBackground(String... strings) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String token = sharedPref.getString("token", null);

            RestServiceClient client = RestServiceClient.getInstance();
            Pair<String,List<Product>> res;
            res = client.getMyProduct(token);

            return res.getSecond();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            if (products != null) {
                myProductData = products;
                adapter = new ProductAdapter(myProductData);
                recyclerView.setAdapter(adapter);
            } else {
                // Error
            }
        }
    }

    public class GetOrderAsyncTask extends AsyncTask<Void,Void,List<Order>> {
        @Override
        protected List<Order> doInBackground(Void... voids) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            String token = sharedPref.getString("token", null);

            RestServiceClient client = RestServiceClient.getInstance();
            Pair<String, List<Order>> res = client.getOrder(token);
            return res.getSecond();
        }

        @Override
        protected void onPostExecute(List<Order> orders) {
            // TODO create new adapter for Order
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);

        MenuItem searchBar = menu.findItem(R.id.search_bar);
        searchBarView =
                (SearchView) searchBar.getActionView();
        MenuItem refresh_button = menu.findItem(R.id.refresh_button_menu);
        refreshButton = (Button) refresh_button.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button_menu:
                switch (lastNavigationItemSelected) {
                    case R.id.navigation_buy:
                        productData = null;
                        showProduct();
                        return true;
                    case R.id.navigation_sell:
                        myProductData = null;
                        showMyProduct();
                        return true;
                    case R.id.navigation_purchased:
                        orderData = null;
                        showOrder();
                        return true;
                }
            default:
                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onBackPressed() {
        if (!searchBarView.isIconified()) {
            searchBarView.onActionViewCollapsed();
        }
        else {
            this.finishAffinity();
        }

    }

}
