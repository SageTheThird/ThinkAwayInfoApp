package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;


import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.HeaderHolder;


@EpoxyModelClass(layout = R.layout.header_layout)
public class HeaderModel extends EpoxyModelWithHolder<HeaderHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute CharSequence name;

    @EpoxyAttribute int textSize;

    @Override
    public void unbind(@NonNull HeaderHolder holder) {

    }

    @Override
    public void bind(@NonNull HeaderHolder holder) {


        holder.textView.setText(name);
        holder.textView.setTextSize(textSize);


    }


    @Override
    protected HeaderHolder createNewHolder() {
        return new HeaderHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.header_layout;
    }
}
