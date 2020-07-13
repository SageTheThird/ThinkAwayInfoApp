package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsHeaderHolder;
import com.homie.psychq.main.models.feeds.Announcement;
import com.homie.psychq.main.adapters.FeedsPagerAdapter;
import com.homie.psychq.utils.SharedPreferences;
import java.util.List;

@EpoxyModelClass(layout = R.layout.feeds_header_layout)
public class FeedsHeaderModel extends EpoxyModelWithHolder<FeedsHeaderHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute
    List<Announcement> announcements;

    @EpoxyAttribute
    SharedPreferences sharedPreferences;

    @Override
    public void unbind(@NonNull FeedsHeaderHolder holder) {

    }

    @Override
    public void bind(@NonNull FeedsHeaderHolder holder) {



        FeedsPagerAdapter feedsPagerAdapter =
                new FeedsPagerAdapter(holder.view.getContext(),sharedPreferences,announcements);
//        holder.feedsViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);

        holder.feedsViewPager.setAdapter(feedsPagerAdapter);
        holder.pageIndicatorView.setViewPager(holder.feedsViewPager);
//        holder.feedsViewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);



//        CustomTypeFaces typeFaces=new CustomTypeFaces(holder.view.getContext());
//        holder.message.setText(message);
////        holder.message.setTypeface(typeFaces.getTrebuchetNormalTF());
//        holder.heading.setText(heading);
////        holder.heading.setTypeface(typeFaces.getTrebuchetNormalTF());
//
//        if(!sharedPreferences.getBooleanPref(BaseApplication.get().getString(R.string.isSubscribed),false)){
//            holder.content_updates_note_tv.setVisibility(View.VISIBLE);
//        }else {
//            holder.content_updates_note_tv.setVisibility(View.GONE);
//        }
    }


    @Override
    protected FeedsHeaderHolder createNewHolder() {
        return new FeedsHeaderHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.feeds_header_layout;
    }
}
