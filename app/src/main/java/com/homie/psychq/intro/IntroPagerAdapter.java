package com.homie.psychq.intro;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.homie.psychq.R;


import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class IntroPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
    private static final String TAG = "UltraPagerAdapter";

    private List<IntroModel> items_list;
    private Context context;



    public IntroPagerAdapter(Context context, List<IntroModel> items_list) {
        this.context=context;
        this.items_list = items_list;


    }

   @Override
    public int getCount() {

        return items_list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {


        LayoutInflater mInflater = LayoutInflater.from(context);
        View item_view = mInflater.inflate(R.layout.intro_item_pager, container, false);

        final ImageView vector = item_view.findViewById(R.id.vector_intro);
        final TextView description_Tv = item_view.findViewById(R.id.description_introTv);
        final TextView titleTv = item_view.findViewById(R.id.title_intoTv);

        //main method to set all data
        setData(items_list.get(position).getVector(), vector,
                items_list.get(position).getTitle(), titleTv,
                items_list.get(position).getDescription(), description_Tv
        );



        container.addView(item_view);
        return item_view;
    }

    /**
     * Sets Data for every page
     * @param vector
     * @param iv
     * @param title
     * @param titleTv
     * @param description
     * @param descTv
     */
    private void setData(int vector, ImageView iv,
                         String title, TextView titleTv,
                         String description, TextView descTv) {
        Glide.with(context).load(vector)
                .transition(withCrossFade())
                .into(iv);

        titleTv.setText(title);
        descTv.setText(description);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);

    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container,position, object);

    }

    public void addItems(List<IntroModel> list){
        if(list != null){
            items_list.addAll(list);
            notifyDataSetChanged();
        }

    }


}
