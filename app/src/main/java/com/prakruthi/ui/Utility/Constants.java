package com.prakruthi.ui.Utility;

public class Constants {

//    public static final int REQ_CALL = 768;

    public static final int REQUEST_SEL_MUL = 123;
    public static final int REQUEST_TAKE_PHOTO = 787;
    public static final int REQUEST_GALLERY_PHOTO = 865;
    public static final int REQUEST_LOCATION = 365;
    public static final int REQ_CALL = 768;
    //CCAvenue_Payment:--
    public static final String PARAMETER_SEP = "&";
    public static final String PARAMETER_EQUALS = "=";

    //    Production URL
    public static final String TRANS_URL = "https://api.ccavenue.com/apis/servlet/DoWebTrans";

    //Test Url

//// UAT Account ID : 2668133

    public static final String access_code = "4YRUXLSRO20O8NIH"; // add your access_code
    public static final String merchantId = "2"; // add your merchant_id
    public static final String currency = "INR";

    public static final String redirectUrl = "http://192.168.0.241/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
    public static final String cancelUrl = "http://192.168.0.241/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url
    public static final String rsaKeyUrl = "https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url

}
