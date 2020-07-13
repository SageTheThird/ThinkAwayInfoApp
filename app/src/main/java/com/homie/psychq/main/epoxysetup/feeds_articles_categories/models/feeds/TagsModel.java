package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import android.graphics.PorterDuff;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.TagsHolder;
import com.homie.psychq.utils.ColorsHelper;


@EpoxyModelClass(layout = R.layout.single_tag_holder_layout)
public class TagsModel extends EpoxyModelWithHolder<TagsHolder> {


    @EpoxyAttribute CharSequence tag;


    @EpoxyAttribute
    View.OnClickListener clickListener;

    @Override
    public void unbind(@NonNull TagsHolder holder) {

    }

    @Override
    public void bind(@NonNull TagsHolder holder) {
        String tag_ = ""+tag;
        holder.tag.setText(tag_);
        holder.tag.getBackground().setColorFilter(ColorsHelper.getRandomColor(), PorterDuff.Mode.SRC_ATOP);

        holder.tag.setOnClickListener(clickListener);


    }

    @Override
    protected TagsHolder createNewHolder() {
        return new TagsHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.single_tag_holder_layout;
    }


    @Override
    public boolean shouldSaveViewState() {
        return true;
    }
}
