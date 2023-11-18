package com.prakruthi.ui.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHandler {

    private static final String API_URL = "http://prakruthiepl.com/admin/api/saveOrderData";

    public interface ApiCallback {
        void onResponse(boolean success, String message);
    }

    public static void updateOrderQuantity(int userId, String token, String productId, String quntity, ApiCallback callback) {
        new UpdateOrderQuantityTask(userId, token, productId, quntity, callback).execute();
    }

    private static class UpdateOrderQuantityTask extends AsyncTask<Void, Void, String> {

        private final int userId;
        private final String token;
        private final String productId;
        private final String quntity;


        private final ApiCallback callback;

        public UpdateOrderQuantityTask(int userId, String token, String productId, String quntity, ApiCallback callback) {

            this.userId = userId;
            this.token = token;
            this.productId = productId;
            this.quntity = quntity;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set up the connection properties
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create the request body
                String requestBody = "{\"user_id\":" + userId + ",\"token\":\"" + token + "\",\"product_id\":" + productId + ",\"quntity\":" + quntity + "}";
                // Construct the JSON payload

                // Send the request
                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(requestBody);
                outputStream.flush();
                outputStream.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    // Parse the JSON response and handle accordingly
                    // Replace with your actual parsing logic
                    // In this example, we assume the JSON has "status_code" and "message" fields
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("status_code");
                    String message = jsonResponse.getString("message");
                    callback.onResponse(success, message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onResponse(false, "Error parsing response");
                }
            } else {
                callback.onResponse(false, "Request failed");
            }
        }
    }


}
