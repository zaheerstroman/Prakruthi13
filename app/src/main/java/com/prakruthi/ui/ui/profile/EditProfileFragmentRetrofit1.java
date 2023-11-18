package com.prakruthi.ui.ui.profile;

import static android.app.Activity.RESULT_OK;
import static com.prakruthi.ui.Utility.Constants.REQUEST_GALLERY_PHOTO;
import static com.prakruthi.ui.Utility.Constants.REQUEST_TAKE_PHOTO;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.prakruthi.R;
import com.prakruthi.databinding.ActivityEditProfileFragmentRetrofit1Binding;
//import com.prakruthi.databinding.ActivityEditProfileFragmentRetrofitBinding;
import com.prakruthi.ui.APIs.GetDropdownDataResponse;
import com.prakruthi.ui.Api.API_class;
import com.prakruthi.ui.Api.Retrofit_funtion_class;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.network.APIClient;
import com.prakruthi.ui.network.ApiInterface;
import com.prakruthi.ui.ui.UserDetails;
import com.prakruthi.ui.ui.WebViewActivityPayment;
import com.prakruthi.ui.ui.cart.CartModal;
import com.prakruthi.ui.ui.profile.response.BaseResponseGasaverTProperty;
import com.prakruthi.ui.utils.CommonUtils;
import com.prakruthi.ui.utils.Constants;
import com.prakruthi.ui.utils.FilePathUtils;
import com.prakruthi.ui.utils.SharedPrefs;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class EditProfileFragmentRetrofit1 extends AppCompatActivity implements View.OnClickListener{
//public class EditProfileFragmentRetrofit1 extends Fragment implements View.OnClickListener{
public class EditProfileFragmentRetrofit1 extends BottomSheetDialogFragment {

//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

//    public static String[] storage_permissions = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE
//    };

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] storage_permissions_33 = {Manifest.permission.READ_MEDIA_IMAGES};

    public static String user_id = String.valueOf(Variables.id);
    public static int id;
    public static String token = Variables.token;

    ImageView iv_close_http, iv_back;
    AppCompatButton backbtn_http1;

    ShapeableImageView iv_edit_profile;
    EditText et_first_name_http, et_email_http, et_city_http;
    Button btn_save_http;


    ProfileGetUserDataRetrofit profileDetail;
    ProfileGetUserDataRetrofit.Data profileDetails;

    private Uri imageUri;
    private String selectedPath = " ";
    private DismissListener dismissListener;

    ArrayList<String> districtNames = new ArrayList<>();

    PowerSpinnerView spinner_city, editTextState, editTextDistrict, spinner_state_http1, spinner_district_http1;

    String stateId;

    int stateIndex = 0;


    ActivityEditProfileFragmentRetrofit1Binding binding;

//    ArrayList<ProfileGetUserDataRetrofit.Data> states1 = new ArrayList<>();
//    ArrayList<ProfileGetUserDataRetrofit.Data> districts1 = new ArrayList<>();

    ArrayList<GetDropdownDataResponse.State> states1 = new ArrayList<>();
    ArrayList<GetDropdownDataResponse.District> districts1 = new ArrayList<>();

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_edit_profile_fragment_retrofit1, container, false);

        profileDetails = new Gson().fromJson(getArguments().getString("data"), ProfileGetUserDataRetrofit.Data.class);

        iv_close_http = view.findViewById(R.id.iv_close_http1);
        backbtn_http1 = view.findViewById(R.id.backbtn_http1);

        iv_edit_profile = view.findViewById(R.id.iv_edit_profile1);

        et_first_name_http = view.findViewById(R.id.et_first_name_http1);
        et_email_http = view.findViewById(R.id.et_email_http1);
        et_city_http = view.findViewById(R.id.et_city_http1);

        editTextState = view.findViewById(R.id.spinner_state_http1);
        editTextDistrict = view.findViewById(R.id.spinner_district_http1);

        btn_save_http = view.findViewById(R.id.btn_save_http1);


        et_first_name_http.setText(profileDetails.getName());
        et_email_http.setText(profileDetails.getEmail());
        et_city_http.setText(profileDetails.getCity());

        setdata();

        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
            //stateId = newIndex + 1;
            stateId = newItem;
            getDropDownData(editTextState, editTextDistrict);
            editTextDistrict.setText("District");
            //editTextState.setError(null);
        });

        //        editTextDistrict.setOnClickListener(v -> {
        //            if (stateId == 0) {
        //                editTextState.setError("Select State");
        //            } else editTextDistrict.showOrDismiss();
        //        });

        getDropDownData(editTextState, editTextDistrict);

        View root = view.getRootView();

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    editTextState.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = editTextState.getWidth();
                    int height = editTextState.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {

                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });


        btn_save_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextState.setError(null);
                editTextDistrict.setError(null);

                if (isAllFieldsValidated())

                    updateProfileDetails();
//                updateProfileDetails1();
            }
        });


        iv_close_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        init(view);
//

//        fetchCountry();
//        fetchStates();
        return view;
    }

    private void init(View v) {


//        editTextState.setText(profileDetails.getState());
//        editTextDistrict.setText(profileDetails.getDistrict());

        Glide.with(requireContext()).load(Constants.PROFILE_IMG_URL1 + profileDetails.getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(iv_edit_profile);

//        Glide.with(requireContext()).load(Constants.PROFILE_IMG_URL1 + profileDetails.getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivEditProfile1);
//        Glide.with(requireContext()).load(profileDetails.body().getBase_path() + response.body().getData().getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivEditProfile1);


        // Access the root view of the fragment


        // backbtn_http1.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                dismiss();
        //            }
        //        });


        setdata();
    }


    private void setdata() {

        try {

            et_first_name_http.setText((CharSequence) profileDetails.getName());

            et_email_http.setText((CharSequence) profileDetails.getEmail());

            et_city_http.setText((CharSequence) profileDetails.getCity());

            editTextState.setText((CharSequence) profileDetails.getState());
            editTextDistrict.setText((CharSequence) profileDetails.getDistrict());

//            editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
//                stateId = newItem;
//                getDropDownData(editTextState, editTextDistrict);
//                editTextDistrict.setText("District");
//            });
//
//            getDropDownData(editTextState, editTextDistrict);


//            editTextState.setText(UserDetails.state);
//            editTextDistrict.setText(UserDetails.district);


            Glide.with(requireContext()).load(Constants.PROFILE_IMG_URL1 + profileDetails.getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivEditProfile1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateProfileDetails() {
//        CommonUtils.showLoading(getActivity(), "Please Wait", false);
//        CommonUtils.showLoading(EditProfileFragmentRetrofit1.this, "Please Wait", false);
        CommonUtils.showLoading(requireContext(), "Please Wait", false);

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (!selectedPath.trim().isEmpty()) {
            File file = new File(selectedPath);
            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
            builder.addFormDataPart("file", file.getName(), fbody);
        }

        String city = "";
        String state = "";
        String country = "";


        builder
//                    .addFormDataPart("user_id", SharedPrefs.getInstance(requireContext()).getString(Constants.USER_ID))
                .addFormDataPart("user_id", String.valueOf(Variables.id))

//                .addFormDataPart("token", SharedPrefs.getInstance(requireContext()).getString(Constants.TOKEN))
                .addFormDataPart("token", Variables.token)
//                .addFormDataPart("token", SharedPrefs.getInstance(EditProfileFragmentRetrofit1.this).getString(Constants.TOKEN))

                .addFormDataPart("name", et_first_name_http.getText().toString())
                .addFormDataPart("email", et_email_http.getText().toString())
                .addFormDataPart("city", et_city_http.getText().toString())

                .addFormDataPart("state", String.valueOf(editTextState.getText().toString()))
                .addFormDataPart("district", String.valueOf(editTextDistrict.getText().toString()))
//                .addFormDataPart("country", String.valueOf(editTextCity))


                .build();

        Call<BaseResponseGasaverTProperty> call = apiInterface.updateProfile(builder.build());
        call.enqueue(new Callback<BaseResponseGasaverTProperty>() {
            @Override
            public void onResponse(Call<BaseResponseGasaverTProperty> call, Response<BaseResponseGasaverTProperty> response) {
                CommonUtils.hideLoading();
                if (response.body() != null && response.body().getStatusCode()) {
                    if (response.body().getStatusCode()) {
//                        Toast.makeText(getActivity(), "Profile Details Updated successfully", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "Profile Details Updated successfully", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(EditProfileFragmentRetrofit1.this, "Profile Details Updated successfully", Toast.LENGTH_SHORT).show();

                        dismissListener.onDismiss();
                        dismiss();
                    } else {
//                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(EditProfileFragmentRetrofit1.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponseGasaverTProperty> call, Throwable t) {
                CommonUtils.hideLoading();
//                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                Toast.makeText(EditProfileFragmentRetrofit1.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                t.printStackTrace();
            }
        });

    }

    ArrayList<LocationTableResponse.LocationTable> citys = new ArrayList<>();
    //    ArrayList<LocationTableResponse.LocationTable> states = new ArrayList<>();
    ArrayList<LocationTableResponse.LocationTable> districts = new ArrayList<>();

//    private void fetchStates() {
//        CommonUtils.showLoading(requireContext(), "Please Wait", false);
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("user_id", String.valueOf(Variables.id));
//        jsonObject.addProperty("token", Variables.token);
//
//        Call<LocationTableResponse> call = apiInterface.getDefaultDataLoc(jsonObject);
//        call.enqueue(new Callback<LocationTableResponse>() {
//            @Override
//            public void onResponse(Call<LocationTableResponse> call, Response<LocationTableResponse> response) {
//                states = new ArrayList<>();
//                LocationTableResponse locationTableResponse = response.body();
//                if (locationTableResponse != null && locationTableResponse.getLocationTables() != null) {
//                    states.addAll(locationTableResponse.getLocationTables());
//                }
//
//                ArrayList<String> stateNames = new ArrayList<>();
////                stateNames.add("Select");
//                stateNames.add("India");
//
//                int selPos = 0;
//
//                for (LocationTableResponse.LocationTable locationTable : states) {
//                    stateNames.add(locationTable.getName());
//                    if (profileDetails.getState().equals(locationTable.getName()))
//                        selPos = states.indexOf(locationTable) + 1;
//                }
//
//                CommonUtils.hideLoading();
//            }
//
//            @Override
//            public void onFailure(Call<LocationTableResponse> call, Throwable t) {
//                t.printStackTrace();
//                CommonUtils.hideLoading();
//            }
//        });
//
//    }

//    private void fetchCountry() {
//        CommonUtils.showLoading(requireContext(), "Please Wait", false);
//        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("user_id", String.valueOf(Variables.id));
//        jsonObject.addProperty("token", Variables.token);
////        jsonObject.addProperty("table_name", "country");
//
//        Call<LocationTableResponse> call = apiInterface.getDefaultDataLoc(jsonObject);
//        call.enqueue(new Callback<LocationTableResponse>() {
//            @Override
//            public void onResponse(Call<LocationTableResponse> call, Response<LocationTableResponse> response) {
//                districts = new ArrayList<>();
//                LocationTableResponse locationTableResponse = response.body();
//                if (locationTableResponse != null && locationTableResponse.getLocationTables() != null) {
//                    districts.addAll(locationTableResponse.getLocationTables());
//                }
//
//                ArrayList<String> stateNames = new ArrayList<>();
////                stateNames.add("Select");
//                stateNames.add("India");
//
//                int selPos = 0;
//                for (LocationTableResponse.LocationTable locationTable : districts) {
//                    stateNames.add(locationTable.getName());
//                    if (profileDetails.getDistrict() != null && profileDetails.getDistrict().equals(locationTable.getName()))
//                        selPos = districts.indexOf(locationTable) + 1;
//                }
////                spinner_district_http1.
////                        setAdapter(new ArrayAdapter<>(requireContext(), R.layout.list_units, stateNames));
////                spinner_district_http1.setSelection(selPos);
//                CommonUtils.hideLoading();
//            }
//
//            @Override
//            public void onFailure(Call<LocationTableResponse> call, Throwable t) {
//                t.printStackTrace();
//                CommonUtils.hideLoading();
//            }
//        });
//
//    }


    private boolean isAllFieldsValidated() {


        editTextState.setError(null);
        editTextDistrict.setError(null);


        if (TextUtils.isEmpty(et_first_name_http.getText().toString().trim())) {
            et_first_name_http.setError("Required");
            return false;

        }

        if (TextUtils.isEmpty(et_email_http.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(et_email_http.getText().toString()).matches()) {
            et_email_http.setError("Not Valid");
            return false;
        }

        if (TextUtils.isEmpty(et_city_http.getText().toString().trim())) {
            et_city_http.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(editTextState.getText())) {
            editTextState.setError("State cannot be empty");

            ObjectAnimator.ofFloat(editTextState, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            editTextState.requestFocus();

//            hasEmptyFields = true;
            return false;
        }
        if (TextUtils.isEmpty(editTextDistrict.getText())) {
            editTextDistrict.setError("District cannot be empty");

            ObjectAnimator.ofFloat(editTextDistrict, "translationX", 0, -10, 10, -10, 10, -10, 10, -10, 10, 0).start();
            editTextDistrict.requestFocus();

//            hasEmptyFields = true;
            return false;
        }

//        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
//            stateId = newItem;
//            getDropDownData(editTextState, editTextDistrict);
//            editTextDistrict.setText("District");
//        });
//
//        getDropDownData(editTextState, editTextDistrict);


//        editTextState.setText(UserDetails.state);
//        editTextDistrict.setText(UserDetails.district);


        return true;
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileFragmentRetrofit1.this);

        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {


                if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                    imageUri = EditProfileFragmentRetrofit1.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    imageUri = requireActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

//                    imageUri = FileProvider.getUriForFile(
//                            getActivity(),
//                            BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
//                            photo);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            } else if (items[item].equals("Choose from Library")) {


//                if ((Build.VERSION.SDK_INT >= 33) && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY_PHOTO);
//                }

                if ((Build.VERSION.SDK_INT >= 23) && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_GALLERY_PHOTO);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), REQUEST_GALLERY_PHOTO);
                }
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void setDismissListener(DismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

//    public static String[] permissions() {
//        String[] p;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            p = storage_permissions_33;
//        } else {
//            p = storage_permissions;
//        }
//        return p;
//    }


    public interface DismissListener {
        public void onDismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_PHOTO || requestCode == REQUEST_TAKE_PHOTO) {
            Log.e("selected profile img", requestCode + " " + resultCode);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = null;
                    String path = " ";
                    if (requestCode == REQUEST_TAKE_PHOTO) {
//                        selectedPath = FilePathUtils.getRealPath(getActivity(),imageUri);
//                        selectedPath = FilePathUtils.getRealPath(requireContext(), imageUri);
                        selectedPath = FilePathUtils.getRealPath(requireContext(), imageUri);
//                        selectedPath = FilePathUtils.getRealPath(EditProfileFragmentRetrofit1.this,imageUri);


//                        selectedPath = imageUri.getPath();
                        bitmap = BitmapFactory.decodeFile(selectedPath);
                    } else if (requestCode == REQUEST_GALLERY_PHOTO) {
//                        selectedPath = FilePathUtils.getRealPath(getActivity(), data.getData());
//                        selectedPath = FilePathUtils.getRealPath(requireContext(), data.getData());
                        selectedPath = FilePathUtils.getRealPath(requireContext(), data.getData());
//                        selectedPath = FilePathUtils.getRealPath(EditProfileFragmentRetrofit1.this, data.getData());

                        bitmap = BitmapFactory.decodeFile(selectedPath);
                    }
                    Log.e("selected profile img", selectedPath);
//                    iv_profile.setImageBitmap(bitmap);
                    iv_edit_profile.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TAKE_PHOTO) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //launch camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                File photo = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//                imageUri = EditProfileFragmentRetrofit1.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                imageUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


//                imageUri = FileProvider.getUriForFile(
//                        getActivity(),
//                        BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
//                        photo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            } else {
//                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
//                Toast.makeText(EditProfileFragmentRetrofit1.this, "camera permission denied", Toast.LENGTH_LONG).show();
                Toast.makeText(requireContext(), "camera permission denied", Toast.LENGTH_LONG).show();

            }
        } else if (requestCode == REQUEST_GALLERY_PHOTO) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //launch gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), REQUEST_GALLERY_PHOTO);
            } else {
//                Toast.makeText(getActivity(), "storage permission denied", Toast.LENGTH_LONG).show();
//                Toast.makeText(EditProfileFragmentRetrofit1.this, "storage permission denied", Toast.LENGTH_LONG).show();
                Toast.makeText(requireContext(), "storage permission denied", Toast.LENGTH_LONG).show();

            }
        }
    }


    public void updateDetails(ProfileGetUserDataRetrofit.Data profiledetails) {
        this.profileDetails = profiledetails;

        et_first_name_http.setText(profileDetails.getName());
        et_email_http.setText(profileDetails.getEmail());
        et_city_http.setText(profileDetails.getCity());

        editTextState.setText((CharSequence) profileDetails.getState());
        editTextDistrict.setText((CharSequence) profileDetails.getDistrict());

//        editTextState.setOnSpinnerItemSelectedListener((OnSpinnerItemSelectedListener<String>) (oldIndex, oldItem, newIndex, newItem) -> {
//            stateId = newItem;
//            getDropDownData(editTextState, editTextDistrict);
//            editTextDistrict.setText("District");
//        });
//
//        getDropDownData(editTextState, editTextDistrict);
//
//        editTextState.setText(UserDetails.state);
//        editTextDistrict.setText(UserDetails.district);


//        Glide.with(getActivity()).load(Constants.PROFILE_IMG_URL1 + profileDetails.getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivEditProfile1);
        Glide.with(requireContext()).load(Constants.PROFILE_IMG_URL1 + profileDetails.getAttachment()).error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivEditProfile1);


//        Glide.with(EditProfileFragmentRetrofit.this).load(profileDetails.getBase_path() + response.body().getData().getProfilePhoto())
//                .error(R.drawable.profile_img).error(R.drawable.profile_img).into(binding.ivProfileImg);

    }

    @SuppressLint("StaticFieldLeak")
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView District) {
        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {

//                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
//                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");
                FetchData fetchData = new FetchData("http://prakruthiepl.com/admin/api/getDropdownData");


                if (fetchData.startFetch()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        Log.i("getDropDownData", result);
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            return jsonObj;
//                            return new JSONObject(result);
                        } catch (JSONException e) {
                            return null;
                        }
                    }
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObj) {
                super.onPostExecute(jsonObj);

                if (jsonObj != null) {
                    try {

                        states1 = new ArrayList<>();
                        JSONArray states = jsonObj.getJSONArray("state");
                        ArrayList<String> stateNames = new ArrayList<>();
                        stateNames.add("Select");
                        int selPos = 0;
                        for (int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            stateNames.add(state.getString("name"));
                            selPos = states1.indexOf(state) + 1;
//                            selPos = states.editTextState.getSelectedIndex() + 1;
                        }
                        //-----or----
                        for (GetDropdownDataResponse.State locationTable : states1) {
                            stateNames.add(locationTable.getName());
                            if (profileDetails.getState().equals(locationTable.getName()))
                                selPos = states1.indexOf(locationTable) + 1;
//                            selPos = states1.editTextState.getSelectedIndex() + 1;
                        }

                        //----
                        State.setItems(stateNames);
                        JSONArray districts = jsonObj.getJSONArray("district");
                        districtNames.clear();
                        districtNames.add("Select");
                        int selPos1 = 0;
//                        ArrayList<String> districtNames = new ArrayList<>();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);
                            if (districtt.getString("state_name").equalsIgnoreCase(stateId)) {
//                                districtNames.add(districtt.getString("name"));
//                                spinner_district.setItems(districtNames);
//                                District.setItems(districtNames);
                                districtNames.add(districtt.getString("name"));
                                selPos1 = districts1.indexOf(districtt) + 1;
                            }

                        }

                        //-----

                        for (GetDropdownDataResponse.District locationTable : districts1) {
                            districtNames.add(locationTable.getName());
                            if (profileDetails.getDistrict().equals(locationTable.getName()))
                                selPos1 = districts1.indexOf(locationTable) + 1;
//                            selPos = states1.editTextState.getSelectedIndex() + 1;
                        }


                        District.setItems(districtNames);

                        //------------------------------------------
//                        ProfileGetUserDataRetrofit.Data profileDetails;

//                        states1 = new ArrayList<>();

//                        ArrayList<String> districtNames = new ArrayList<>();
//                        ArrayList<String> stateNames1 = new ArrayList<>();
//                        stateNames1.add("Select");
//                        ArrayList<String> districtNames1 = new ArrayList<>();
//                        districtNames1.add("Select");


//                        if (ProfileGetUserDataRetrofit.Data != null && ProfileGetUserDataRetrofit.Data() != null) {
//                        if (locationTableResponse != null && locationTableResponse.getLocationTables() != null) {
//                            states1.addAll(locationTableResponse.getLocationTables());
//                        }


//                        int selPos = 0;

//                        for (ProfileGetUserDataRetrofit.Data locationTable : states1) {
//                        for (GetDropdownDataResponse.State locationTable : states1) {
//                            stateNames1.add(locationTable.getName());
//                            if (profileDetails.getState().equals(locationTable.getName()))
//                                selPos = states1.indexOf(locationTable) + 1;
//                                selPos = states1.editTextState.getSelectedIndex() + 1;
//
//
//                        }


                    } catch (JSONException e) {
                        Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


}