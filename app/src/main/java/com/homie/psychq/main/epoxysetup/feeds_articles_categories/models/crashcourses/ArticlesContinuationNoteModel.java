package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.crashcourses;

import androidx.annotation.NonNull;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.crashcourses.ArticlesContinuationHolder;

/*Sets data for continuation model*/

@EpoxyModelClass(layout = R.layout.articles_continuation_note_layout)
public class ArticlesContinuationNoteModel extends EpoxyModelWithHolder<ArticlesContinuationHolder> {

    //@EpoxyAttribute CharSequence catTitle;
    //@EpoxyAttribute CharSequence catImage;
    @EpoxyAttribute CharSequence name;

    @Override
    public void unbind(@NonNull ArticlesContinuationHolder holder) {

    }

    @Override
    public void bind(@NonNull ArticlesContinuationHolder holder) {


//        holder.textView.setText(name);



    }


    @Override
    protected ArticlesContinuationHolder createNewHolder() {
        return new ArticlesContinuationHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.articles_continuation_note_layout;
    }
}
