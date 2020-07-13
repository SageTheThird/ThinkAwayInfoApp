package com.homie.psychq.main.ui.categories;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.homie.psychq.R;
import com.homie.psychq.main.models.categories.CategoryFeatured;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CategoriesAdapterPsych extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = "CategoriesAdapterPsych";
    public static final int TYPE_CATEGORY = 101;
    public static final int TYPE_HEADER = 102;

    private List<CategoryFeatured> cat_list;
    private Context mContext;
    private OnPhotoClickedListener mListener;
    private SharedPreferences sharedPreferences;


    @Inject
    public CategoriesAdapterPsych(Context context,OnPhotoClickedListener listener) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        cat_list =new ArrayList<>();
        sharedPreferences = new SharedPreferences(context);
        mContext = context;
        mListener=listener;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder holder = null;

        switch (viewType){
            case TYPE_CATEGORY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.psych_categories_item, parent, false);
                holder = new CategoryHolder(view);
                break;

            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_crash_category, parent, false);
                holder = new HeaderHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }

        return TYPE_CATEGORY;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        switch (holder.getItemViewType()){
            case TYPE_CATEGORY:


                CategoryHolder categoryHolder = (CategoryHolder) holder;

                setCategoryData(categoryHolder,position);

                break;

            case TYPE_HEADER:

                //header Holder
                HeaderHolder holder2 = (HeaderHolder) holder;
                holder2.heading.setText(sharedPreferences.getString(mContext.getString(R.string.CategoriesHeading),"ABOUT"));
                holder2.message.setText(sharedPreferences.getString(mContext.getString(R.string.CategoriesMessage),mContext.getString(R.string.categories_general_description)));


                if(!sharedPreferences.getBooleanPref(mContext.getString(R.string.isSubscribed),false)){
                    holder2.updatesNote.setVisibility(View.VISIBLE);
                }else {
                    holder2.updatesNote.setVisibility(View.GONE);
                }

                break;
        }

    }

    private void setCategoryData(CategoryHolder holder, int position) {

        String photos_count=String.valueOf(cat_list.get(position).getTotal_posts_count()) + " Posts";
        holder.titles_tv.setText(cat_list.get(position).getTitle());
//        holder.photos_count_tv.setText(photos_count);
        holder.description.setText(cat_list.get(position).getDescription());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.format(DecodeFormat.PREFER_RGB_565);
        requestOptions.override(330,440);

        Glide.with(mContext).load(cat_list.get(position).getPic_left())
                .error(R.drawable.ic_pitcher)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(holder.left_image);

        Glide.with(mContext).load(cat_list.get(position).getPic_right())
                .error(R.drawable.ic_pitcher)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(holder.right_image);

        Glide.with(mContext).load(cat_list.get(position).getPic_top())
                .error(R.drawable.ic_pitcher)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(holder.top_image);

        holder.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.photoClicked(cat_list.get(position),holder.top_image,holder.getAdapterPosition());
            }
        });
    }

    public void addPhotos(List<CategoryFeatured> photos){
        int lastCount = getItemCount();
        cat_list.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    public void clearList(){
        if(cat_list !=null){
            cat_list.clear();
        }
    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        View view2;
        public ImageView top_image;
        public ImageView left_image;
        public ImageView right_image;
        public TextView titles_tv;
//        public TextView photos_count_tv;
        public TextView description;
        public CategoryHolder(View view) {
            super(view);

            view2=view;
            top_image=view2.findViewById(R.id.top_image_cat_psych);
            left_image=view2.findViewById(R.id.left_image_cat_psych);
            right_image=view2.findViewById(R.id.right_image_cat_psych);
            titles_tv=view2.findViewById(R.id.title_cat_psych);
//            photos_count_tv=view2.findViewById(R.id.photos_count_cat_psych);
            description=view2.findViewById(R.id.description_cat_psych);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        View view2;
        public TextView heading,message,updatesNote;
        //        public TextView pages_count_tv;
//        public TextView description;
        public HeaderHolder(View view) {
            super(view);

            view2=view;
            message=view2.findViewById(R.id.cc_header_tv);
            heading=view2.findViewById(R.id.heading);
            updatesNote=view2.findViewById(R.id.note_feeds_header);
        }
    }

    public interface OnPhotoClickedListener {
        void photoClicked(CategoryFeatured categoryUnit, ImageView topImageView,int position);
    }
}