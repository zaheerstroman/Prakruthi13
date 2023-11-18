package com.prakruthi.ui.ui.profile.myaddress;

import static com.prakruthi.ui.Variables.postalCode;
import static com.prakruthi.ui.ui.AddressUserDetails.address;
import static com.prakruthi.ui.ui.AddressUserDetails.city;
import static com.prakruthi.ui.ui.AddressUserDetails.country;
import static com.prakruthi.ui.ui.AddressUserDetails.is_default;
import static com.prakruthi.ui.ui.AddressUserDetails.name;
import static com.prakruthi.ui.ui.AddressUserDetails.state;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.DeleteDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetMyDeliveryAddressDetailsAsyncTask;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetails;
import com.prakruthi.ui.APIs.SaveDeliveryAddressDetailsEditUpdate;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddressUserDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;
//import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model_EditUpdate;

import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAddresses extends AppCompatActivity implements GetDeliveryAddressDetails.DeliveryAddressListener, SaveDeliveryAddressDetails.OnSaveDeliveryAddressApiHits, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits {

    ShimmerRecyclerView myAddresses_personal_address_recyclerview_List;

    AppCompatButton btn_add_new_address, txtRemove;

    ImageView iv_edit_yourAddress;

    public String id;

    EditText nameEditText, countryEditText;
    EditText cityEditText, stateEditText, idEditText, addressEditText, postalCodeEditText;

    AppCompatButton myAddress_back_btn;

    int stateId = 0;

    PowerSpinnerView spinner_city, editTextState, editTextDistrict;

    ArrayList<String> cityNames = new ArrayList<>();

    GetMyDeliveryAddressDetails.GetMyDeliveryAddressDetailsFetchedListener mListner;
    GetMyDeliveryAddressDetailsAsyncTask.onGetMyDeliveryAddressDetailsDataFetchedListner mListner1;


    @SuppressLint({"WrongViewCast", "RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        myAddress_back_btn = findViewById(R.id.myAddress_back_btn);

        myAddress_back_btn.setOnClickListener(v -> super.onBackPressed());


        getDeliveryAddressDetails();

        myAddresses_personal_address_recyclerview_List = findViewById(R.id.myAddresses_personal_address_recyclerview_List);

        // set an OnTouchListener to the root view
        View root = findViewById(android.R.id.content);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // check if the touch is outside of the state view
                    int[] location = new int[2];
                    stateEditText.getLocationOnScreen(location);
                    int x = location[0];
                    int y = location[1];
                    int width = stateEditText.getWidth();
                    int height = stateEditText.getHeight();
                    if (!(event.getX() > x && event.getX() < x + width && event.getY() > y && event.getY() < y + height)) {

                        return true; // consume the event
                    }
                }
                return false; // do not consume the event
            }
        });

        btn_add_new_address = findViewById(R.id.btn_add_new_address);

        iv_edit_yourAddress = findViewById(R.id.iv_edit_yourAddress);


        btn_add_new_address.setOnClickListener(v -> {
            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Add New Address");
            // Set the custom layout for the dialog
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_address, null);
            builder.setView(dialogView);

            EditText nameEditText = dialogView.findViewById(R.id.edittext_name);
            stateEditText = dialogView.findViewById(R.id.editTextState);
            cityEditText = dialogView.findViewById(R.id.edittext_city);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country);
            EditText addressEditText = dialogView.findViewById(R.id.edittext_address);
            EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code);
            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default);

            // Add click listener to the Submit button
            nameEditText.setText(name);
            stateEditText.setText(state);
            cityEditText.setText(city);
            countryEditText.setText(country);
            addressEditText.setText(address);
            postalCodeEditText.setText(AddressUserDetails.postal_code);
            defaultCheckBox.setText(is_default);

            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {

                    stateEditText.setError(null);
                    cityEditText.setError(null);

                    String name = nameEditText.getText().toString();
                    String state = stateEditText.getText().toString();
                    String city = cityEditText.getText().toString();
                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();


                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
                        hasError = true;
                    }

                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation
                        SaveDeliveryAddressDetails saveDeliveryAddressDetails = new SaveDeliveryAddressDetails(this, name, state, city, country, address, postalCode, boolToInt(isDefault));

                        saveDeliveryAddressDetails.HitApi();
                        Loading.show(this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });
            });

            dialog.show();
        });

        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);

        getDeliveryAddressDetails.execute();

        //    ProfileFragmentHttpURLConnection/Edit(Half):------------------------------------------------------------------------------

        iv_edit_yourAddress = findViewById(R.id.iv_edit_yourAddress);

    }

    public void getDeliveryAddressDetails() {
        GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
        getDeliveryAddressDetails.execute();
    }

    //Add Address type:--
    private void showDialog() {
        myAddresses_personal_address_recyclerview_List.setOnClickListener(v -> {

            final Dialog dialogView = new Dialog(MyAddresses.this);
            dialogView.setContentView(R.layout.dialog_edit_address);
            dialogView.setCancelable(true);

            // Create an AlertDialog Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MyAddresses.this);
            builder.setTitle("Edit New Address");

            // Set the custom layout for the dialog

            EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
            stateEditText = dialogView.findViewById(R.id.editTextState_edit);
            cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
            EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
            EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
            EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
            CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);

            Button buttonSubmit = dialogView.findViewById(R.id.edit_address_btn_save);

            // Add click listener to the Submit button
            nameEditText.setText(name);
            stateEditText.setText(state);
            cityEditText.setText(city);
            countryEditText.setText(country);
            addressEditText.setText(address);
            postalCodeEditText.setText(AddressUserDetails.postal_code);
            defaultCheckBox.setText(AddressUserDetails.is_default);


            // Set the positive and negative buttons
            builder.setPositiveButton("Save", null); // Set null initially

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                // Handle the cancel button click
                // Dismiss the dialog
                dialog.dismiss();
            });

            // Create and show the dialog
            AlertDialog dialog = builder.create();

            // Override the onClickListener for the positive button
            dialog.setOnShowListener(dialogInterface -> {
                Button saveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                saveButton.setOnClickListener(view -> {

                    stateEditText.setError(null);
                    cityEditText.setError(null);


                    // Handle the save button click
                    String name = nameEditText.getText().toString();

                    String state = stateEditText.getText().toString();
//                    String state = String.valueOf(stateEditText.getSelectedIndex() + 1);

                    String city = cityEditText.getText().toString();
//                    String city = String.valueOf(cityEditText.getSelectedIndex() + 1);

                    String country = countryEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postalCode = postalCodeEditText.getText().toString();
                    boolean isDefault = defaultCheckBox.isChecked();


                    // Check if any of the fields are empty
                    boolean hasError = false;
                    if (name.isEmpty()) {
                        nameEditText.setError("Name is required");
                        hasError = true;
                    }
                    if (state.isEmpty()) {
                        stateEditText.setError("State is required");
                        hasError = true;
                    }
                    if (city.isEmpty()) {
                        cityEditText.setError("City is required");
                        hasError = true;
                    }
                    if (country.isEmpty()) {
                        countryEditText.setError("Country is required");
                        hasError = true;
                    }
                    if (address.isEmpty()) {
                        addressEditText.setError("Address is required");
                        hasError = true;
                    }
                    if (postalCode.isEmpty()) {
                        postalCodeEditText.setError("Postal Code is required");
                        hasError = true;
                    }

                    if (!hasError) {
                        // All fields are properly filled, perform the save operation

                        Loading.show(MyAddresses.this);
                        // Dismiss the dialog
                        dialog.dismiss();
                    }

                });
            });

            dialog.show();
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void getDropDownData(PowerSpinnerView State, PowerSpinnerView City) {

        new AsyncTask<Void, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(Void... voids) {
//                FetchData fetchData = new FetchData("https://houseofspiritshyd.in/prakruthi/admin/api/getDropdownData");
                FetchData fetchData = new FetchData("https://prakruthiepl.com/admin/api/getDropdownData");

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

                        JSONArray states = jsonObj.getJSONArray("state");
                        ArrayList<String> stateNames = new ArrayList<>();
                        for (int i = 0; i < states.length(); i++) {
                            JSONObject state = states.getJSONObject(i);
                            stateNames.add(state.getString("state"));
                        }

                        //----------
                        JSONArray districts = jsonObj.getJSONArray("city");
                        cityNames.clear();
                        for (int i = 0; i < districts.length(); i++) {
                            JSONObject districtt = districts.getJSONObject(i);

                            if (districtt.getInt("state_id") == stateId) {

                                cityNames.add(districtt.getString("city"));

                            }


                            cityNames.add(districtt.getString("city"));
                        }
                        City.setItems(cityNames);


                    } catch (JSONException e) {
                        Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyAddresses.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public int boolToInt(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void onDeliveryAddressLoaded(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressList) {
        try {
            MyAddresses.this.runOnUiThread(() -> {
                myAddresses_personal_address_recyclerview_List.hideShimmerAdapter();
                myAddresses_personal_address_recyclerview_List.setLayoutManager(new LinearLayoutManager(this));
                myAddresses_personal_address_recyclerview_List.setAdapter(new MyAddressesAdaptor(addressList, this));

            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    @Override
    public void onSaveDeliveryAddress(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();

            PopupDialog.getInstance(this).setStyle(Styles.SUCCESS).setHeading("Well Done").setDescription("Successfully" + " Added New Address").setCancelable(false).showDialog(new OnDialogButtonClickListener() {
                @Override
                public void onDismissClicked(Dialog dialog) {
                    super.onDismissClicked(dialog);
                }
            });
            Loading.hide();
        });
    }

    @Override
    public void onSaveDeliveryAddressApiError(String error) {
        runOnUiThread(() -> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void OnDeleteDeliveryAddress(String message) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();

            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();
        });
    }

    @Override
    public void OnOnDeleteDeliveryAddressApiGivesError(String error) {
        MyAddresses.this.runOnUiThread(() -> {
            Loading.hide();
            Toast.makeText(MyAddresses.this, error, Toast.LENGTH_SHORT).show();

            myAddresses_personal_address_recyclerview_List.showShimmerAdapter();
            GetDeliveryAddressDetails getDeliveryAddressDetails = new GetDeliveryAddressDetails(this);
            getDeliveryAddressDetails.execute();

        });
    }


}