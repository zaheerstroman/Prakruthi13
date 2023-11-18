package com.prakruthi.ui.ui.productPage;

import static com.google.firebase.messaging.Constants.TAG;
import static com.prakruthi.ui.ui.AddReviewsUserDetails.rating;
import static com.prakruthi.ui.ui.AddReviewsUserDetails.review;
import static com.prakruthi.ui.utils.Constants.CART;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.prakruthi.databinding.ActivityProductPageBinding;
import com.prakruthi.databinding.FragmentHomeBinding;
import com.prakruthi.databinding.FragmentWishlistBinding;
import com.prakruthi.ui.APIs.AddToBuy;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetRecentViewProductsAPI;
import com.prakruthi.ui.APIs.GetUserDataApi;
import com.prakruthi.ui.APIs.SavePaymentDetailsApi;
import com.prakruthi.ui.APIs.SuggestedproductsApi;
import com.prakruthi.ui.HomeActivity;
import com.prakruthi.ui.Utility.AvenuesParams;
import com.prakruthi.ui.Utility.ServiceUtility;
import com.prakruthi.ui.ui.cart.CartFragment;
import com.prakruthi.ui.ui.home.HomeFragment;
import com.prakruthi.ui.ui.profile.AboutUsWebViewActivity;
import com.prakruthi.ui.ui.profile.EditProfileFragmentHttpURLConnection;
import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponse;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.prakruthi.ui.ui.profile.recentProducts.SuggestedproductsAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.SuggestedproductsModel;
import com.prakruthi.ui.utils.Constants;
import com.willy.ratingbar.ScaleRatingBar;

//import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddMyOrdersProductReviews;
import com.prakruthi.ui.APIs.AddRecentViewProductsAPI;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.GetProductDetails;
import com.prakruthi.ui.APIs.GetProductReviews;
import com.prakruthi.ui.APIs.SaveWishList;
//import com.prakruthi.ui.Utility.Constants;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddReviewsUserDetails;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsAdaptor;
import com.prakruthi.ui.ui.productPage.productReviews.ProductReviewsModel;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.willy.ratingbar.RotationRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductPage extends AppCompatActivity implements SuggestedproductsApi.OnSuggestedproductsApiHit, GetProductDetails.OnProductDataFetched, AddToCart.OnDataFetchedListner, AddToBuy.OnAddToBuyDataFetchedListner,SaveWishList.OnSaveWishListDataFetchedListener, GetProductReviews.OnGetProductReviewsHits, SavePaymentDetailsApi.OnSavePaymentDetailsApiHitListner, View.OnClickListener {

    String categoryId, category_id;

    public ProductModel productModel;
    String productId;

    public SharedPreferences sharedPreferences;

    GetProductReviews.OnGetProductReviewsHits mListner;

    public static String address;
    public static String product_ids;
    public static String total_amount;

    ArrayList<ProductModel> productModals;
    TextView ProductName, ProductDescription, CurrentPrice, MRPPrice, ProductDeleveryAddress,
            Avalable, Ratingcount, VoewAllRating, tv_youtube_link, no_stack, machine_type_Head, tv_machine_type;

    ImageView youtube;

    PowerSpinnerView Qty;
    AppCompatButton AddtoCart, BuyNow;
    DotsIndicator dotsIndicator;
    AppCompatButton Wishlist, Product_Share, productPage_back_btn;

    AppCompatButton Write;

    RelativeLayout ll_site_visit_req_LastUpdated4_http_details;
    ViewPager2 ProductImagePager;
    RotationRatingBar ratingBar;
    ShimmerRecyclerView ReviewsrecyclerView, ReviewsrecyclerView_add;

    String amount, order_ID;

    boolean Reviewable;
    boolean in_wishlist;

    String is_active;

    private ActivityProductPageBinding binding;

    public static int id;

    public static ShimmerRecyclerView ProductPageProductsRecycler;

    LinearLayout ll_videoUrl3, ll_videoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        binding = ActivityProductPageBinding.inflate(getLayoutInflater());

        // Retrieve the product ID from the intent
        productId = getIntent().getStringExtra("productId");

        ProductPageProductsRecycler = findViewById(R.id.ProductPageSuggestedProductsRecycler);

        ProductPageProductsRecycler.showShimmerAdapter();

        SuggestedproductsApi suggestedproductsApi = new SuggestedproductsApi(this, productId);
        suggestedproductsApi.HitSuggestedproductsRecentApi();


        SetViews();

        GetApiData();

        Clicks();

        View root = binding.getRoot();

        id = root.getId();
        address = String.valueOf(root.getId());
        product_ids = String.valueOf(root.getId());
        total_amount = String.valueOf(root.getId());

        final String photoUrl = "http://pngriver.com/wp-content/uploads/2017/12/download-Android-Technology-logo-PNG-transparent-images-transparent-backgrounds-PNGRIVER-COM-Android-Mobile-App-Development-Company-In-India-brillmindztech-39975001-800-799.png";


        final ImagePopup imagePopup = new ImagePopup(this);
        imagePopup.setBackgroundColor(Color.BLACK);
        imagePopup.setFullScreen(false);
        imagePopup.setHideCloseIcon(false);
        imagePopup.setImageOnClickClose(true);

        imagePopup.initiatePopupWithPicasso(photoUrl);

    }


    private void SetViews() {

        productPage_back_btn = findViewById(R.id.productPage_back_btn);

        ProductImagePager = findViewById(R.id.ProductImagePager);

        dotsIndicator = findViewById(R.id.dots_indicator);
        ProductName = findViewById(R.id.ProductName);
//        Spanned spanned = Html.fromHtml(ProductDescription);
        ProductDescription = findViewById(R.id.ProductDescription);
        CurrentPrice = findViewById(R.id.CurrentPrice);
        MRPPrice = findViewById(R.id.MRPPrice);
        ProductDeleveryAddress = findViewById(R.id.ProductDeleveryAddress);

        Avalable = findViewById(R.id.Avalable);
        no_stack = findViewById(R.id.no_stack);

        Qty = findViewById(R.id.Qty);
        AddtoCart = findViewById(R.id.AddtoCart);
        BuyNow = findViewById(R.id.BuyNow);
        Wishlist = findViewById(R.id.Product_Save_Wishlist);
        Product_Share = findViewById(R.id.Product_Share);

        ratingBar = findViewById(R.id.simpleRatingBar);
        Ratingcount = findViewById(R.id.RatingCount);
        VoewAllRating = findViewById(R.id.ViewAllRatimg);

        tv_youtube_link = findViewById(R.id.tv_youtube_link);
        tv_youtube_link.setOnClickListener(this);

        youtube = findViewById(R.id.youtube);
        youtube.setOnClickListener(this);

        machine_type_Head = findViewById(R.id.machine_type_Head);
        tv_machine_type = findViewById(R.id.tv_machine_type);

        ll_videoUrl3 = findViewById(R.id.ll_videoUrl3);
        ll_videoUrl = findViewById(R.id.ll_videoUrl);


        if (BuyNow != null) {
            BuyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    startActivity(new Intent(ProductPage.this, CCAvenueBuyNowPageActivity.class));
//                    startActivity(new Intent(ProductPage.this, CartFragment.class));
                    startActivity(new Intent(ProductPage.this, SavePaymentDetailsActivity.class));


                }
            });
        }

    }


    private int getTotal(List<ProductModel> products) {

        int TotalPrice = 0;
        for (ProductModel product : products) {
            TotalPrice = Integer.parseInt(TotalPrice + product.getCustomerPrice());
        }
        return TotalPrice;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //generating new order number for every transaction
        Integer randomNum = ServiceUtility.randInt(0, 9999999);
        order_ID = randomNum.toString();
    }

    public void showToast(String msg) {
        Toast.makeText(this, "Toast: " + msg, Toast.LENGTH_LONG).show();
    }

    private void Clicks() {

        productPage_back_btn.setOnClickListener(v -> super.onBackPressed());



        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String youtubeLink = productModel.getYoutubeLink();
                if (!youtubeLink.isEmpty()) {
                    // Create an Intent to open the YouTube app or web browser
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube"); // Specify the YouTube app package for faster loading

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // YouTube app is not installed, open in a web browser
                        intent.setPackage(null); // Clear the package to open in a web browser
                        startActivity(intent);
                    }
                }
            }
        });


        Product_Share.setOnClickListener(v -> {
            try {
//                String url = "https://tproperty.in/Welcome/properties_details/" + projectDetailsResponse.getProjectDetails().getId();
//                String url = "https://prakruthiepl.com/" + projectDetailsResponse.getProjectDetails().getId();
                String url = "https://play.google.com/";

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "TProperty");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Prakruthi");

                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Wishlist.setOnClickListener(v -> {

            if (in_wishlist) {
                SaveWishList saveWishList = new SaveWishList(this, productId);
                saveWishList.HitSaveWishListApi("No");
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_outline));
                Toast.makeText(this, "Remove Wishlist", Toast.LENGTH_SHORT).show();


            } else if (!in_wishlist) {
                SaveWishList saveWishList = new SaveWishList(this, productId);
                saveWishList.HitSaveWishListApi("Yes");
//                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_filled));
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_filled));
                Toast.makeText(this, "Added Wishlist", Toast.LENGTH_SHORT).show();


            }

        });

        AddtoCart.setOnClickListener(v -> {
            Qty.setError(null);
            if (Qty.getText().toString().isEmpty()) {
                Qty.setError("Select Quantity");
                ObjectAnimator.ofFloat(Qty, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                Qty.requestFocus();
                return;
            }
            Loading.show(this);
            AddToCart addToCart = new AddToCart(productId, String.valueOf(Qty.getSelectedIndex() + 1), String.valueOf(Qty.getSelectedIndex() + 1), false, this);
            addToCart.fetchData();
        });

        BuyNow.setOnClickListener(v -> {
            Qty.setError(null);
            if (Qty.getText().toString().isEmpty()) {
                Qty.setError("Select Quantity");
                ObjectAnimator.ofFloat(Qty, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
                Qty.requestFocus();
                return;
            }
            Intent intent = new Intent(ProductPage.this, SavePaymentDetailsActivity.class);

//            Intent intent=new Intent(ProductPage.this, HomeActivity.class);
            intent.putExtra("cart", "1");
            startActivity(intent);
            Loading.show(this);
            AddToBuy addToBuy = new AddToBuy(productId, String.valueOf(Qty.getSelectedIndex() + 1), String.valueOf(Qty.getSelectedIndex() + 1), false, this);
            addToBuy.fetchData();

        });

        //----------
        VoewAllRating.setOnClickListener(v -> {

            {

                ArrayList<AddMyOrdersProductReviews> reviews = new ArrayList<>(); // Initialize your reviews list

                //Get:--------
                // Inflate the custom layout
                View bottomSheetView = getLayoutInflater().inflate(R.layout.product_reviews_bottom_popup, null);

                AppCompatButton Write = bottomSheetView.findViewById(R.id.Write);

                Write.setOnClickListener(view -> {
                    if (Reviewable) {
                        WriteAReview();
//                        AddRatingReviewFeedBackDialog();
                    } else {
                        Toast.makeText(this, "Purchase Product To Review", Toast.LENGTH_SHORT).show();
                    }
                });


                // Find the RecyclerView in the layout
                ReviewsrecyclerView = bottomSheetView.findViewById(R.id.recyclerView);
                // Set up your RecyclerView (e.g., set adapter, layout manager, etc.)
                ReviewsrecyclerView.showShimmerAdapter();

                Log.e(TAG, "OnDataFetched: ");

                // Create the bottom sheet dialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProductPage.this);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                GetProductReviews getProductReviews = new GetProductReviews(productId, this);
                getProductReviews.HitReviewsApi();

            }
        });
    }


    //ALI:--
    //------------------------

    public void WriteAReview() {
        // Create a custom dialog instance
        Dialog dialog = new Dialog(ProductPage.this);
        dialog.setContentView(R.layout.custom_dialog_layout);

        Button post = dialog.findViewById(R.id.Post);
        RotationRatingBar rotationRatingBar = dialog.findViewById(R.id.ReviewRatingBar);
        EditText review = dialog.findViewById(R.id.review);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (review.getText().toString().isEmpty()) {
                    review.setError("Cannnot Be Empty");
                } else {
                    AddMyOrdersProductReviews addProductReviews = new AddMyOrdersProductReviews(productId, String.valueOf(rotationRatingBar.getRating()), review.getText().toString(), new AddMyOrdersProductReviews.OnAddMyOrdersProductReviewsHitsListener() {

                        @Override
                        public void OnAddProductReviewsResult() {
                            runOnUiThread(() -> {
                                GetProductReviews getProductReviews = new GetProductReviews(productId, ProductPage.this);

                                getProductReviews.HitReviewsApi();
                                Toast.makeText(ProductPage.this, "Review Added", Toast.LENGTH_SHORT).show();
                                dialog.dismiss(); // Close the dialog
                            });

                        }

                        @Override
//                        public void OnAddProductReviewsError(String error) {
                        public void OnGetProductReviewsError(String error) {

                            runOnUiThread(() -> {
                                GetProductReviews getProductReviews = new GetProductReviews(productId, ProductPage.this);
                                getProductReviews.HitReviewsApi();
                                Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
                                dialog.dismiss(); // Close the dialog
                            });

                        }
                    });
                    addProductReviews.HitAddMyOrdersProductReviewsApi();
                }

            }
        });
        dialog.show(); // Show the dialog

    }


//--------


    public void GetApiData() {
        GetProductDetails getProductDetails = new GetProductDetails(this, productId);
        getProductDetails.fetchData();

        AddRecentViewProductsAPI addRecentViewProductsAPI = new AddRecentViewProductsAPI(productId);
        addRecentViewProductsAPI.HitRecentApi();

        SuggestedproductsApi suggestedproductsApi = new SuggestedproductsApi(this, productId);
        suggestedproductsApi.HitSuggestedproductsRecentApi();
    }


    @Override
    public void OnDataFetched(ProductModel productModel) {
//    public void OnDataFetched(ArrayList<ProductModel> productModel) {

        this.runOnUiThread(() -> {

//            ProductImagePager.setAdapter(new ProductPagerAdaptor(this, productModel.getAttachments()));
            ProductImagePager.setAdapter(new ProductPagerAdaptor(this, productModel.getAttachments()));

            ProductImagePager.setOnClickListener(v -> {

//                Toast.makeText(v.getContext(), "Item is clicked", Toast.LENGTH_SHORT).show();

                //-----

                if (productModel != null) {
                    List<String> attachments = productModel.getAttachments();

                    if (attachments != null && !attachments.isEmpty()) {

                        final ImagePopup imagePopup = new ImagePopup(ProductPage.this);
                        imagePopup.setBackgroundColor(Color.WHITE);
                        imagePopup.setFullScreen(false);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setMaxWidth(60);
                        imagePopup.setMaxHeight(500);

                        imagePopup.initiatePopupWithPicasso(attachments.get(0)); // Assuming you want to show the first attachment
                        imagePopup.viewPopup();

//                        if (productModel != null && !productModel.getAttachments().isEmpty()) {
//                            String firstAttachment = productModel.getAttachments().get(0); // Assuming the first attachment is a string representing an image URL or file path
//                            imagePopup.initiatePopupWithPicasso(firstAttachment);
//                            imagePopup.viewPopup();
//                        }

                    }

                }


                //------

                Log.e("Width", "" + Resources.getSystem().getDisplayMetrics().widthPixels);
                final ImagePopup imagePopup = new ImagePopup(ProductPage.this);
                imagePopup.setBackgroundColor(Color.WHITE);
                imagePopup.setFullScreen(false);
                imagePopup.setHideCloseIcon(true);
                imagePopup.setImageOnClickClose(true);
                imagePopup.setHideCloseIcon(true);
                imagePopup.setMaxWidth(10);
                imagePopup.setMaxHeight(10);
//                imagePopup.initiatePopupWithPicasso((File) productModel.getAttachments());
//                imagePopup.viewPopup();

                if (productModel != null && !productModel.getAttachments().isEmpty()) {
                    String firstAttachment = productModel.getAttachments().get(0); // Assuming the first attachment is a string representing an image URL or file path
                    imagePopup.initiatePopupWithPicasso(firstAttachment);
                    imagePopup.viewPopup();
                }

            });

            dotsIndicator.attachTo(ProductImagePager);

            ProductName.setText(productModel.getName());

//            Spanned spanned = Html.fromHtml(productModel.getDescription());
//            ProductDescription.setText(spanned);


//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                // Use HtmlCompat.fromHtml for API 24 and above
//                CharSequence formattedText = HtmlCompat.fromHtml(productModel.getDescription(), HtmlCompat.FROM_HTML_MODE_LEGACY);
//                ProductDescription.setText(formattedText);
//            } else {
//                // Use the deprecated Html.fromHtml for below API 24
//                Spanned spanned = Html.fromHtml(productModel.getDescription());
//                ProductDescription.setText(spanned);
//            }

            //-----------------

            String htmlContent = productModel.getDescription();

            // Manually process HTML tags
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                Spanned spanned = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY);
//                ProductDescription.setText(spanned);
//            } else {
//                Spanned spanned = Html.fromHtml(htmlContent);
//                ProductDescription.setText(spanned);
//            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Spanned spanned = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    ProductDescription.setText(spanned);
                } else {
                    ProductDescription.setText(Html.fromHtml(htmlContent));
                }
            } else {
                // For versions older than N
                ProductDescription.setText(Html.fromHtml(htmlContent));
            }

            //-----

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
////                ProductDescription.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
//            } else {
////                ProductDescription.loadData(htmlContent, "text/html", "UTF-8");
//            }



            if (Variables.departmentId.equals(2)) {
//                CurrentPrice.setText("₹ : ");
                CurrentPrice.setText("₹  ");

                CurrentPrice.append(productModel.getCustomerPrice());
            } else if (Variables.departmentId.equals(3)) {

                CurrentPrice.setText("₹  ");
                CurrentPrice.append(productModel.getDealerPrice());
            } else if (Variables.departmentId.equals(4)) {

                CurrentPrice.setText("₹  ");
                CurrentPrice.append(productModel.getMartPrice());
            }

            MRPPrice.setText(" M.R.P ₹  ");
            MRPPrice.append(productModel.getActualPrice());
            MRPPrice.setPaintFlags(MRPPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            ProductDeleveryAddress.setText(Variables.address);

            in_wishlist = productModel.isIn_wishlist();
            if (in_wishlist)
                Wishlist.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.like_filled));


            ratingBar.setRating(Float.parseFloat(productModel.getRating()));
//            Ratingcount.setText(productModel.getCount_rating());
            Ratingcount.setText(productModel.getRating());

//            String v_url = "https://www.youtube.com/watch?v=";
            String v_url = "";


//            tv_youtube_link.setText("YoutubeLink: " + v_url + productModel.getYoutubeLink());
            tv_youtube_link.setText(productModel.getYoutubeLink());
            //youtube.setText(productModel.getYoutubeLink());


            if (youtube != null) {
                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String youtubeLink = productModel.getYoutubeLink();
                        if (!youtubeLink.isEmpty()) {
                            // Create an Intent to open the YouTube app or web browser
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setPackage("com.google.android.youtube"); // Specify the YouTube app package for faster loading

                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                // YouTube app is not installed, open in a web browser
                                intent.setPackage(null); // Clear the package to open in a web browser
                                startActivity(intent);
                            }
                        }
                    }
                });
            }


            tv_machine_type.setText(productModel.getMachine_type());

            tv_machine_type.setVisibility(View.VISIBLE);
            machine_type_Head.setVisibility(View.VISIBLE);

//            if (tv_machine_type == null) {
            if (tv_machine_type != null && machine_type_Head != null) {

//            if (tv_machine_type == null && machine_type_Head == null) {
//                machine_type_Head.setVisibility(View.VISIBLE);
//            } else {
//                machine_type_Head.setVisibility(View.GONE);
//            }



//            if (!productModel.getStatus().equalsIgnoreCase("null")) {
//            if (!productModel.getStatus().equalsIgnoreCase("Manual", "Automatic", "semi-Automatic")) {

//            if (!productModel.getStatus().equalsIgnoreCase("Manual") &&
//                    !productModel.getStatus().equalsIgnoreCase("Automatic") &&
//                    !productModel.getStatus().equalsIgnoreCase("semi-Automatic")) {

                machine_type_Head.setVisibility(View.VISIBLE);
                machine_type_Head.setEnabled(true);
                tv_machine_type.setVisibility(View.VISIBLE);
                tv_machine_type.setEnabled(true);

                machine_type_Head.setVisibility(View.GONE);
                machine_type_Head.setEnabled(false);
                tv_machine_type.setVisibility(View.GONE);
                tv_machine_type.setEnabled(false);

            } else {
                machine_type_Head.setVisibility(View.GONE);
                machine_type_Head.setEnabled(false);
                tv_machine_type.setVisibility(View.GONE);
                tv_machine_type.setEnabled(false);

                machine_type_Head.setVisibility(View.VISIBLE);
                machine_type_Head.setEnabled(true);
                tv_machine_type.setVisibility(View.VISIBLE);
                tv_machine_type.setEnabled(true);
                // If the condition is false, hide the view and disable it


            }


//            tv_machine_type.setText(": ");
//            tv_machine_type.append(productModel.getMachine_type());

            //is_active = productModel.getStatus();
            //boolean isAvailable = true;

            AddtoCart.setVisibility(View.VISIBLE);
            BuyNow.setVisibility(View.VISIBLE);

            if (!productModel.getStatus().equalsIgnoreCase("Active")) {

                // If the condition is true, make the view visible and enabled


                Avalable.setVisibility(View.GONE);
                Avalable.setEnabled(false);
                no_stack.setVisibility(View.VISIBLE);
                no_stack.setEnabled(true);


                AddtoCart.setVisibility(View.GONE);
                AddtoCart.setEnabled(false);
                BuyNow.setVisibility(View.GONE);
                BuyNow.setEnabled(false);

            } else {
                no_stack.setVisibility(View.GONE);
                no_stack.setEnabled(false);

                Avalable.setVisibility(View.VISIBLE);
                Avalable.setEnabled(true);
                // If the condition is false, hide the view and disable it


            }


        });

        Reviewable = productModel.getIs_review();

    }

    @Override
    public void OnDataFetchError(String message) {
        this.runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this).setStyle(Styles.FAILED).setHeading("Uh-Oh").setDescription("Unexpected error occurred." + " Try again later.").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnCarteditDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
        });

    }

    @Override
    public void OnAddtoCartDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added Into The Cart").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {

                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnBuyeditDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
        });

    }

    @Override
    public void OnAddtoBuyDataFetched(String Message) {
        this.runOnUiThread(() -> {
            Loading.hide();
            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added Into The Cart").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {

                    super.onDismissClicked(dialog);
                }
            });
        });

    }

    @Override
    public void OnErrorFetched(String Error) {
        this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(this, Error, Toast.LENGTH_SHORT).show();
            PopupDialog.getInstance(this).setStyle(Styles.FAILED).setHeading("Uh-Oh").setDescription("Unexpected error occurred." + " Try again later.").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    //Spanned spanned = Html.fromHtml(String.valueOf(ProductDescription));
                    //TextView.setText(spanned);
                    super.onDismissClicked(dialog);
                }
            });
        });
    }

    @Override
    public void OnItemSavedToWishlist(String message) {
        runOnUiThread(() -> {
            GetApiData();
        });

    }

    @Override
    public void OnSaveWishlistApiGivesError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnGetProductReviewsResult
            (ArrayList<ProductReviewsModel> productReviewsModels) {
        try {
            runOnUiThread(() -> {
                ReviewsrecyclerView.setLayoutManager(new LinearLayoutManager(ProductPage.this));
                ReviewsrecyclerView.setAdapter(new ProductReviewsAdaptor(productReviewsModels));
                ReviewsrecyclerView.hideShimmerAdapter();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void OnGetProductReviewsError(String error) {
        ProductPage.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void OnsuggestedproductsAPIGivesResult(ArrayList<SuggestedproductsModel> suggestedproductsModels) {
        try {
            this.runOnUiThread(() -> {

                ProductPageProductsRecycler.hideShimmerAdapter();

                ProductPageProductsRecycler.setLayoutManager(new GridLayoutManager(this, 2));

                ProductPageProductsRecycler.setAdapter(new SuggestedproductsAdaptor(suggestedproductsModels));

                ProductPageProductsRecycler.hideShimmerAdapter();

            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void OnsuggestedproductsAPIGivesError(String error) {
        try {
            this.runOnUiThread(() -> {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

                ProductPageProductsRecycler.hideShimmerAdapter();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnSavePaymentDtailsResult(String product) {
        ProductPage.this.runOnUiThread(Loading::hide);

    }

    @Override
    public void OnOnSavePaymentDtailsResultApiGivesError(String error) {
        ProductPage.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(ProductPage.this, error, Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public void onClick(View v) {
        tv_youtube_link.getPaint().setUnderlineText(true);

        if (productModel.getYoutubeLink().isEmpty())
            ll_videoUrl.setVisibility(View.GONE);

        else {
            String v_url = "";
//          tv_youtube_link.setText("YoutubeLink: "  + productModel.getYoutubeLink());
            tv_youtube_link.setText(productModel.getYoutubeLink());

            ll_videoUrl.setVisibility(View.VISIBLE);
        }
    }
}