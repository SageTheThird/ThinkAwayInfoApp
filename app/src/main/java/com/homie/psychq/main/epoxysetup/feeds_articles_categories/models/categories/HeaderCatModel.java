package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.categories;

import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.categories.HeaderCatHolder;


@EpoxyModelClass(layout = R.layout.header_cat_layout)
public class HeaderCatModel extends EpoxyModelWithHolder<HeaderCatHolder> {

    @EpoxyAttribute View.OnClickListener clickListener;
    @EpoxyAttribute String category_title;
    @EpoxyAttribute int posts_count;
    @EpoxyAttribute String cat_description;
    @EpoxyAttribute String about;

    @Override
    public void unbind(@NonNull HeaderCatHolder holder) {

    }

    @Override
    public void bind(@NonNull HeaderCatHolder holder) {




        holder.cat_description.setText(cat_description);
        holder.cat_title.setText(category_title);

        String posts__ = posts_count + " Posts";
        holder.posts_count.setText(posts__);
        holder.about.setText(about);



//        int widthTemp=holder.view.getContext().getResources().getDisplayMetrics().widthPixels;
//        int width=widthTemp/2;
//        holder.imageView.requestLayout();
//        holder.imageView.getLayoutParams().width=width;
//        holder.imageView.getLayoutParams().height=900;
//        holder.textView.setText(title);
//        holder.cateogry.setText(category);
//        Glide.with(holder.view.getContext()).load(image).into(holder.imageView);
//        holder.imageView.setOnClickListener(clickListener);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            for(int i=0;i<list_size;i++){
//                holder.imageView.setTransitionName("shared_element" +i);
//            }
//        }
    }



    @Override
    protected HeaderCatHolder createNewHolder() {
        return new HeaderCatHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.header_cat_layout;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

}
