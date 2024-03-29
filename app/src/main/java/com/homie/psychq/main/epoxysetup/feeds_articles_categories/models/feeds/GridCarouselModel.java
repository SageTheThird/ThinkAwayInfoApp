package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;
import android.content.Context;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.airbnb.epoxy.ModelView.Size;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
public class GridCarouselModel extends Carousel {
    private static final int SPAN_COUNT = 1;

    public GridCarouselModel(Context context) {
        super(context);
    }

    @androidx.annotation.NonNull
    @Override
    protected LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), SPAN_COUNT, LinearLayoutManager.HORIZONTAL, false);

    }
}