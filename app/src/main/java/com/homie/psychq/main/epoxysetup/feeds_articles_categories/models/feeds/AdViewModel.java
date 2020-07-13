package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;


import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.AdViewHolder;


/*Handles loading and managing ad model*/
@EpoxyModelClass(layout = R.layout.psych_featured_adview)
public class AdViewModel extends EpoxyModelWithHolder<AdViewHolder> {


    @EpoxyAttribute
    AdRequest adRequest;
    @EpoxyAttribute
    AdListener adListener;
    @Override
    public void unbind(@NonNull AdViewHolder holder) {

    }

    @Override
    public void bind(@NonNull AdViewHolder holder) {
        holder.adView.loadAd(adRequest);
        holder.adView.setAdListener(adListener);
    }

    @Override
    protected AdViewHolder createNewHolder() {
        return new AdViewHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.psych_featured_adview;
    }
}
