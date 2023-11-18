package com.prakruthi.ui.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.prakruthi.R;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.network.APIClient;
import com.prakruthi.ui.network.ApiInterface;
import com.prakruthi.ui.utils.CommonUtils;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.SharedPrefs;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebView_Verification_AnimationRetrofit extends AppCompatActivity {

    GifImageView successGif;
    ImageView gif_webview_verify_image;

    TextView text_OrderId,text_Amount,txt_24_hour;

    private SuccessPaymentCheckModel responseProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_verification_animation);

        successGif = findViewById(R.id.successGif);
        gif_webview_verify_image = findViewById(R.id.gif_webview_verify_image);

        text_OrderId = findViewById(R.id.text_OrderId);
        text_Amount = findViewById(R.id.text_Amount);
        txt_24_hour = findViewById(R.id.txt_24_hour);


        fetchProfileDetails();


    }

    private void fetchProfileDetails() {

        CommonUtils.showLoading(this, "Please Wait", false);

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("user_id", SharedPrefs.getInstance(this).getString(Constants.USER_ID))
                .addFormDataPart("token", SharedPrefs.getInstance(this).getString(Constants.TOKEN))
                .addFormDataPart("invoice_id", SharedPrefs.getInstance(this).getString(Constants.invoiceId))
                .addFormDataPart("invoice_num", SharedPrefs.getInstance(this).getString(Constants.invoiceNum)).build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", SharedPrefs.getInstance(this).getString(Constants.USER_ID));
        jsonObject.addProperty("token", SharedPrefs.getInstance(this).getString(Constants.TOKEN));
        jsonObject.addProperty("invoice_id", SharedPrefs.getInstance(this).getString(Constants.invoiceId));
        jsonObject.addProperty("invoice_num", SharedPrefs.getInstance(this).getString(Constants.invoiceNum));


        Call<SuccessPaymentCheckModel> call = apiInterface.AKRUTHTEST3(jsonObject);


        call.enqueue(new Callback<SuccessPaymentCheckModel>() {
            @Override
            public void onResponse(Call<SuccessPaymentCheckModel> call, Response<SuccessPaymentCheckModel> response) {
                CommonUtils.hideLoading();
                try {
                    responseProfile = response.body();

                    Log.e("TAG", String.valueOf(responseProfile) );


                    if (responseProfile.getOrderNo() != null)
                    {
                        //Retrofit
                        text_OrderId.setText(String.valueOf(responseProfile.getOrderNo()));

                        //HttpUrlConnection
                        Variables.Order_No = text_OrderId.getText().toString();
                        text_OrderId.setText(Variables.Order_No);

                        Toast.makeText(WebView_Verification_AnimationRetrofit.this, "Payment Sucess.", Toast.LENGTH_SHORT).show();


                    }else text_OrderId.setText("");


                    if (responseProfile.getAmount() != null)
                    {
                        text_Amount.setText(String.valueOf(responseProfile.getAmount()));

                        Variables.Amount = text_Amount.getText().toString();
                        text_Amount.setText(Variables.Amount);

                    }else text_Amount.setText("");


                    if (responseProfile.getMessage() != null)
                    {
                        txt_24_hour.setText(String.valueOf(responseProfile.getMessage()));
                    }
                    else txt_24_hour.setText("");



                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SuccessPaymentCheckModel> call, Throwable t) {
                CommonUtils.hideLoading();
            }
        });

    }

}