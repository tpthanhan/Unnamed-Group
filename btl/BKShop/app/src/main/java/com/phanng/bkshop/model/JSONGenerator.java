package com.phanng.bkshop.model;

/*
 * Convert JSON to Object
 */

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONGenerator {
    public Product generateProduct(JSONObject object) throws MalformedURLException, JSONException {
        String _id = object.getString("_id");
        String dateCreated = String.valueOf(object.get("dateCreated"));
        String createdBy = object.getString("createdBy");
        String dateModified = String.valueOf(object.get("dateModified"));
        String productName;
        if (object.get("productName").toString()!="null")
            productName = object.getString("productName");
        else productName="";

        int productPrice;
        if (object.get("productPrice").toString()!="null")
        productPrice = object.getInt("productPrice");
        else productPrice=0;

        String productDescription;
        if (object.get("productDescription").toString()!="null")
        productDescription = object.getString("productDescription");
        else productDescription="";

        String productCategory;
        if (object.get("productCategory").toString()!="null")
        productCategory = object.getString("productCategory");
        else productCategory="";

        ArrayList<String> productTags = new ArrayList<String>();
        if (object.get("productTags").toString()!="null") {
            JSONArray temp = object.getJSONArray("productTags");

            for (int i = 0; i < temp.length(); i++) {
                productTags.add(temp.get(i).toString());
            }
        }
        int status = object.getInt("status");

        ArrayList<String> productImages = new ArrayList<String>();
        if (object.get("productImages").toString()!="null") {
            JSONArray temp = object.getJSONArray("productImages");

            for (int i = 0; i < temp.length(); i++) {
                productImages.add(temp.get(i).toString());
            }
        }

        int productQuantity;
        if (object.get("productQuantity").toString()!="null")
        productQuantity = object.getInt("productQuantity");
        else productQuantity=0;

        Product product = new Product(_id, productName, productCategory, productPrice, productImages,
                productDescription, productTags, productQuantity, dateModified, createdBy, dateCreated, status);

        return product;
    }

    /**
     *
     */
    public User generateUser(JSONObject object) {
        // TODO Auto-generated method stub
        User user = null;
        try {
            String _id = object.getString("_id");
            String email = object.getString("email");
            String avatar = object.getString("avatar");
            String fullName = object.getString("fullName");
            String phoneNumber = object.getString("phoneNumber");
            String address = object.getString("address");
            String dateOfBirth = object.getString("dateOfBirth");
            Long dateCreated = object.getLong("dateCreated");
            Long dateModified = object.getLong("dateModified");
            user = new User(_id, email, fullName, phoneNumber, address, dateOfBirth, avatar, dateCreated,
                    dateModified);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;

    }

    public Order generateOrder(JSONObject object) {
        Order order = null;
        try {
            String _id = object.getString("_id");
            String productID = object.getString("productID");
            int productQuantity = object.getInt("productQuantity");
            Long dateCreated = object.getLong("dateCreated");
            Long dateModified = object.getLong("dateModified");
            String createdBy = object.getString("createdBy");
            int status = object.getInt("status");
            order = new Order(_id, productID, productQuantity, dateCreated, dateModified, createdBy, status);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return order;
    }
}
