package com.prakruthi.ui.ui.profile;

import static android.content.ContentValues.TAG;
import static com.prakruthi.ui.Variables.city;
import static com.prakruthi.ui.Variables.district;
import static com.prakruthi.ui.Variables.email;
import static com.prakruthi.ui.Variables.name;
import static com.prakruthi.ui.Variables.state;
import static com.prakruthi.ui.Variables.token;
import static com.prakruthi.ui.Variables.userCode;
import static com.prakruthi.ui.Variables.userId;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prakruthi.R;
import com.prakruthi.databinding.FragmentEditProfile2Binding;
import com.prakruthi.databinding.FragmentProfileBinding;
import com.prakruthi.ui.APIs.FeedBackApi;
import com.prakruthi.ui.APIs.GetRecentViewProductsAPI;
import com.prakruthi.ui.APIs.GetUserDataApi;
import com.prakruthi.ui.APIs.UserDetailsUpdateApi;
import com.prakruthi.ui.HomeActivity;
import com.prakruthi.ui.Login;
import com.prakruthi.ui.SplashScreen;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.network.APIClient;
import com.prakruthi.ui.network.ApiInterface;
import com.prakruthi.ui.ui.profile.myaddress.MyAddresses;
import com.prakruthi.ui.ui.profile.myorders.MyOrdersActivity;
import com.prakruthi.ui.ui.profile.mypayments.MyPaymentsActivity;
import com.prakruthi.ui.ui.profile.order_qty.OrderQtyActivity;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductAdaptor;
import com.prakruthi.ui.ui.profile.recentProducts.RecentProductModel;
import com.prakruthi.ui.utils.CommonUtils;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.SharedPrefs;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements FeedBackApi.OnFeedbackItemAPiHit, GetRecentViewProductsAPI.OnGetRecentViewProductsAPIHit, GetUserDataApi.OnGetUserDataApiHitFetchedListner, UserDetailsUpdateApi.OnUserDetailsUpdateApiGivesFetchedListner, View.OnClickListener {

    public SharedPreferences sharedPreferences;

    AppCompatTextView tvRecentProducts, tvViewAll;

    AppCompatButton btn_add_new_address, login, Logout;

    ImageView iv_edit;

    Context context;

    LinearLayout ll_site_visit_req_LastUpdated4;

    public ShimmerRecyclerView ProfileHomeProductsRecycler;

    TextView tvProfileName, tvEmail, tvPhone, tvRole,
            tvMyOrders, tvMyAddress, tvMyWishlist, tvPayments,
            tvFeedback, tvSupport, tvAboutUs, tvTermsConditions, tvPrivacyPolicy,

    tvRRP, tvSecurity;

    private ProfileGetUserDataResponse responseProfile;
    private ProfileGetUserDataResponseRetrofit responseProfile1;

//    public EditProfileFragment2 editProfileActivity2;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getUserData Api Retrofit
//        fetchProfileDetails();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //TProperty
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        iv_edit = view.findViewById(R.id.iv_edit);
        iv_edit.setOnClickListener(this);

        tvProfileName = view.findViewById(R.id.tv_profile_name);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvRole = view.findViewById(R.id.tv_role);


        Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(this);


        tvMyOrders = view.findViewById(R.id.tv_my_orders);
        tvMyOrders.setOnClickListener(this);

        tvMyAddress = view.findViewById(R.id.tv_my_address);
        tvMyAddress.setOnClickListener(this);

        tvMyWishlist = view.findViewById(R.id.tv_my_wishlist);
        tvMyWishlist.setOnClickListener(this);

        tvPayments = view.findViewById(R.id.tv_payments);
        tvPayments.setOnClickListener(this);

        tvFeedback = view.findViewById(R.id.tv_feedback);
        tvFeedback.setOnClickListener(this);

        tvSupport = view.findViewById(R.id.tv_support);
        tvSupport.setOnClickListener(this);

        tvAboutUs = view.findViewById(R.id.tv_About_us);
        tvAboutUs.setOnClickListener(this);

        tvPrivacyPolicy = view.findViewById(R.id.tv_privacy_policy);
        tvPrivacyPolicy.setOnClickListener(this);

        tvTermsConditions = view.findViewById(R.id.tv_Terms_Conditions);
        tvTermsConditions.setOnClickListener(this);

        tvRRP = view.findViewById(R.id.tv_returnRefundPolicy);
        tvRRP.setOnClickListener(this);

        tvSecurity = view.findViewById(R.id.tv_security);
        tvSecurity.setOnClickListener(this);


        ProfileHomeProductsRecycler = view.findViewById(R.id.ProfileHomeProductsRecycler);
        ProfileHomeProductsRecycler.showShimmerAdapter();


//        fetchProfileDetails();

        sharedPreferences = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);

        Logout.setOnClickListener(v -> {

            Variables.clear();
            // Get SharedPreferences.Editor object
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("rememberMe", false);
            // Apply changes
            editor.apply();
            startActivity(new Intent(requireContext(), Login.class));
            requireActivity().finish();
        });

        iv_edit.setOnClickListener(v -> {

            if (responseProfile1 != null) {


                ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit data = responseProfile1.getData();

//                if (data != null) {
//
//                    editProfileActivity2 = new EditProfileFragment2();
//
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("data", new Gson().toJson(responseProfile1.getData()));
//                    editProfileActivity2.setArguments(bundle);
//
//                    editProfileActivity2.setDismissListener(new EditProfileFragment2.DismissListener() {
//                        //EditProfileActivity2
//                        @Override
//                        public void onDismiss() {
////                            fetchProfileDetails();
//                        }
//                    });
//
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.iv_edit, editProfileActivity2, "EditProfileFragment2");
//                    fragmentTransaction.addToBackStack(null); // Add this line if you want the fragment to be added to the back stack
//                    fragmentTransaction.commit();
//
//
//                    new ProfileFragment().ApiTaskPro();
//
//                }

//                else {
//                    // Handle the case when 'data' is null
//                    Log.e("TAG", "Profile data is null.");
//                }

            }



        });


        //TProperty
        return view;

//        return binding.getRoot();
    }

    @SuppressLint("StaticFieldLeak")
    public void ApiTaskPro() {
        Loading.show(requireContext());
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                //Creating array for parameters
                String[] field = new String[2];
                field[0] = "user_id";
                field[1] = "token";
                //Creating array for data
                String[] data = new String[2];
                data[0] = String.valueOf(Variables.id);
                data[1] = token;
                PutData putData = new PutData(Variables.BaseUrl + "getUserData", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        // result = Api Result
                        String result = putData.getResult();
                        return result;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    try {
                        Log.e(TAG, result);
                        JSONObject json = new JSONObject(result);
                        boolean statusCode = json.getBoolean("status_code");
                        String message = json.getString("message");
                        if (statusCode) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            getUserData(json);
                        } else {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
                login.setVisibility(View.VISIBLE);
                Loading.hide();
            }
        }.execute();

    }

    private FragmentManager getSupportFragmentManager() {
//        return null;
        return null;

    }


    public void SetTextViews() {

        tvProfileName.setText("");

        tvProfileName.append(String.valueOf(name));

        tvEmail.setText("");

        tvEmail.append(String.valueOf(Variables.email));


        tvPhone.setText("");

        tvPhone.append(String.valueOf(Variables.mobile));


        tvRole.setText("");

        tvRole.append(String.valueOf(Variables.userCode));

        ProfileHomeProductsRecycler.showShimmerAdapter();

        tvTermsConditions.setSelected(true);


        GetRecentViewProductsAPI getRecentViewProductsAPI = new GetRecentViewProductsAPI(this);
        getRecentViewProductsAPI.HitRecentApi();

        GetUserDataApi getUserDataApi = new GetUserDataApi((GetUserDataApi.OnGetUserDataApiHitFetchedListner) this);
        getUserDataApi.HitGetUserDataApi();


    }



    public void SetClickListeners() {

        tvMyOrders.setOnClickListener(v -> startActivity(new Intent(requireContext(), MyOrdersActivity.class)));

        tvMyAddress.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), MyAddresses.class));
        });

        if (Variables.departmentId != 2) {
            tvMyWishlist.setText("quantity");

            Drawable top = getResources().getDrawable(R.drawable.baseline_warehouse_24);
            tvMyWishlist.setCompoundDrawables(null, top, null, null);

            tvMyWishlist.setOnClickListener(v -> {

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });
        } else {
            tvMyWishlist.setOnClickListener(v -> {


                //Comment
                BottomNavigationView bottomNavigationView;
                bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.nav_view);
                bottomNavigationView.setSelectedItemId(R.id.navigation_wishlist);

                startActivity(new Intent(requireContext(), OrderQtyActivity.class));
            });
        }


        tvPayments.setOnClickListener(v -> startActivity(new Intent(requireContext(), MyPaymentsActivity.class)));


        tvFeedback.setOnClickListener(v -> {

            FeedBackDialog();
        });

        tvPrivacyPolicy.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), PrivacyPolicyActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.PRIVACY_POLICY_URL1));

            startActivity(i);
        });

        tvTermsConditions.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), TermNConditionWebViewActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.TERMS_COND_URL1));
            startActivity(i);
        });

        tvAboutUs.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), AboutUsWebViewActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.ABOUTUS));
            startActivity(i);
        });

        tvSupport.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), SupportActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            startActivity(i);

        });

        tvRRP.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), ReturnRefundPolicyActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.ABOUTUS));
            startActivity(i);
        });

        tvSecurity.setOnClickListener(v -> {

            startActivity(new Intent(requireContext(), SecurityActivity.class));
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.ABOUTUS));
            startActivity(i);
        });


    }


    public void FeedBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Feedback");

        // Create the EditText
        final EditText editText = new EditText(requireContext());
        builder.setView(editText);

        // Add the submit button
        builder.setPositiveButton("Submit", null); // Set the click listener to null for now

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Get the button from the dialog's view
        Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitButton.setOnClickListener(view -> {
            // Handle the submit button click
            String feedback = editText.getText().toString();

            if (feedback.isEmpty()) {
                editText.setError("Feedback cannot be empty");
            } else {
                // Do something with the feedback
                FeedBackApi feedBackApi = new FeedBackApi(this, feedback);
                feedBackApi.FeedbackHitApi();
                dialog.dismiss(); // Dismiss the dialog after handling the click
            }
        });
    }




    @Override
    public void OnProfileItemFeedback(String description) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), description, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnProfileItemFeedbackAPiGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------
    @Override
    public void OnGetRecentViewProductsAPIGivesResult
    (ArrayList<RecentProductModel> recentProductModels) {
        try {
            requireActivity().runOnUiThread(() -> {
                ProfileHomeProductsRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));

                ProfileHomeProductsRecycler.setAdapter(new RecentProductAdaptor(recentProductModels));

                ProfileHomeProductsRecycler.hideShimmerAdapter();

            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void OnGetRecentViewProductsAPIGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                ProfileHomeProductsRecycler.hideShimmerAdapter();

                tvRecentProducts.setVisibility(View.GONE);

                tvViewAll.setVisibility(View.GONE);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void getUserData(JSONObject ResultJson) {
        try {
            JSONArray resultArray = ResultJson.getJSONArray("result");
            ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels = new ArrayList<>();
            for (int i = 0; i < resultArray.length(); i++) {

                String status_code = ResultJson.getString("status_code");
                String privacyPolicy = ResultJson.getString("privacyPolicy");
                String termsAndConditions = ResultJson.getString("termsAndConditions");
                String returnRefundPolicy = ResultJson.getString("returnRefundPolicy");
                String security = ResultJson.getString("security");
                String aboutUs = ResultJson.getString("aboutUs");
                String message = ResultJson.getString("message");

                JSONArray userDetailsArray = ResultJson.getJSONArray("data");

                JSONObject userDetails = userDetailsArray.getJSONObject(0);

                JSONObject resultObject = resultArray.getJSONObject(i);
                int id = resultObject.getInt("id");
                int departmentId = resultObject.getInt("department_id");
                String userId = String.valueOf(resultObject.getInt("user_id"));



                String userCode = resultObject.optString("user_code", "");


                String name = resultObject.optString("name", "");
                String lastName = resultObject.optString("last_name", "");
                String email = resultObject.optString("email", "");
                String password = resultObject.optString("password", "");
                String mobile = resultObject.optString("mobile", "");
                String gender = resultObject.optString("gender", "");
                String dob = resultObject.optString("dob", "");
                String attachment = resultObject.optString("attachment", "");
                String city = resultObject.optString("city", "");
                String postCode = resultObject.optString("postCode", "");
                String address = resultObject.optString("address", "");
                String state = resultObject.optString("state", "");
                String country = resultObject.optString("country", "");
                String district = resultObject.optString("district", "");
                String street = resultObject.optString("street", "");
                String about = resultObject.optString("about", "");
                String status = resultObject.optString("status", "");
                String mobileVerified = resultObject.optString("mobile_verified", "");
                String isVerified = resultObject.optString("is_verified", "");
                String logDateCreated = resultObject.optString("log_date_created", "");
                String createdBy = resultObject.optString("created_by", "");
                String logDateModified = resultObject.optString("log_date_modified", "");
                String modifiedBy = resultObject.optString("modified_by", "");
                String fcmToken = resultObject.optString("fcm_token", "");
                String deviceId = resultObject.optString("device_id", "");
                String allowEmail = resultObject.optString("allow_email", "");
                String allowSms = resultObject.optString("allow_sms", "");
                String allowPush = resultObject.optString("allow_push", "");


                // Store values in static variables
                Variables.clear();

                Variables.status_code = status_code;
                Variables.privacyPolicy = privacyPolicy;
                Variables.termsAndConditions = termsAndConditions;
                Variables.returnRefundPolicy = returnRefundPolicy;
                Variables.security = security;
                Variables.aboutUs = aboutUs;
                Variables.message = message;

                Variables.id = id;
                Variables.departmentId = departmentId;
                Variables.userCode = userCode;
                Variables.name = name;
                Variables.lastName = lastName;
                Variables.email = email;
                Variables.password = password;
                Variables.mobile = mobile;
                Variables.gender = gender;
                Variables.dob = dob;
                Variables.attachment = attachment;
                Variables.city = city;
                Variables.postCode = postCode;
                Variables.address = address;
                Variables.state = state;
                Variables.country = country;
                Variables.district = district;
                Variables.street = street;
                Variables.about = about;
                Variables.status = status;
                Variables.mobileVerified = mobileVerified;
                Variables.isVerified = isVerified;
                Variables.logDateCreated = logDateCreated;
                Variables.createdBy = createdBy;
                Variables.logDateModified = logDateModified;
                Variables.modifiedBy = modifiedBy;
                Variables.fcmToken = fcmToken;
                Variables.deviceId = deviceId;
                Variables.allowEmail = allowEmail;
                Variables.allowSms = allowSms;
                Variables.allowPush = allowPush;


            }
            iv_edit.setVisibility(View.VISIBLE);
            Variables.name = tvProfileName.getText().toString();
            Variables.email = tvEmail.getText().toString();
            Variables.mobile = tvPhone.getText().toString();
            Variables.userCode = tvRole.getText().toString();

            startActivity(new Intent(requireContext(), ProfileFragment.class));
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            Toast.makeText(requireContext(), "System Error", Toast.LENGTH_SHORT).show();
            iv_edit.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_edit:

//                if (responseProfile != null) {
//                if (responseProfile1.getData() != null) {
//
//                    editProfileActivity2 = new EditProfileFragment2();
//
////                    intent.putExtra("data", new Gson().toJson(editProfileActivity2));
//
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("data", new Gson().toJson(responseProfile1.getData()));
//                    editProfileActivity2.setArguments(bundle);
//
//                    editProfileActivity2.setDismissListener(new EditProfileFragment2.DismissListener() {
//                        //EditProfileActivity2
//                        @Override
//                        public void onDismiss() {
////                            fetchProfileDetails();
//                        }
//                    });
//
//                }
                break;


            case R.id.ll_site_visit_req_LastUpdated4:
                SharedPrefs.getInstance(getActivity()).clearSharedPrefs();
                Intent intent1 = new Intent(getActivity(), SplashScreen.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;

            case R.id.tv_my_orders:

                startActivity(new Intent(requireContext(), MyOrdersActivity.class));
                break;

            case R.id.tv_my_address:

                startActivity(new Intent(requireContext(), MyAddresses.class));
                break;

            case R.id.tv_my_wishlist:
                if (Variables.departmentId != 2) {
                    tvMyWishlist.setText("quantity");

                    Drawable top = getResources().getDrawable(R.drawable.baseline_warehouse_24);
                    tvMyWishlist.setCompoundDrawables(null, top, null, null);

                    startActivity(new Intent(requireContext(), OrderQtyActivity.class));
                } else {



                    //Comment
                    BottomNavigationView bottomNavigationView;
                    bottomNavigationView = (BottomNavigationView) requireActivity().findViewById(R.id.nav_view);
                    bottomNavigationView.setSelectedItemId(R.id.navigation_wishlist);

                    startActivity(new Intent(requireContext(), OrderQtyActivity.class));
                    break;
                }


            case R.id.tv_payments:
                startActivity(new Intent(requireContext(), MyPaymentsActivity.class));
                break;


            case R.id.tv_feedback:
                FeedBackDialog();
                break;


            case R.id.tv_support:
                startActivity(new Intent(requireContext(), SupportActivity.class));
                Intent i = new Intent(Intent.ACTION_VIEW);

                break;


            case R.id.tv_privacy_policy:

                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.PRIVACY_POLICY_URL1));

                startActivity(i);
                break;

            case R.id.tv_Terms_Conditions:

                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.TERMS_COND_URL1));
                startActivity(i);
                break;


            case R.id.tv_About_us:

                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.ABOUTUS));
                startActivity(i);
                break;

            case R.id.tv_returnRefundPolicy:

                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.RETURN_REFUND_POLICY_URL1));
                startActivity(i);
                break;

            case R.id.tv_security:

                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Constants.SECURITY));
                startActivity(i);
                break;


        }
    }


    @Override
    public void OnUserDetailsUpdateApiGivesFetched(String message) {
        requireActivity().runOnUiThread(() -> {
            Loading.hide();

            SetTextViews();


            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

            GetUserDataApi getUserDataApi = new GetUserDataApi(this);
            getUserDataApi.HitGetUserDataApi();

            PopupDialog.getInstance(requireContext())
                    .setStyle(Styles.SUCCESS)
                    .setHeading("Well Done")
                    .setDescription("Successfully" +
                            " Edit Profile")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
            Loading.hide();
        });

    }

    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }


    @Override
    public void OnUserDetailsUpdateApiGivesError(String error) {
        requireActivity().runOnUiThread(() -> {

            SetTextViews();


            requireActivity().runOnUiThread(() ->
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show());
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();

            PopupDialog.getInstance(requireContext())
                    .setStyle(Styles.FAILED)
                    .setHeading("Uh-Oh")
                    .setDescription("Unexpected error occurred." +
                            " Try again later.")
                    .setCancelable(false)
                    .showDialog(new OnDialogButtonClickListener() {
                        @Override
                        public void onDismissClicked(Dialog dialog) {
                            super.onDismissClicked(dialog);
                        }
                    });
        });


    }


    @Override
    public void OnGetUserDataResultApiGivesSuccess(ArrayList<ProfileGetUserDataResponse.ProfileGetUserDataModel> profileGetUserDataModels) {
        try {
            requireActivity().runOnUiThread(() -> {

                SetTextViews();


                Log.e("TAG", String.valueOf(profileGetUserDataModels));


                GetUserDataApi getUserDataApi = new GetUserDataApi(this);
                getUserDataApi.HitGetUserDataApi();


            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }

        PopupDialog.getInstance(requireContext())
                .setStyle(Styles.SUCCESS)
                .setHeading("Well Done")
                .setDescription("Successfully" +
                        " Profile")
                .setCancelable(false)
                .showDialog(new OnDialogButtonClickListener() {
                    @Override
                    public void onDismissClicked(Dialog dialog) {
                        super.onDismissClicked(dialog);
                    }
                });

    }

    @Override
    public void OnGetUserDataResultApiGivesError(String error) {
        try {
            requireActivity().runOnUiThread(() -> {

                SetTextViews();


                try {

                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                PopupDialog.getInstance(requireContext())
                        .setStyle(Styles.FAILED)
                        .setHeading("Uh-Oh")
                        .setDescription("Unexpected error occurred." +
                                " Try again later.")
                        .setCancelable(false)
                        .showDialog(new OnDialogButtonClickListener() {
                            @Override
                            public void onDismissClicked(Dialog dialog) {
                                super.onDismissClicked(dialog);
                            }
                        });
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}