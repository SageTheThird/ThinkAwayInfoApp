package com.homie.psychq.main.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homie.psychq.R;
import com.homie.psychq.room2.PostFavEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/*Adapter for populating Posts in recyclerView fetched from Database*/

public class FavouritesAdapterPosts extends RecyclerView.Adapter<FavouritesAdapterPosts.ViewHolder> {


    private static final String TAG = "PhotosAdapter";

    private List<PostFavEntity> items_list;
    private Context mContext;
    private OnPostClickedListener mListener;


    @Inject
    public FavouritesAdapterPosts(Context context, OnPostClickedListener listener) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        items_list =new ArrayList<>();
        mContext = context;
        mListener=listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_psych_item_layout8, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



      holder.category.setText(items_list.get(position).getCategory());
      holder.title.setText(items_list.get(position).getTitle());


        Glide.with(mContext).load(items_list.get(position).getThumbnail())
                .transition(withCrossFade())
                .into(holder.image);

      holder.view2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mListener.photoClicked(items_list.get(position),holder.image,holder.getAdapterPosition());
          }
      });
//        Picasso.with(mContext)
//                .load(items_list.get(position).getTop_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.top_image);
//
//        Picasso.with(mContext)
//                .load(items_list.get(position).getLeft_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.left_image);
//        Picasso.with(mContext)
//                .load(items_list.get(position).getRight_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.right_image);

    }

    public void addPhotos(List<PostFavEntity> photos){
        int lastCount = getItemCount();
        items_list.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    public void clearList(){
        if(items_list !=null){
            items_list.clear();
        }
    }

    @Override
    public int getItemCount() {
        return items_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view2;
        public ImageView image;
        public TextView category;
        public TextView title;
        public ViewHolder(View view) {
            super(view);

            view2=view;
            image=view2.findViewById(R.id.psych_featured_thumb);
            category=view2.findViewById(R.id.psych_feautred_category);
            title=view2.findViewById(R.id.psych_feautred_item_tv2);

        }
    }

    public interface OnPostClickedListener {
        void photoClicked(PostFavEntity postFavEntity, ImageView imageView, int position);
    }
}