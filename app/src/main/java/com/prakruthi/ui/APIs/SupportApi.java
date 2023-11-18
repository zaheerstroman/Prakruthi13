package com.prakruthi.ui.APIs;

import static com.google.firebase.messaging.Constants.TAG;

import android.util.Log;

import com.prakruthi.ui.Variables;
//import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.prakruthi.ui.ui.profile.ProfileSupportResponse;
import com.prakruthi.ui.ui.profile.SupportModel;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SupportApi {

    OnSupportApiHitFetchedListner mListner;

    public SupportApi(OnSupportApiHitFetchedListner mListner) {
        this.mListner = mListner;
    }

    public void HitSupportApi() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new SupportDataApi());
    }


    private class SupportDataApi implements Runnable {

        @Override
        public void run() {
            //Creating array for parameters
            String[] field = new String[2];
            field[0] = "user_id";
            field[1] = "token";

            //Creating array for data
            String[] data = new String[2];
            data[0] = String.valueOf(Variables.id);
            data[1] = Variables.token;

            PutData putData = new PutData(Variables.BaseUrl + "support", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {

                    String result = putData.getResult();

                    Log.e(TAG, result);

                    if (putData.onComplete()) {
                        result = putData.getResult();
                        Log.e(TAG, result);
                        handleResponse(result);
                    } else {
                        handleError("Failed to fetch data");
                    }

                }
            } else {
                handleError("Failed to fetch data");
            }
        }

    }


    private void handleResponse(String result) {
        if (result != null) {
            try {

                JSONObject jsonResponse = new JSONObject(result);

                JSONArray getSupportDataList = jsonResponse.getJSONArray("result");

                boolean statusCode = jsonResponse.optBoolean("status_code");
                String message = jsonResponse.optString("message");


                ArrayList<SupportModel> supportResponses = new ArrayList<>();

                    JSONObject product = getSupportDataList.getJSONObject(0);

                    int id = product.getInt("id");
                    String support_mobile = product.getString("support_mobile");
                    String support_email = product.getString("support_email");
                    String support_address = product.getString("support_address");

                    supportResponses.add(new SupportModel(support_mobile, support_email, support_address));



                mListner.OnSupportApiGivesSuccess(supportResponses);


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }

    }


    private void handleError(String error) {
        mListner.OnSupportApiGivesError(error);

    }

public interface OnSupportApiHitFetchedListner {

    void OnSupportApiGivesSuccess(ArrayList<SupportModel> supportModels);

    void OnSupportApiGivesError(String error);
}


}
