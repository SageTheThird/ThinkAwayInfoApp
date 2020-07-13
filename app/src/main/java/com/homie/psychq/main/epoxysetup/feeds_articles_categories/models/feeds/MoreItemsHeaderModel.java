package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.MoreItemsHeaderHolder;



@EpoxyModelClass(layout = R.layout.more_items_header_layout)
public class MoreItemsHeaderModel extends EpoxyModelWithHolder<MoreItemsHeaderHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute CharSequence name;
    @EpoxyAttribute int textSize;

    @Override
    public void unbind(@NonNull MoreItemsHeaderHolder holder) {

    }

    @Override
    public void bind(@NonNull MoreItemsHeaderHolder holder) {


        holder.textView.setText(name);
        holder.textView.setTextSize(textSize);


    }


    @Override
    protected MoreItemsHeaderHolder createNewHolder() {
        return new MoreItemsHeaderHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.more_items_header_layout;
    }
}
