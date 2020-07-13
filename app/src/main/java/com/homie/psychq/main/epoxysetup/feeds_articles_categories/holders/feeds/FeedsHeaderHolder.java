package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;


import android.view.View;

import androidx.annotation.NonNull;
import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;
import com.homie.psychq.utils.FeedsViewPager;
import com.rd.PageIndicatorView;

public class FeedsHeaderHolder extends EpoxyHolder {

    public View view;
//    public TextView message,heading,content_updates_note_tv;


    public FeedsViewPager feedsViewPager;
    public PageIndicatorView pageIndicatorView;

    @Override
    protected void bindView(@NonNull View itemView) {

        view=itemView;
        feedsViewPager = view.findViewById(R.id.feeds_ViewPager);
        pageIndicatorView = view.findViewById(R.id.pageIndicatorView_fpool);

    }
}
