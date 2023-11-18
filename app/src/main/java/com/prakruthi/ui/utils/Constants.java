package com.prakruthi.ui.utils;

import static com.prakruthi.ui.network.APIClient.BASE;
import static com.prakruthi.ui.network.APIClient.BASE_URL;

public class Constants {

    //JSP 	JSP	- Java Server Pages

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TOKEN = "token";

    public static final String PROFILE_IMG_URL=BASE_URL+"public/img/user/";
    public static final String PROFILE_IMG_URL1=BASE_URL+"assets/portal/user/";

    public static String id;
    public static String user_id;
    public static String token;

    public static final String MESSAGE = "message";


    public static final String allow_email = "allow_email";
    public static final String allow_push = "allow_push";
    public static final String allow_sms = "allow_sms";

    public static final String USER_NAME = "User_Name";

    public static final String USER_EMAIL = "User_Email";

    public static final String USER_MOBILE= "mobile";

    public static final String invoiceId = "invoice_id";
    public static final String invoiceNum = "invoice_num";


    public static final String PRIVACY_POLICY_URL = BASE_URL + "privacyPolicy.html";
    public static final String PRIVACY_POLICY_URL1 = BASE + "privacy-policy-mobile.html";


    public static final String TERMS_COND_URL = BASE_URL + "termsAndConditions.html";
    public static final String TERMS_COND_URL1 = BASE + "terms-and-conditions-mobile.html";

    public static final String RETURN_REFUND_POLICY_URL1 = BASE + "return-refund-policy-mobile.html";

    public static final String SECURITY = BASE + "security-mobile.html";

    public static final String ABOUTUS = BASE + "aboutus-mobile.html";

public static final String PAYMENTURL = "";
public static  String CART = "1";

    //CCAvenue_Payment:--

    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";


    //Test API URL/Staging API URL:-----------------------------------

    public static final String TRANS_URL = "";




    public static final String uat_account_ID = "2738600"; // add your access_code


    public static final String merchantId= "2738600"; // add your merchant_id

    public static final String access_code = "AVHQ89GL40BJ77QHJB"; // add your access_code


    public static final String currency="INR";



    //HDFC PDF URL:-----
    public static final String redirectUrl="www.amazonaws.com/payment/redirect.php";

    //HDFC PDF URL:-----
    public static final String cancelUrl="www.amazonaws.com/payment/cancel.php"; //this is test url you can modify with your on url

    public static final String rsaKeyUrl="https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url

    public static final String enc_val="bbfd519ee11e197c2dd55ecd695e85ffb02ca3e4f0f3691c0f74d5d5fbed06ae8a5a17d\n" +
            "09d77ebc895133d61e208a841ffee1075ca7e75ed9f337a6d6df4138adf4cdd9de4a0f4dc98440\n" +
            "7d59db8213c4c41e91e9dc66560bba4437c4ac6892baa73114bc668fd8431b388512685d3ea";


    public static final String amount="1";

    public static void clear() {
        user_id = "";
        id = "";
        token = "";
    }
}
