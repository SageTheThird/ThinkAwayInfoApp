package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;

import com.homie.psychq.R;
import com.homie.psychq.utils.TypeWriter;
import com.victor.loading.rotate.RotateLoading;

public class LoadingHolder extends EpoxyHolder {

    public View view;
    public RotateLoading progressBar;
    public TypeWriter typewriterTv;

    @Override
    protected void bindView(@NonNull View itemView) {

        view=itemView;
        progressBar=view.findViewById(R.id.rotateloading);
        typewriterTv=view.findViewById(R.id.loadingTv);

    }
}
