package com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyHolder;
import com.homie.psychq.R;


/*
* When user reach the end of articles in a crash course, this will tell them if the course is continued or not
* */
public class ArticlesContinuationHolder extends EpoxyHolder {

    public View view;
    public TextView textView;

    @Override
    protected void bindView(@NonNull View itemView) {

        view=itemView;
        textView=view.findViewById(R.id.note__);

    }
}
