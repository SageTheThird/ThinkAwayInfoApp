package com.homie.psychq.main.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homie.psychq.R;
import com.homie.psychq.room2.ArticleFavEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/*Adapter for populating articles in recyclerView fetched from Database*/

public class FavouritesAdapterArticles extends RecyclerView.Adapter<FavouritesAdapterArticles.ViewHolder> {


    private static final String TAG = "PhotosAdapter";

    private List<ArticleFavEntity> items_list;
    private Context mContext;
    private OnArticleFavClickedListener mListener;


    @Inject
    public FavouritesAdapterArticles(Context context, OnArticleFavClickedListener listener) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        items_list =new ArrayList<>();
        mContext = context;
        mListener=listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item_layout_test2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.titles_tv.setText(items_list.get(position).getTitle());
        String number__ = String.valueOf(items_list.get(position).getCourse_count());
        holder.number_tv.setText(number__);
//        holder.content_tv.setText(items_list.get(position).getArticle_content());


//        if(articles_list.get(position).getId()
//                .equals(sharedPreferences.getString("pinnedArticleId",null))){
//
//            holder.pin_iv.setVisibility(View.VISIBLE);
//
//        }else {
//
//            holder.pin_iv.setVisibility(View.GONE);
//
//        }

        /*
         * From testing override doesnt work with transform for round corners
         * - leave just transform(new CenterCrop, new ROundCorner()) when the images are of optimal sizes i-e
         * just barely fitting in its imageView
         * */
        Glide.with(mContext).load(items_list.get(position).getThumbnail())
                .override(100,100)
//              .transform(new CenterCrop(),new RoundedCorners(20))
                .into(holder.thumb);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.articleClicked(items_list.get(position),holder.layout,holder.getAdapterPosition());

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

    public void addPhotos(List<ArticleFavEntity> photos){
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

        CardView layout;
        public TextView number_tv;
        public TextView titles_tv;
        public TextView content_tv;
        public Button see_more_btn;
        public  ImageView thumb;
        public  ImageView pin_iv;
        //        public TextView pages_count_tv;
//        public TextView description;
        public ViewHolder(View view) {
            super(view);

            view2=view;
            layout=view2.findViewById(R.id.article_linear2);
            number_tv=view2.findViewById(R.id.article_number_);
            titles_tv=view2.findViewById(R.id.article_title);
//            content_tv=view2.findViewById(R.id.article_content);
//            see_more_btn=view2.findViewById(R.id.article_seeMore);
            thumb=view2.findViewById(R.id.article_thumb);
            pin_iv=view2.findViewById(R.id.pin_iv);
        }
    }

    public interface OnArticleFavClickedListener {
        void articleClicked(ArticleFavEntity articleFavEntity, CardView imageView, int position);
    }
}