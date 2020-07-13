package com.homie.psychq.main.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.homie.psychq.R;
import com.homie.psychq.main.models.crashcourses.CrashCourseUnit;
import com.homie.psychq.utils.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


/*Adapter For Displaying Crash Courses*/
public class CCFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = "PhotosAdapter";


    public static final int TYPE_CC = 101;
    public static final int TYPE_HEADER = 102;

    private List<CrashCourseUnit> cc_list;
    private Context mContext;
    private OnCCClickedListener mListener;
    private SharedPreferences sharedPreferences;


    @Inject
    public CCFragmentAdapter(Context context, OnCCClickedListener listener) {
        Log.d(TAG, "PhotosAdapter: Injected Successfully");
        cc_list = new ArrayList<>();
        mContext = context;
        mListener=listener;

        sharedPreferences = new SharedPreferences(context);
    }


    @Override
    public int getItemViewType(int position) {

        //it works when we set header position to 1, but not with 0
        Log.d(TAG, "getItemViewType: "+position);
        Log.d(TAG, "getItemViewType: "+cc_list.size());
        if(position == 0){
            return TYPE_HEADER;
        }
        return TYPE_CC;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        
        switch (viewType){
            case TYPE_CC:
                
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cc_item_layout3, parent, false);
                holder= new CCHolder(view);
                
                break;
                
                
            case TYPE_HEADER:

                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_crash_category, parent, false);
                holder =  new HeaderHolder(view);
                
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }

       
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()){
            case TYPE_CC:
                
                CCHolder holder1 = (CCHolder) holder;
                setCCData(holder1, cc_list.get(position), holder);

                break;


            case TYPE_HEADER:
                //header Holder
                HeaderHolder holder2 = (HeaderHolder) holder;
                setHeaderData(holder2);

                break;
        }
     
    }

    private void setHeaderData(HeaderHolder holder2) {
        holder2.heading.setText(sharedPreferences.getString(mContext.getString(R.string.CrashCourseHeading),"ABOUT"));
        holder2.message.setText(sharedPreferences.getString(mContext.getString(R.string.CrashCourseMessage),mContext.getString(R.string.crashcourses_general_description)));


        if(!sharedPreferences.getBooleanPref(mContext.getString(R.string.isSubscribed),false)){
            holder2.updatesNote.setVisibility(View.VISIBLE);
        }else {
            holder2.updatesNote.setVisibility(View.GONE);
        }
    }

    private void setCCData(CCHolder holder1, CrashCourseUnit ccUnit, RecyclerView.ViewHolder holder) {
        holder1.titles_tv.setText(ccUnit.getTitle());

        holder1.source_info.setText(ccUnit.getDescription());


        /*RequestOptions for authors image {small size}*/
        RequestOptions requestOptionsSmall = new RequestOptions();
        requestOptionsSmall.format(DecodeFormat.PREFER_RGB_565);
        requestOptionsSmall.override(130,130);
        requestOptionsSmall.transform(new CenterCrop(),new RoundedCorners(10));

        /*RequestOptions for CrashCourse background image {medium size}*/
        RequestOptions requestOptionsLarge = new RequestOptions();
        requestOptionsLarge.format(DecodeFormat.PREFER_RGB_565);
        requestOptionsLarge.override(400,250);

        Glide.with(mContext).load(ccUnit.getPic_top())
                .apply(requestOptionsLarge)
                .transition(withCrossFade())
                .into(holder1.top_image);
        Glide.with(mContext).load(ccUnit.getAuthor_image())
                .apply(requestOptionsSmall)
                .transition(withCrossFade())
                .into(holder1.authour_image);

        holder1.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.ccClicked(ccUnit,holder1.top_image,holder.getAdapterPosition());
            }
        });
    }

    public void addPhotos(List<CrashCourseUnit> photos){
        int lastCount = getItemCount();
        cc_list.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    @Override
    public int getItemCount() {
        return cc_list.size();
    }


    public class CCHolder extends RecyclerView.ViewHolder {

        View view2;
        public ImageView top_image,authour_image;
        public TextView titles_tv, source_info;
//        public TextView pages_count_tv;
//        public TextView description;
        public CCHolder(View view) {
            super(view);

            view2=view;
            top_image=view2.findViewById(R.id.cc_iv);
            titles_tv=view2.findViewById(R.id.title_cc);
            authour_image=view2.findViewById(R.id.author_image);
            source_info =view2.findViewById(R.id.authorinfo_cc);

        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        View view2;
        public TextView heading,message,updatesNote;
        //        public TextView pages_count_tv;
//        public TextView description;
        public HeaderHolder(View view) {
            super(view);

            view2=view;
            message=view2.findViewById(R.id.cc_header_tv);
            heading=view2.findViewById(R.id.heading);
            updatesNote=view2.findViewById(R.id.note_feeds_header);
        }
    }

    public interface OnCCClickedListener {
        void ccClicked(CrashCourseUnit ccUnit, ImageView topImageView, int position);
    }
}