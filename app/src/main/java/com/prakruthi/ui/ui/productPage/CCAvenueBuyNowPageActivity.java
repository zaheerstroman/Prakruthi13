package com.prakruthi.ui.ui.productPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.prakruthi.R;
import com.prakruthi.ui.utils.Constants;

public class CCAvenueBuyNowPageActivity extends AppCompatActivity {

    private WebView webView, iv_attachment_ccavenue_buynow;

    public static String payment_url, paymentUrl,invoice_id,invoiceId,invoice_num,invoiceNum,status_code,statusCode,message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ccavenue_buy_now_page);

        iv_attachment_ccavenue_buynow = findViewById(R.id.iv_attachment_ccavenue_buynow);

        webView = findViewById(R.id.iv_attachment_ccavenue_buynow);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("payment_url");
        webView.loadUrl("invoice_id");
        webView.loadUrl("invoice_num");
        webView.loadUrl("status_code");
        webView.loadUrl("message");

        webView.loadUrl(paymentUrl);
        webView.loadUrl(invoiceId);
        webView.loadUrl(invoiceNum);
        webView.loadUrl(statusCode);
        webView.loadUrl(message);


        // Get dynamic URLs from intent
        String paymentUrl = getIntent().getStringExtra("payment_url");
        String invoiceId = getIntent().getStringExtra("invoice_id");

        String invoiceNum = getIntent().getStringExtra("invoice_num");
        String statusCode = getIntent().getStringExtra("status_code");
        String message = getIntent().getStringExtra("message");

        iv_attachment_ccavenue_buynow.loadUrl(paymentUrl);
        iv_attachment_ccavenue_buynow.loadUrl(invoiceId);
        iv_attachment_ccavenue_buynow.loadUrl(invoiceNum);
        iv_attachment_ccavenue_buynow.loadUrl(statusCode);
        iv_attachment_ccavenue_buynow.loadUrl(message);

        if (paymentUrl != null) {
            webView.loadUrl(paymentUrl);
        }
        else if (invoiceId != null) {
            webView.loadUrl(invoiceId);
        }
        else if (invoiceNum != null) {
            webView.loadUrl(invoiceNum);
        }
        else if (statusCode != null) {
            webView.loadUrl(statusCode);
        }
        else if (message != null) {
            webView.loadUrl(message);
        }
        else
        {
            // Handle the case when paymentUrl is null or empty
            webView.loadUrl("Payment URL is invalid");

        }

    }

    private void init() {


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("CCAvenue Buy Now Page");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.PAYMENTURL));

                startActivity(i);
                finish();
            }
        });
        iv_attachment_ccavenue_buynow = findViewById(R.id.iv_attachment_ccavenue_buynow);
        iv_attachment_ccavenue_buynow.getSettings().setBuiltInZoomControls(true);
    }

}