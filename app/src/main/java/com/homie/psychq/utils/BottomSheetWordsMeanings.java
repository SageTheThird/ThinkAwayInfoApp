package com.homie.psychq.utils;

import android.content.Context;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.homie.psychq.R;
import com.homie.psychq.main.api.wordsapi.models.WordResults;



/*
* BottomSheet for built-in dictionary for articles
* Triggered when a word is copied to clipboard
* */

public class BottomSheetWordsMeanings extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheetWordsMeaning";
    private int resourceLay;
    private TextView word, pos1, pos2, pos3,def1,def2,def3;
    private Context context;
    private WordResults wordResults;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout linearLayout;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(resourceLay,container,false);

        word =view.findViewById(R.id.word);
        pos1 =view.findViewById(R.id.pos_tv1);
        pos2 =view.findViewById(R.id.pos_tv2);
        pos3 =view.findViewById(R.id.pos_tv3);
        def1=view.findViewById(R.id.def1);
        def2=view.findViewById(R.id.def2);
        def3=view.findViewById(R.id.def3);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_wordsMeanigns_frameLayout);
        linearLayout=view.findViewById(R.id.bottom_sheet_relativeLayoutW);


        shimmerFrameLayout.startShimmer();



//        title.setTypeface(customTypeFaces.getCoutureTypeFace());


        if(wordResults != null && wordResults.getDefinitions().size() > 0){

            TransitionManager.beginDelayedTransition(linearLayout);

            int definitionsListSize = wordResults.getDefinitions().size();

            if(definitionsListSize > 3){
                definitionsListSize = 3;
            }


            stopShimmer();

            word.setVisibility(View.VISIBLE);

            word.setText(wordResults.getWord());


            if(definitionsListSize == 1){

                pos1.setVisibility(View.VISIBLE);
                def1.setVisibility(View.VISIBLE);





                pos1.setText( wordResults.getDefinitions().get(0).getPartOfSpeech());
                def1.setText( wordResults.getDefinitions().get(0).getDefinition());

            }
            if(definitionsListSize == 2){

                pos1.setVisibility(View.VISIBLE);
                def1.setVisibility(View.VISIBLE);
                pos2.setVisibility(View.VISIBLE);
                def2.setVisibility(View.VISIBLE);




                pos1.setText( wordResults.getDefinitions().get(0).getPartOfSpeech());
                def1.setText( wordResults.getDefinitions().get(0).getDefinition());

                pos2.setText( wordResults.getDefinitions().get(1).getPartOfSpeech());
                def2.setText( wordResults.getDefinitions().get(1).getDefinition());

            }

            if(definitionsListSize == 3){

                pos1.setVisibility(View.VISIBLE);
                def1.setVisibility(View.VISIBLE);
                pos2.setVisibility(View.VISIBLE);
                def2.setVisibility(View.VISIBLE);
                pos3.setVisibility(View.VISIBLE);
                def3.setVisibility(View.VISIBLE);








                pos1.setText( wordResults.getDefinitions().get(0).getPartOfSpeech());
                def1.setText( wordResults.getDefinitions().get(0).getDefinition());

                pos2.setText( wordResults.getDefinitions().get(1).getPartOfSpeech());
                def2.setText( wordResults.getDefinitions().get(1).getDefinition());

                pos3.setText( wordResults.getDefinitions().get(2).getPartOfSpeech());
                def3.setText( wordResults.getDefinitions().get(2).getDefinition());

            }
        }else {
            stopShimmer();
            word.setVisibility(View.VISIBLE);
            word.setText("Couldn't Find Any Definition For The Word");
        }

        return view;
    }



    private void stopShimmer() {
        if(shimmerFrameLayout != null){
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    public void setData(WordResults wordResults){
        this.wordResults=wordResults;
    }


    public void setLayout(int resourceLayout){
        this.resourceLay=resourceLayout;
    }

    public void provideContext(Context context){
        this.context=context;
    }


}
