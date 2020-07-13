package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.PageNumHolder;



@EpoxyModelClass(layout = R.layout.page_num_layout2)
public class PageNumModel extends EpoxyModelWithHolder<PageNumHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute int pageNumber;
    @EpoxyAttribute int totalPages;

    @Override
    public void unbind(@NonNull PageNumHolder holder) {

    }

    @Override
    public void bind(@NonNull PageNumHolder holder) {


        String pageNum = String.valueOf(pageNumber) + "/"+String.valueOf(totalPages);
        holder.textView.setText(pageNum);

    }


    @Override
    protected PageNumHolder createNewHolder() {
        return new PageNumHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.page_num_layout2;
    }
}
