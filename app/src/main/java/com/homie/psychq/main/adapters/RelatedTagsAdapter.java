package com.homie.psychq.main.adapters;


import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.homie.psychq.R;
import com.homie.psychq.utils.ColorsHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class RelatedTagsAdapter extends RecyclerView.Adapter<RelatedTagsAdapter.TagHolder> {


    private static final String TAG = "CategoriesAdapterPsych";
    public static final int TYPE_CATEGORY = 101;
    public static final int TYPE_HEADER = 102;

    private List<String> tags_list;
    private Context mContext;
    private OnTagClickedListener mListener;



    @Inject
    public RelatedTagsAdapter(Context context, OnTagClickedListener listener) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        tags_list =new ArrayList<>();
        mContext = context;
        mListener=listener;

    }


    @Override
    public RelatedTagsAdapter.TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RelatedTagsAdapter.TagHolder holder = null;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tag_holder_layout, parent, false);
        holder = new TagHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(final RelatedTagsAdapter.TagHolder holder, final int position) {


        holder.tag_tv.getBackground().setColorFilter(ColorsHelper.getRandomColor(), PorterDuff.Mode.SRC_ATOP);
        holder.tag_tv.setText(tags_list.get(position));

        holder.tag_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTagClicked(tags_list.get(position),holder.tag_tv,position);
            }
        });


    }


    public void addTags(List<String> tags){
        int lastCount = getItemCount();
        if(tags_list.size() > 0){
            tags_list = new ArrayList<>();
        }
        tags_list.addAll(tags);
        notifyDataSetChanged();
    }

    public void clearList(){
        if(tags_list !=null){
            tags_list.clear();
        }
    }

    @Override
    public int getItemCount() {
        return tags_list.size();
    }

    public class TagHolder extends RecyclerView.ViewHolder {

        View view2;

        public TextView tag_tv;

        public TagHolder(View view) {
            super(view);

            view2=view;
            tag_tv=view.findViewById(R.id.single_categories_holder_tv);
        }
    }



    public interface OnTagClickedListener {
        void onTagClicked(String tag, TextView tagTv, int position);
    }
}