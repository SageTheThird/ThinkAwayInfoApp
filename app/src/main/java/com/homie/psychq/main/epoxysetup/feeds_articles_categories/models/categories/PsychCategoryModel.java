package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.categories;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.categories.PsychCategoryHolder;
import com.homie.psychq.main.models.categories.CategoryFeatured;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

@EpoxyModelClass(layout = R.layout.nested_categories_item_layout)
public class PsychCategoryModel extends EpoxyModelWithHolder<PsychCategoryHolder> {

    @EpoxyAttribute
    CategoryFeatured category;
    @EpoxyAttribute View.OnClickListener clickListener;
    @Override
    public void unbind(@NonNull PsychCategoryHolder holder) {

    }

    @Override
    public void bind(@NonNull PsychCategoryHolder holder) {
        holder.titleTv.setText(category.getTitle());
        holder.descriptionTv.setText(category.getDescription());

        RequestOptions requestOptionsSmall = new RequestOptions();
        requestOptionsSmall.format(DecodeFormat.PREFER_RGB_565);
        requestOptionsSmall.override(130,130);
        requestOptionsSmall.transform(new CenterCrop(),new RoundedCorners(10));

        RequestOptions requestOptionsLarge = new RequestOptions();
        requestOptionsLarge.format(DecodeFormat.PREFER_RGB_565);
        requestOptionsLarge.override(240,130);


        Glide.with(BaseApplication.get()).load(category.getPic_right())
                .error(R.drawable.ic_pitcher)
                .apply(requestOptionsLarge)
                .transition(withCrossFade())
                .into(holder.largeImage);
        Glide.with(BaseApplication.get()).load(category.getPic_top())
                .error(R.drawable.ic_pitcher)
                .apply(requestOptionsSmall)
                .transition(withCrossFade())
                .into(holder.smallImage);

        holder.openBtn.setOnClickListener(clickListener);

    }

    @Override
    protected PsychCategoryHolder createNewHolder() {
        return new PsychCategoryHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.nested_categories_item_layout;
    }


    @Override
    public boolean shouldSaveViewState() {
        return true;
    }
}
