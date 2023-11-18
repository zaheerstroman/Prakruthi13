package com.prakruthi.ui.ui.productPage;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.prakruthi.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPagerAdaptor extends RecyclerView.Adapter<ProductPagerAdaptor.ProductViewHolder> {

    public ArrayList<ProductModel> list1;

    Context context;
    List<String> list;

    public ProductPagerAdaptor(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.list1 = list1;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_page_viewpager_imageview, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        List<String> productModel = Collections.singletonList(list.get(position));

        // Load image using Glide library
        Glide.with(context)
                .load(list.get(position))
                .error(R.drawable.profile_img)
                .into(holder.productImage);

        Glide.with(context).load(productModel.get(position)).error(R.drawable.profile_img).into(holder.productImage);

//        Log.e(TAG, productModel.getAttachments());
        holder.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(context, "Item is clicked", Toast.LENGTH_SHORT).show();
                Log.e("Width", "" + Resources.getSystem().getDisplayMetrics().widthPixels);

                //---------------------------------------------

                if (productModel != null) {

//                    List<String> attachments = productModel.getAttachments();
                    List<String> attachments = Collections.singletonList(productModel.get(position));

                    if (attachments != null && !attachments.isEmpty()) {
                        final ImagePopup imagePopup = new ImagePopup(context);
                        imagePopup.setBackgroundColor(Color.WHITE);
                        imagePopup.setFullScreen(false);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setMaxWidth(60);
                        imagePopup.setMaxHeight(500);

                        imagePopup.initiatePopupWithPicasso( attachments.get(0)); // Assuming you want to show the first attachment
                        imagePopup.viewPopup();



                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image_view);
//            Drawable drawable = getResources().getDrawable(R.drawable.profile_img);
//            productImage.setImageDrawable(drawable);

        }
    }
}
