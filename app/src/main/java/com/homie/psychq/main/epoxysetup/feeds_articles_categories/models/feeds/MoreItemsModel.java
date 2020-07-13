package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.MoreItemsHolder;


@EpoxyModelClass(layout = R.layout.more_items_item_layout)
public class MoreItemsModel extends EpoxyModelWithHolder<MoreItemsHolder> {

    @EpoxyAttribute CharSequence title;
    @EpoxyAttribute CharSequence image;
    @EpoxyAttribute View.OnClickListener clickListener;
    @EpoxyAttribute int list_size;
    @EpoxyAttribute String post_id;
    @EpoxyAttribute String category;
    @EpoxyAttribute String sub_category;
//    @EpoxyAttribute String description;

    @Override
    public void unbind(@NonNull MoreItemsHolder holder) {

    }

    @Override
    public void bind(@NonNull MoreItemsHolder holder) {


        int widthTemp=holder.view.getContext().getResources().getDisplayMetrics().widthPixels;
        int width=widthTemp/2;
        holder.imageView.requestLayout();
        holder.imageView.getLayoutParams().width=width;
        holder.imageView.getLayoutParams().height=900;
        holder.title_tv.setText(title);


        //here we have to make changes i-e disbale hardware rendering (gives crash on s10)
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.format(DecodeFormat.PREFER_ARGB_8888);
        requestOptions.disallowHardwareConfig();

        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

//        Glide.with(holder.view.getContext())
//                .applyDefaultRequestOptions(requestOptions)
//                .load(image)
//                .into(holder.imageView);

        Glide.with(holder.view.getContext())
//                .applyDefaultRequestOptions(requestOptions)
                .load(image)
                .error(R.drawable.ic_pitcher)
                .thumbnail(0.1f)
                .transition(DrawableTransitionOptions.withCrossFade())
//                .transform(new RoundedCornersTransformation(radius, margin))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);


        holder.imageView.setOnClickListener(clickListener);
        holder.category_tv.setText(category);
//        holder.description_tv.setText(description);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            for(int i=0;i<list_size;i++){
//                holder.imageView.setTransitionName("shared_element" +i);
//            }
//        }
    }

    @Override
    protected MoreItemsHolder createNewHolder() {
        return new MoreItemsHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.more_items_item_layout;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

}
