package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.LoadingHolder;
import com.homie.psychq.utils.QuotesLoadingHelper;
import com.homie.psychq.utils.SharedPreferences;


@EpoxyModelClass(layout = R.layout.loading_layout)
public class LoadingModel extends EpoxyModelWithHolder<LoadingHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    @EpoxyAttribute
    SharedPreferences sharedPreferences;




    @Override
    public void unbind(@NonNull LoadingHolder holder) {

    }

    @Override
    public void bind(@NonNull LoadingHolder holder) {

//        holder.progressBar.setVisibility(View.VISIBLE);
        String loadingText = QuotesLoadingHelper.getQuoteForInt(QuotesLoadingHelper.getSequentialIndexForQuote(sharedPreferences));
        holder.typewriterTv.setCharacterDelay(10);
        holder.typewriterTv.animateText(loadingText);
        holder.progressBar.start();

    }


    @Override
    protected LoadingHolder createNewHolder() {
        return new LoadingHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.loading_layout;
    }
}

