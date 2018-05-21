/**
 * Trả về 1 cặp giá trị. Giá trị 1 là status, 2 là tùy vào loại method gồm:
 *
 * Register: access_token
 * Login: access_token
 * getProfile: User
 * forgetPassword: "SUCCESS"
 * createProduct: "SUCCESS"
 * getProductById: Product object
 * getCategoryList: List<String>
 * uploadImage: "SUCCESS"
 * BuyProduct: Order Object
 * getProductList: List<Product>
 * cancelProduct: "SUCCESS"
 * getMyProduct: List<Product>
 * getOrder: List<Order>
 *
 *
 *
 */
package com.phanng.bkshop.restutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.Gson;

import com.phanng.bkshop.model.JSONGenerator;
import com.phanng.bkshop.model.Order;
import com.phanng.bkshop.model.Product;
import com.phanng.bkshop.model.User;

/**
 * @author tptha
 *
 */
public class RestServiceClient {
    private static RestServiceClient instance = null;
    private final String homeUrl = "http://103.221.220.210:8888";

    private RestServiceClient() {
    }

    public static RestServiceClient getInstance() {
        if (instance == null) {
            instance = new RestServiceClient();
        }
        return instance;
    }

    /**
     * @param deString
     * @param method
     * @return
     * @throws IOException
     */
    private HttpURLConnection createConnection(String deString, String method) throws IOException {
        URL url = new URL(deString);
        HttpURLConnection conn;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        //conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    private String readResponse(HttpURLConnection connection) throws UnsupportedEncodingException, IOException {
        // get output
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF8"));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();
        return response.toString();
    }

    /**
     * @param user
     * @return tuple <String status, String message>
     */
    public Pair<String, String> register(User user) {
        Pair<String, String> result = new Pair<>("FAIL", "");
        String abc = homeUrl + "/api/register";
        try {
            // create connection
            HttpURLConnection connection = createConnection(abc, "POST");

            // create input
            Gson gson = new Gson();
            String input = gson.toJson(user);
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);

            result = new Pair<String, String>(obj.getString("status"), obj.getString("message"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param email
     * @param password
     * @return
     */
    public Pair<String, String> login(String email, String password) {
        Pair<String, String> result = new Pair<>("FAIL", "");
        String abc = homeUrl + "/api/login";
        // create connection
        try {
            HttpURLConnection connection = createConnection(abc, "POST");
            String input = "{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            result = new Pair<String, String>(obj.getString("status"), obj.getJSONObject("message").getString("token"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    public Pair<String, User> getProfile(String access_token) {
        Pair<String, User> result = null;
        String abc = homeUrl + "/api/me";
        try {
            HttpURLConnection connection = createConnection(abc, "GET");
            connection.setRequestProperty("Authorization", access_token);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            if (obj.getString("status").equals("SUCCESS")) {
                JSONObject userJson = obj.getJSONObject("message");
                JSONGenerator generator = new JSONGenerator();
                User user = generator.generateUser(userJson);
                result = new Pair<String, User>(obj.getString("status"), user);
            } else
                result = new Pair<String, User>(obj.getString("status"), null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<String, String> forgetPassword(String email) {
        Pair<String, String> result = new Pair<>("FAIL", "");
        String abc = homeUrl + "/api/forget-password";
        try {
            HttpURLConnection connection = createConnection(abc, "POST");

            String input = "{\"email\":\"" + email + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            if (obj.getString("status").equals("SUCCESS")) {
                result = new Pair<String, String>(obj.getString("status"), "SUCCESS");
            } else
                result = new Pair<String, String>(obj.getString("status"), obj.getString("message"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    /**
     * @param product
     * @return
     */
    public Pair<String, String> createProduct(String access_token, Product product) {
        String abc = homeUrl + "/api/product";
        Pair<String, String> result = null;
        try {
            // create connection
            HttpURLConnection connection = createConnection(abc, "POST");
            connection.setRequestProperty("Authorization", access_token);
            // create input
            Gson gson = new Gson();
            String input = gson.toJson(product);
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // read output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject

            connection.disconnect();
            JSONObject obj = new JSONObject(response);

            if (obj.getString("status").equals("FAIL")) {
                String productJson = obj.getString("message");
                result = new Pair<>(obj.getString("status"), productJson);
            } else
                result = new Pair<>(obj.getString("status"), "SUCCESS");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param id
     * @return if successful, return product, else null
     */
    public Pair<String, Product> getProductById(String id) {
        String abc = homeUrl + "/api/product/" + id;
        Pair<String, Product> result = null;
        try {
            // create connection
            HttpURLConnection connection = createConnection(abc, "GET");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // read output
            String response = readResponse(connection);

            // gen to JSONObject

            connection.disconnect();
            JSONObject obj = new JSONObject(response);
            if (obj.getString("status").equals("SUCCESS")) {
                JSONObject productJson = obj.getJSONObject("message");

                JSONGenerator generator = new JSONGenerator();
                result = new Pair<>(obj.getString("status"), generator.generateProduct(productJson));
            } else
                result = new Pair<>(obj.getString("status"), null);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @return
     */
    public Pair<String, List<String>> getCategoryList() {
        String abc = homeUrl + "/api/category";
        Pair<String, List<String>> result = null;
        try {
            // create connection
            HttpURLConnection connection = createConnection(abc, "GET");
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // read output
            String response = readResponse(connection);

            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            List<String> categoryList = new ArrayList<>();
            if (obj.getString("status").equals("SUCCESS")) {
                JSONArray temp = obj.getJSONArray("message");
                for (int i = 0; i < temp.length(); i++) {
                    categoryList.add(temp.get(i).toString());
                }
                result = new Pair<>(obj.getString("status"), categoryList);
            } else {
                System.out.println(obj.getString("message"));
                result = new Pair<>(obj.getString("status"), null);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<String, String> uploadImage(String access_token, String image) {
        Pair<String, String> result = new Pair<>("FAIL", "");
        String abc = homeUrl + "/api/login";
        // create connection
        try {
            HttpURLConnection connection = createConnection(abc, "POST");
            String input = "{\"image\":\"" + image + "\"}";
            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            result = new Pair<String, String>(obj.getString("status"), obj.getJSONObject("message").getString("token"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Pair<String, Order> buyProduct(String access_token, String productId, int productQuantity) {
        String abc = homeUrl + "/api/order";
        Pair<String, Order> result = new Pair<>("FAIL", null);
        try {
            HttpURLConnection connection = createConnection(abc, "POST");
            connection.setRequestProperty("Authorization", access_token);

            String input = "{\"productID\":\"" + productId + "\", \"productQuantity\":\"" + productQuantity + "\"}";

            OutputStream os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            JSONGenerator generator = new JSONGenerator();

            if (obj.getString("status").equals("SUCCESS")) {
                result = new Pair<String, Order>(obj.getString("status"),
                        generator.generateOrder(obj.getJSONObject("message")));
            } else {
                System.out.println(obj.getString("status") + " because " + obj.getString("message"));
                result = new Pair<>(obj.getString("status"), null);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<String, List<Product>> getProductList(String access_token) {
        Pair<String, List<Product>> result = null;
        String abc = homeUrl + "/api/product";

        try {
            HttpURLConnection connection = createConnection(abc, "GET");
            //connection.addRequestProperty("Authorization", access_token);

            // String input = "{\"page\":\"" + page + "\", \"pageSize\":\"" + pageSize +
            // "\"}";
            // OutputStream os = connection.getOutputStream();
            // os.write(input.getBytes());
            // os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            JSONGenerator generator = new JSONGenerator();
            List<Product> products = new ArrayList<>();
            if (obj.getString("status").equals("SUCCESS")) {
                JSONArray temp = obj.getJSONArray("message");
                for (int i = 0; i < temp.length(); i++) {
                    JSONObject product = temp.getJSONObject(i);
                    products.add(generator.generateProduct(product));
                }
                result = new Pair<>(obj.getString("status"), products);
            } else {
                System.out.println(obj.getString("status") + " because " + obj.getString("message"));
                result = new Pair<>(obj.getString("status"), null);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<String, String> cancelProduct(String access_token, String id) {
        Pair<String, String> result = null;
        String abc = homeUrl + "/api/product/" + id;
        try {
            HttpURLConnection connection = createConnection(abc, "DELETE");
            connection.setRequestProperty("Authorization", access_token);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }
            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            result = new Pair<>(obj.getString("status"), obj.getString("message"));
            if (!result.equals("SUCCESS")) {
                System.out.println(obj.getString("status") + " because " + obj.getString("message"));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Pair<String, List<Product>> getMyProduct(String access_token) {
        Pair<String, List<Product>> result = null;
        List<Product> productList = new ArrayList<>();
        String abc = homeUrl + "/api/me/product";
        try {
            HttpURLConnection connection = createConnection(abc, "GET");
            connection.setRequestProperty("Authorization", access_token);

            // String input = "{\"page\":\"" + page + "\", \"pageSize\":\"" + pageSize +
            // "\"}";
            // OutputStream os = connection.getOutputStream();
            // os.write(input.getBytes());
            // os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            JSONGenerator generator = new JSONGenerator();
            if (obj.getString("status").equals("SUCCESS")) {
                JSONArray temp = obj.getJSONArray("message");
                for (int i = 0; i < temp.length(); i++) {
                    JSONObject product = temp.getJSONObject(i);
                    productList.add(generator.generateProduct(product));
                }
                result = new Pair<>(obj.getString("status"), productList);
            } else {
                System.out.println(obj.getString("status") + " because " + obj.getString("message"));
                result = new Pair<>(obj.getString("status"), null);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Pair<String, List<Order>> getOrder(String access_token) {
        Pair<String, List<Order>> result = null;
        List<Order> orderList = new ArrayList<>();
        String abc = homeUrl + "/api/me/order";
        try {
            HttpURLConnection connection = createConnection(abc, "GET");
            connection.setRequestProperty("Authorization", access_token);

            // String input = "{\"page\":\"" + page + "\", \"pageSize\":\"" + pageSize +
            // "\"}";
            // OutputStream os = connection.getOutputStream();
            // os.write(input.getBytes());
            // os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            // get output
            String response = readResponse(connection);
            connection.disconnect();
            // gen to JSONObject
            JSONObject obj = new JSONObject(response);
            JSONGenerator generator = new JSONGenerator();
            if (obj.getString("status").equals("SUCCESS")) {
                JSONArray temp = obj.getJSONArray("message");
                for (int i = 0; i < temp.length(); i++) {
                    JSONObject order = temp.getJSONObject(i);
                    orderList.add(generator.generateOrder(order));
                }
                result = new Pair<>(obj.getString("status"), orderList);
            } else {
                System.out.println(obj.getString("status") + " because " + obj.getString("message"));
                result = new Pair<>(obj.getString("status"), null);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @return the homeUrl
     */
    public String getHomeUrl() {
        return homeUrl;
    }


}
