package com.homie.psychq.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.homie.psychq.R;


import java.io.Serializable;
import java.util.Objects;

public class MaterialBottomSheet extends BottomSheetDialogFragment {

    private BottomSheetListener mListener;

    public interface BottomSheetListener extends Serializable {
        void onUltraClicked();
        void onHighClicked();
        void onMediumClicked();
        void onLowClicked();
    }


    private int resourceLay;
    private TextView ultra, high, medium, low,title;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(resourceLay,container,false);

        ultra =view.findViewById(R.id.ultra_tv);
        high =view.findViewById(R.id.high_tv);
        medium =view.findViewById(R.id.medium_tv);
        low =view.findViewById(R.id.low_tv);
        title=view.findViewById(R.id.msg);




        try {
            mListener = (BottomSheetListener) Objects.requireNonNull(getArguments()).getSerializable("dialogInterface");
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        ultra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onUltraClicked();
                dismiss();
            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHighClicked();
                dismiss();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onMediumClicked();
                dismiss();
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLowClicked();
                dismiss();
            }
        });
        return view;
    }

    public void setLayout(int resourceLayout){
        this.resourceLay=resourceLayout;
    }

    public void provideContext(Context context){
        this.context=context;
    }

    public static MaterialBottomSheet getInstance(BottomSheetListener dialogInterface, boolean downloadOn) {
        MaterialBottomSheet fragmentDialog = new MaterialBottomSheet();

        Bundle args = new Bundle();
        args.putSerializable("dialogInterface", dialogInterface);
//        args.putParcelable("photo", (Parcelable) photo);

        if(downloadOn){
            args.putString("Action","Download");
        }else {
            args.putString("Action","Set");
        }
        // set fragment arguments

        fragmentDialog.setArguments(args);

        return fragmentDialog;
    }
}
