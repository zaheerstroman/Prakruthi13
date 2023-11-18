package com.prakruthi.ui.ui.productPage;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prakruthi.R;
import com.prakruthi.ui.APIs.AddToBuy;
import com.prakruthi.ui.APIs.AddToCart;
import com.prakruthi.ui.APIs.DeleteCartDetails;
import com.prakruthi.ui.Variables;
import com.prakruthi.ui.misc.Loading;
import com.prakruthi.ui.ui.cart.CartModal;
import com.prakruthi.ui.ui.cart.CartRecyclerAdaptor;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyRecyclerAdaptor extends RecyclerView.Adapter<BuyRecyclerAdaptor.ViewHolder> {

    private List<ProductModel> productModels;
    ArrayList<CartModal> cartCategoryModalList = new ArrayList<>();
    Context context;
    AddToBuy.OnAddToBuyDataFetchedListner listner;
    DeleteCartDetails.OnCartItemDeleteAPiHit Deletelistner;

    public BuyRecyclerAdaptor(Context context, ArrayList<CartModal> cartCategoryModalList, AddToBuy.OnAddToBuyDataFetchedListner listner, DeleteCartDetails.OnCartItemDeleteAPiHit Deletelistner) {
        this.cartCategoryModalList.clear();
        this.cartCategoryModalList = cartCategoryModalList;
        this.context = context;
        this.listner = listner;
        this.Deletelistner = Deletelistner;
    }

    @NonNull
    @Override
    public BuyRecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buylistlayout, parent, false);

        return new BuyRecyclerAdaptor.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BuyRecyclerAdaptor.ViewHolder holder, int position) {

        try {

            CartModal cartData = cartCategoryModalList.get(position);
            holder.CartProductName.setText(cartData.getName());
            holder.CartProductSubInformation.setText(cartData.getDescription());

            Spanned spanned = Html.fromHtml(cartData.getDescription());
            holder.CartProductSubInformation.setText(spanned);

            if (Variables.departmentId.equals(2)) {

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getCustomerPrice());
            } else if (Variables.departmentId.equals(3)) {

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getDealerPrice());
            } else if (Variables.departmentId.equals(4)) {

                holder.CartProductPrice.setText("₹  ");
                holder.CartProductPrice.append(cartData.getMartPrice());
            }

            holder.CartProductQuantity.setText(String.valueOf(cartData.getQuantity()));
            Glide.with(context)
                    .load(cartCategoryModalList.get(position).getAttachment())
                    .placeholder(R.drawable.baseline_circle_24)
                    .into(holder.CartProductImage);

            holder.CartProductImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.CartProductImage.getContext().startActivity(new Intent(holder.itemView.getContext(), ProductPage.class)
                            .putExtra("productId", String.valueOf(cartData.getProduct_id())));

                }
            });

            holder.CartProductDelete.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                DeleteCartDetails deleteCartDetails = new DeleteCartDetails(Deletelistner, cartData.getId());
                deleteCartDetails.HitApi();
            });

            holder.minus.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                AddToBuy addToBuy = new AddToBuy(String.valueOf(cartData.getProduct_id()), String.valueOf(1), String.valueOf(cartData.getId()), true, listner);
                addToBuy.fetchData();
            });
            holder.plus.setOnClickListener(v -> {
                Loading.show(holder.itemView.getContext());
                AddToBuy addToBuy = new AddToBuy(String.valueOf(cartData.getProduct_id()), String.valueOf(1),  String.valueOf(cartData.getId()), false, listner);
                addToBuy.fetchData();
            });
            if (cartData.getQuantity() <= 1) {
                holder.minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#020202")));
                holder.minus.setClickable(true);
            } else if (cartData.getQuantity() >= 2) {
                holder.minus.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C6E4FF")));
                holder.minus.setClickable(true);
            }
            if (position % 2 == 0) {
                holder.cartlistlayoutbackground.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#92C8F9"))); // Set elevation for even positions.
            } else {
                holder.cartlistlayoutbackground.setElevation(0f); // Remove elevation for odd positions.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return cartCategoryModalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatButton plus, minus;

        RelativeLayout cartlistlayoutbackground;

        AppCompatImageButton CartProductDelete;
        public CircleImageView CartProductImage;
        public TextView CartProductName, CartProductSubInformation, CartProductPrice, CartProductQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CartProductName = itemView.findViewById(R.id.CartProductName);
            CartProductSubInformation = itemView.findViewById(R.id.CartProductSubInformation);
            CartProductPrice = itemView.findViewById(R.id.CartProductPrice);
            CartProductImage = itemView.findViewById(R.id.CartProductImage);
            plus = itemView.findViewById(R.id.CartProductPlus);
            minus = itemView.findViewById(R.id.CartProductMinus);

            CartProductQuantity = itemView.findViewById(R.id.CartProductQuantity);
            CartProductDelete = itemView.findViewById(R.id.CartProductDelete);

            CartProductName.setSelected(true);
            CartProductSubInformation.setSelected(true);

            cartlistlayoutbackground = itemView.findViewById(R.id.cartlistlayoutbackground);


        }


    }

}
