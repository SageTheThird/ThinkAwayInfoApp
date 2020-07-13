package com.homie.psychq.main.epoxysetup.feeds_articles_categories.models.feeds;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.homie.psychq.R;
import com.homie.psychq.main.epoxysetup.feeds_articles_categories.holders.feeds.FeedsPostHolder;
import com.homie.psychq.main.models.feeds.PsychPhoto;
import com.homie.psychq.room2.DatabaseTransactions;
import com.homie.psychq.room2.PostReadEntity;
import com.homie.psychq.utils.AppExecutors;
import com.homie.psychq.utils.CategoryDrawableHelper;
import com.homie.psychq.utils.ColorsHelper;
import com.homie.psychq.utils.TimeStampHelper;
import java.util.List;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


@EpoxyModelClass(layout = R.layout.featured_psych_item_layout8)//6 is working for now
public class FeedsPostModel extends EpoxyModelWithHolder<FeedsPostHolder> {

    private static final String TAG = "FeedsPostModel";


    @EpoxyAttribute
    PsychPhoto post;

    @EpoxyAttribute View.OnClickListener clickListener;
    @EpoxyAttribute View.OnClickListener vectorCircularIvClickListener;
    @EpoxyAttribute int list_size;
    @EpoxyAttribute DatabaseTransactions databaseTransactions;




    @EpoxyAttribute RequestOptions requestOptions;

//    @EpoxyAttribute String description;

    @Override
    public void unbind(@NonNull FeedsPostHolder holder) {

    }

    @Override
    public void bind(@NonNull FeedsPostHolder holder) {


//        int widthTemp=holder.view.getContext().getResources().getDisplayMetrics().widthPixels;
//        int width=widthTemp/2;
//        holder.imageView.requestLayout();
//        holder.imageView.getLayoutParams().width=width;
//        holder.imageView.getLayoutParams().height=1000;

//        holder.rootConstraintLayout.setBackground(ContextCompat.getDrawable(BaseApplication.get(), R.drawable.quotes_border_background));

//        ViewCompat.setBackground(holder.rootConstraintLayout,ContextCompat.getDrawable(BaseApplication.get(), R.drawable.quotes_border_background));

//        holder.rootConstraintLayout.setBackgroundResource(R.drawable.quotes_border_background);

        String categoryWOSpaces = post.getCategory().replace(" ","");

        holder.rootConstraintLayout.getBackground().setColorFilter(ColorsHelper.getCategoryColor(categoryWOSpaces), PorterDuff.Mode.SRC_ATOP);



        checkIfPostIsReadOrNot(post.getId(),holder.titleOnThumb);

        holder.vectorCircularIv.setImageResource(CategoryDrawableHelper.getDrawableForCategory(categoryWOSpaces));

        holder.vectorCircularIv.setBorderColor(ColorsHelper.getCategoryColor(categoryWOSpaces));



        holder.vectorCircularIv.setOnClickListener(vectorCircularIvClickListener);
        //When vectorIv is clicked it will lead to the category

        holder.titleOnThumb.setText(post.getTitle());



//        String description__ = post.getDescription();
//
//        if(post.getDescription() == null || post.getDescription().equals("")  ){
//            description__ = "No description needed, since main content of the post are described inside the post.";
//        }

//        holder.descriptionTv.setText(description__);



        holder.category_tv.setText(post.getCategory());

        //here we have to make changes i-e disbale hardware rendering (gives crash on s10)
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.format(DecodeFormat.PREFER_RGB_565);
        requestOptions.override(410,410);



        try {

            Glide.with(holder.view.getContext())
                    .applyDefaultRequestOptions(requestOptions)
                    .load(post.getThumbnail())
                    .error(R.drawable.ic_pitcher)
                            //                .transform(new CenterCrop(),new RoundedCorners(20))
//                .transform(new CenterCrop(), new RoundedCorners(20))
                    .disallowHardwareConfig()

//                .thumbnail(0.1f)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .transform(new RoundedCornersTransformation(radius, margin))
                    .transition(withCrossFade())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);

                            return false;
                        }
                    })

                    .into(holder.imageView);

        }catch (OutOfMemoryError e){
            Toast.makeText(holder.view.getContext(), "Out Of Memory", Toast.LENGTH_LONG).show();
        }




        holder.view.setOnClickListener(clickListener);

        if(TimeStampHelper.getDaysAgoInt(post.getTimeCreated()) < 4){
            //content is 3 days old, show new stamp otherwise hide stamp
            holder.freshStampIv.setVisibility(View.VISIBLE);
        }else {
            holder.freshStampIv.setVisibility(View.GONE);
        }
        holder.timestamp.setText(TimeStampHelper.getTimeDifference(post.getTimeCreated()));




    }




    public void slideUp(final TextView view){

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight()/4,  // fromYDelta
                - view.getHeight());                // toYDelta
        animate.setDuration(250);

        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animate);

    }

    private void checkIfPostIsReadOrNot(String post_id, TextView titleTv) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                if(databaseTransactions != null){

                    Log.d(TAG, "run: Database Transactions : "+databaseTransactions);
                    List<PostReadEntity> postReadEntityList = databaseTransactions.isPostAlreadyRead(post_id);

                    if(postReadEntityList.size() > 0){
                        //id is already entered
                        changeBgToRead(titleTv);

                    }else {
                        //here we enter the id to db
                        Log.d(TAG, "run: Post Is Not Read Before, Entering Into DB");

                        changeBgToUnRead(titleTv);

                    }
                }

            }
        });
    }


    private void changeBgToRead(TextView titleTv){
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {

                titleTv.setBackground(titleTv.getContext().getResources().getDrawable(R.drawable.title_read_background_feeds));
                titleTv.getBackground().setColorFilter(ColorsHelper.getCategoryColor(post.getCategory().replace(" ","")), PorterDuff.Mode.SRC_ATOP);

            }
        });


    }

    private void changeBgToUnRead(TextView titleTv){
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {

                titleTv.setBackground(titleTv.getContext().getResources().getDrawable(R.drawable.title_unread_background_feeds2));
                titleTv.getBackground().setColorFilter(ColorsHelper.getCategoryColor(post.getCategory().replace(" ","")), PorterDuff.Mode.SRC_ATOP);

            }
        });
    }

    @Override
    protected FeedsPostHolder createNewHolder() {
        return new FeedsPostHolder();
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.featured_psych_item_layout8;
    }

    @Override
    public boolean shouldSaveViewState() {
        return true;
    }

}
