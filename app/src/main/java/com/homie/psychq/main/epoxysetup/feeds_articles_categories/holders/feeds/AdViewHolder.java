package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.google.android.gms.ads.AdView;
import com.homie.psychq.R;

public class AdViewHolder extends EpoxyHolder {

    public AdView adView;
    public View view;

    @Override
    protected void bindView(@NonNull View itemView) {
        view=itemView;
        adView=view.findViewById(R.id.adView_);
        //adView.setAdSize(AdS);
    }
}
