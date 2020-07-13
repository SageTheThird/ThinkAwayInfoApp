package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;


/*
* Main Article Holder
* */
public class ArticleHolder extends EpoxyHolder {

    public View view2;

    public CardView layout;
    public TextView number_tv;
    public TextView titles_tv;
    public TextView content_tv;
//    public Button see_more_btn;
    public  ImageView thumb;
    public  ImageView pin_iv;
    public  ImageView fresh_stamp_iv;

    @Override
    protected void bindView(@NonNull View itemView) {
        view2=itemView;
        layout=view2.findViewById(R.id.article_linear2);
        number_tv=view2.findViewById(R.id.article_number_);
        titles_tv=view2.findViewById(R.id.article_title);
//        content_tv=view2.findViewById(R.id.article_content);
//        see_more_btn=view2.findViewById(R.id.article_seeMore);
        thumb=view2.findViewById(R.id.article_thumb);
        pin_iv=view2.findViewById(R.id.pin_iv);
        fresh_stamp_iv=view2.findViewById(R.id.fresh_stamp_iv_article);
//        description_tv=view.findViewById(R.id.psych_feautred_item_descriptiontv);
    }
}
