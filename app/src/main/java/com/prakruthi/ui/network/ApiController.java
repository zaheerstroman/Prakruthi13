package com.prakruthi.ui.network;

import com.prakruthi.ui.ui.profile.order_qty.SaveOrderPurchaseQuantityQtyDataModal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiController {

    private static final String BASE_URL = "http://prakruthiepl.com/admin/api/";

    public static Boolean statusCode;
    public static String message;

    public static String productId = "";
    public static String quntity = "";

    public SaveOrderPurchaseQuantityQtyDataModal saveOrderData(int userId, String token, String productId, String quntity) {

        SaveOrderPurchaseQuantityQtyDataModal apiResponse = null;

        try {
            URL url = new URL(BASE_URL + "saveOrderData");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            //-----
            JSONObject requestData = new JSONObject();

            JSONObject updateDatas = new JSONObject();

            //------


            requestData.put("user_id", userId);
            requestData.put("token", token);
            requestData.put("update_data", productId.toString()+quntity.toString());


            try {
                requestData.put("product_id", productId);
                requestData.put("quntity", quntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            OutputStream os = connection.getOutputStream();
            byte[] input = requestData.toString().getBytes("utf-8");
            os.write(input, 0, input.length);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                apiResponse = new SaveOrderPurchaseQuantityQtyDataModal(statusCode, message);

                apiResponse.setStatus_code(jsonResponse.getBoolean("status_code"));
                apiResponse.setMessage(jsonResponse.getString("message"));
            } else {
                // Handle error cases
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apiResponse;
    }


}

