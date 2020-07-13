package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import android.view.View;
import androidx.annotation.NonNull;
import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.SubscriptionHolder;

@EpoxyModelClass(layout = R.layout.subscription_holder_layout)
public class SubscriptionModel extends EpoxyModelWithHolder<SubscriptionHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute
    View.OnClickListener SubscribeClickLIstener;

    @Override
    public void unbind(@NonNull SubscriptionHolder holder) {

    }

    @Override
    public void bind(@NonNull SubscriptionHolder holder) {

        holder.subscribeBtn.setOnClickListener(SubscribeClickLIstener);

    }


    @Override
    protected SubscriptionHolder createNewHolder() {
        return new SubscriptionHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.subscription_holder_layout;
    }
}
