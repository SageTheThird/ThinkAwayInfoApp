package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.ModelView.Size;

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
public class LinearSingleTagModel extends Carousel {
    private static final int SPAN_COUNT = 2;

    public LinearSingleTagModel(Context context) {
        super(context);
    }

    @androidx.annotation.NonNull
    @Override
    protected LayoutManager createLayoutManager() {
//        return new GridLayoutManager(getContext(), SPAN_COUNT, RecyclerView.HORIZONTAL, false);
        return new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL);

    }


}