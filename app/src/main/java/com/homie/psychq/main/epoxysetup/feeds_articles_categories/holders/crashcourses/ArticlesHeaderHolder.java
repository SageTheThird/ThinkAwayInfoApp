package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;

public class ArticlesHeaderHolder extends EpoxyHolder {

    View view2;

    public CardView layout;
    public TextView about;
    public TextView description;
    public TextView article, courseName, author;
    public Button seeMore;
    public LinearLayout linearLayout;
    public ImageView courseThumb;

    @Override
    protected void bindView(@NonNull View itemView) {
        view2=itemView;
        layout=view2.findViewById(R.id.card_info);
        about=view2.findViewById(R.id.about_ccOnClik);
        description=view2.findViewById(R.id.courseDescription);
        article=view2.findViewById(R.id.article);
        seeMore=view2.findViewById(R.id.seeMoreAboutBtn);
        linearLayout=view2.findViewById(R.id.linear_about);
        courseName = view2.findViewById(R.id.courseName);
        author = view2.findViewById(R.id.author);
        courseThumb = view2.findViewById(R.id.iv);
//        description_tv=view.findViewById(R.id.psych_feautred_item_descriptiontv);
    }
}
