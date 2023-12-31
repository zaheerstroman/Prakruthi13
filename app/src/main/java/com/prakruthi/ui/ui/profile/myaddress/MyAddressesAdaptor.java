package com.prakruthi.ui.ui.profile.myaddress;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.prakruthi.R;
import com.prakruthi.ui.APIs.DeleteDeliveryAddressDetails;
import com.prakruthi.ui.APIs.GetUserData;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.AddressUserDetails;
import com.prakruthi.ui.ui.home.address.Address_BottomSheet_Recycler_Adaptor_Model;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesAdaptor extends RecyclerView.Adapter<MyAddressesAdaptor.ViewHolder> {

    ImageView iv_edit_yourAddress;

    private ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList = new ArrayList<>();

    public Context context;

    EditText cityEditText, stateEditText, addressEditText, postalCodeEditText, cityEditText_edit, stateEditText_edit;

    View dialogView;
    private List<String> data;
    private LayoutInflater inflater;
    DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner;

    public MyAddressesAdaptor(ArrayList<Address_BottomSheet_Recycler_Adaptor_Model> addressModelsList, DeleteDeliveryAddressDetails.OnDeleteDeliveryAddressApiHits deleteDeliveryAddressListner) {
        this.addressModelsList.clear();
        this.addressModelsList = addressModelsList;
        this.deleteDeliveryAddressListner = deleteDeliveryAddressListner;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_myaddress_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            Address_BottomSheet_Recycler_Adaptor_Model addressModel = addressModelsList.get(position);

            holder.txtName.setText(addressModel.getName());
            holder.txtAddress.setText(addressModel.getAddress());

            holder.txt_personal_my_address_state.setText(addressModel.getState());
            holder.txt_personal_my_address_city.setText(addressModel.getCity());


            holder.txtRemove.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                DeleteDeliveryAddressDetails deleteDeliveryAddressDetails = new DeleteDeliveryAddressDetails(deleteDeliveryAddressListner, addressModel.getId());
                deleteDeliveryAddressDetails.HitApi();
            });

            holder.iv_edit_yourAddress.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());

            });



//            ProfileFragmentHttpURLConnection/Edit:------

            holder.iv_edit_yourAddress.setOnClickListener(v -> {
                GetUserData getUserData = new GetUserData(new GetUserData.OnGetUserDataFetchedListener() {

                    @Override
                    public void onUserDataFetched() {

                        try {

                        } catch (Exception ignore) {


                        }

                    }

                    @Override
                    public void onUserDataFetchError(String error) {


                    }
                });
                getUserData.HitGetUserDataApi();

                iv_edit_yourAddress.setClickable(false);


            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showDialog() {

        final Dialog dialogView = new Dialog(context);
        dialogView.setContentView(R.layout.dialog_edit_address);
        dialogView.setCancelable(true);

        // Set the dialog's window width to match_parent
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialogView.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogView.getWindow().setAttributes(layoutParams);


        EditText nameEditText = dialogView.findViewById(R.id.edittext_name_edit);
        stateEditText = dialogView.findViewById(R.id.editTextState_edit);
        cityEditText = dialogView.findViewById(R.id.edittext_city_edit);
        EditText countryEditText = dialogView.findViewById(R.id.edittext_country_edit);
        EditText addressEditText = dialogView.findViewById(R.id.edittext_address_edit);
        EditText postalCodeEditText = dialogView.findViewById(R.id.edittext_postal_code_edit);
        CheckBox defaultCheckBox = dialogView.findViewById(R.id.checkbox_default_edit);

        Button buttonSubmit = dialogView.findViewById(R.id.edit_address_btn_save);

        // Add click listener to the Submit button
        nameEditText.setText(AddressUserDetails.name);
        stateEditText.setText(AddressUserDetails.state);
        cityEditText.setText(AddressUserDetails.city);
        countryEditText.setText(AddressUserDetails.country);
        addressEditText.setText(AddressUserDetails.address);
        postalCodeEditText.setText(AddressUserDetails.postal_code);
        defaultCheckBox.setText(AddressUserDetails.is_default);

    }


    @Override
    public int getItemCount() {
        return addressModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtAddress;

        TextView txt_personal_my_address_state, txt_personal_my_address_city;

        EditText cityEditText, stateEditText, addressEditText, postalCodeEditText, cityEditText_edit, stateEditText_edit;

        ImageView iv_edit_yourAddress;

        AppCompatButton txtRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.myAddresses_Name);
            txtAddress = itemView.findViewById(R.id.txt_personal_my_address);
            txt_personal_my_address_state = itemView.findViewById(R.id.txt_personal_my_address_state);
            txt_personal_my_address_city = itemView.findViewById(R.id.txt_personal_my_address_city);

            iv_edit_yourAddress = itemView.findViewById(R.id.iv_edit_yourAddress);
            txtRemove = itemView.findViewById(R.id.txt_remove);
        }
    }
}
