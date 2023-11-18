package com.prakruthi.ui.APIs;


import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SaveDeliveryAddressDetailsEditUpdate {

    private final OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener;

    public String name;
    public String city;
    public String state;
    String id;
    public String country;
    public String address;
    public String postal_code;
    public String is_default;

    public SaveDeliveryAddressDetailsEditUpdate(OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner mListener, String name, String city, String state, String id, String country, String address, String postal_code, int is_default) {

        this.mListener = mListener;
        this.name = name;
        this.city = city;
        this.state = state;
        this.id = id;
        this.country = country;
        this.address = address;
        this.postal_code = postal_code;
        this.is_default = String.valueOf(is_default);

    }

    public void HitSaveDeliveryAddressDetailsEditUpdateApiApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new HitSaveDeliveryAddressDetailsEditUpdateApi());
    }

    private class HitSaveDeliveryAddressDetailsEditUpdateApi implements Runnable {

        @Override
        public void run() {

            String[] field = new String[10];
            field[0] = "user_id";
            field[1] = "token";
            field[2] = "name";
            field[3] = "address";
            field[4] = "city";
            field[5] = "state";
            field[6] = "id";
            field[7] = "country";
            field[8] = "postal_code";
            field[9] = "is_default";

            // Creating array for data
            String[] data = new String[10];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;
            data[2] = name;
            data[3] = address;
            data[4] = city;
            data[5] = state;
            data[6] = String.valueOf(id);
            data[7] = country;
            data[8] = postal_code;
            data[9] = String.valueOf(is_default);

            Log.e(TAG, Arrays.toString(field) + Arrays.toString(data));
            PutData putData = new PutData(Variables.BaseUrl + "saveDeliveryAddressDetails", "POST", field, data);
            if (putData.startPut()) {
                String result = putData.getResult();
                Log.e(TAG, result);
                if (putData.onComplete()) {
                    result = putData.getResult();
                    Log.e(TAG, result);
                    try {
                        JSONObject response = new JSONObject(result);

                        // Extract the "message" string
                        String message = response.getString("message");

                        // Use the "message" string as needed
                        handleResponse(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    handleError("Failed to fetch data");
                }
            } else {
                handleError("Failed to connect to the server");
            }
        }
    }

    private void handleError(String error) {
        mListener.onSaveDeliveryAddressDetailsEditUpdateApiError(error);
    }

    private void handleResponse(String message) {
        mListener.onSaveDeliveryAddressDetailsEditUpdate(message);
    }


    public interface OnSaveDeliveryAddressDetailsEditUpdateApiHitsListner {
        void onSaveDeliveryAddressDetailsEditUpdate(String message);

        void onSaveDeliveryAddressDetailsEditUpdateApiError(String error);
    }


}
