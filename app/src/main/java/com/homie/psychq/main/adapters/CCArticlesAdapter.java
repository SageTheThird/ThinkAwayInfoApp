package com.homie.psychq.main.adapters;


import android.content.Context;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.homie.psychq.R;
import com.homie.psychq.main.models.crashcourses.Article;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/*Adapter For Articles + Header Section*/
public class CCArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = "PhotosAdapter";
    private static final int ARTICLE = 101;
    private static final int INFO = 102;

    private List<Article> articles_list;
    private Context mContext;
    private OnArticleClickedListener mListener;
    private SharedPreferences sharedPreferences;
    private String courseDescription;


    @Inject
    public CCArticlesAdapter(Context context, OnArticleClickedListener listener, String courseDescription) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        articles_list =new ArrayList<>();
        mContext = context;
        mListener=listener;
        sharedPreferences = new SharedPreferences(context);

        this.courseDescription = courseDescription;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == ARTICLE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_item_layout_test2, parent, false);
            return new ArticleHolder(view);

        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.snippet_about_crashcourse_layout, parent, false);
            return new InfoHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 1){
            return INFO;
        }else {
            return ARTICLE;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

//        String photos_count=String.valueOf(articles_list.get(position).getTotal_posts_count()) + " Photos";


        switch (holder.getItemViewType()){
            case ARTICLE:

                ArticleHolder holder1 = (ArticleHolder) holder;

                setArticleData(holder1,position);

                break;


            case INFO:

                InfoHolder holder2 = (InfoHolder) holder;

                setInfoData(holder2,position);

                break;
        }




//        Glide.with(mContext).load(articles_list.get(position).getPic_right()).into(holder.right_image);
//        Glide.with(mContext).load(articles_list.get(position).getPic_top()).into(holder.top_image);


//        Picasso.with(mContext)
//                .load(articles_list.get(position).getTop_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.top_image);
//
//        Picasso.with(mContext)
//                .load(articles_list.get(position).getLeft_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.left_image);
//        Picasso.with(mContext)
//                .load(articles_list.get(position).getRight_image())
//                .resize(300, 300)
//                .centerCrop()
//                .into(holder.right_image);

    }


    /*Method to set the header data*/
    private void setInfoData(InfoHolder holder2, int position) {





        holder2.description.setText(courseDescription);

        holder2.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder2.seeMore.getText().equals("See More")){
                    TransitionManager.beginDelayedTransition(holder2.linearLayout);
                    holder2.description.requestLayout();
                    holder2.description.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    holder2.seeMore.setText("See Less");
                }else {
                    TransitionManager.beginDelayedTransition(holder2.linearLayout);
                    holder2.description.requestLayout();
                    holder2.description.getLayoutParams().height = 1000;
                    holder2.seeMore.setText("See More");
                }
            }
        });


    }

    /*Method to set the main article data*/
    private void setArticleData(ArticleHolder holder1, int position) {

//        holder1.content_tv.setTypeface(customTypeFaces.getTrebuchetNormalTF());

        holder1.titles_tv.setText(articles_list.get(position).getTitle());
        String number__ = String.valueOf(articles_list.get(position).getCourseCount());
        holder1.number_tv.setText(number__);
//        holder1.content_tv.setText(articles_list.get(position).getArticleContent());


        if(articles_list.get(position).getId()
                .equals(sharedPreferences.getString("pinnedArticleId",null))){

            holder1.pin_iv.setVisibility(View.VISIBLE);

        }else {

            holder1.pin_iv.setVisibility(View.GONE);

        }

        /*
         * From testing override doesnt work with transform for round corners
         * - leave just transform(new CenterCrop, new ROundCorner()) when the images are of optimal sizes i-e
         * just barely fitting in its imageView
         * */
        Glide.with(mContext).load(articles_list.get(position).getThumbnail())
                .override(100,100)
//              .transform(new CenterCrop(),new RoundedCorners(20))
                .into(holder1.thumb);


        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.articleClicked(articles_list.get(position),holder1.content_tv,holder1.getAdapterPosition());

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles_list.size() ;
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {

        View view2;

        CardView layout;
        public TextView number_tv;
        public TextView titles_tv;
        public TextView content_tv;
        public Button see_more_btn;
        public  ImageView thumb;
        public  ImageView pin_iv;
//        public TextView pages_count_tv;
//        public TextView description;
        public ArticleHolder(View view) {
            super(view);

            view2=view;
            layout=view2.findViewById(R.id.article_linear2);
            number_tv=view2.findViewById(R.id.article_number_);
            titles_tv=view2.findViewById(R.id.article_title);
//            content_tv=view2.findViewById(R.id.article_content);
//            see_more_btn=view2.findViewById(R.id.article_seeMore);
            thumb=view2.findViewById(R.id.article_thumb);
            pin_iv=view2.findViewById(R.id.pin_iv);
        }
    }


    /*Header Holder*/
    public class InfoHolder extends RecyclerView.ViewHolder {

        View view2;

        CardView layout;
        public TextView about;
        public TextView description;
        public TextView article;
        public Button seeMore;
        public LinearLayout linearLayout;

        //        public TextView pages_count_tv;
//        public TextView description;
        public InfoHolder(View view) {
            super(view);

            view2=view;
            layout=view2.findViewById(R.id.card_info);
            about=view2.findViewById(R.id.about_ccOnClik);
            description=view2.findViewById(R.id.courseDescription);
            article=view2.findViewById(R.id.article);
            seeMore=view2.findViewById(R.id.seeMoreAboutBtn);
            linearLayout=view2.findViewById(R.id.linear_about);

        }
    }

    public interface OnArticleClickedListener {
        void articleClicked(Article ccUnit, TextView contentTv, int position);
    }
}