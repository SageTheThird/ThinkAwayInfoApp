package com.homie.psychq.main.ui.factspool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.homie.psychq.R;

import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.factspool.FactsPoolApi;
import com.homie.psychq.main.models.factspool.NumberFact;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/*Fragment for number facts*/

public class NumberFragment extends Fragment {
    private static final String TAG = "NumberFragment";



    private TextView fact_tv;
    private EditText num_et;
    private Button enterBtn;
    private ProgressBar progressBar;
    private Button incrementBtn,decrementBtn;
    private int count;
    PsychComponent component;
    FactsPoolApi factsPoolApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_numbers,container,false);


        initUiComponents(view);
        initComponent();

        //default request
        num_et.setText("911");
        fetchFact(911,fact_tv,enterBtn,progressBar);


        enterBtn.setOnClickListener(confirmBtnCL);
        incrementBtn.setOnClickListener(incrementBtnCL);
        decrementBtn.setOnClickListener(decrementBtnCL);


        return view;
    }

    private void initUiComponents(View view) {
        fact_tv=view.findViewById(R.id.fact_tv_num);
        num_et=view.findViewById(R.id.year_edt);
        enterBtn=view.findViewById(R.id.go_btn);
        progressBar=view.findViewById(R.id.progressBar);
        incrementBtn=view.findViewById(R.id.increment_btn_year);
        decrementBtn=view.findViewById(R.id.decrement_year);
    }

    private void initComponent() {

        component= DaggerPsychComponent.builder()
                .build();

        factsPoolApi=component.provideFactsApi();
    }

    private void hideBtnShowProgressBar(Button button, ProgressBar progressBar){
        button.setAlpha(.5f);
        button.setClickable(false);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void showBtnHideProgressBar(Button button,ProgressBar progressBar){
        button.setAlpha(1);
        button.setClickable(true);
        progressBar.setVisibility(View.INVISIBLE);
    }


    /* Once input is confirmed, requests fact from API, and sets data on UI*/
    private void fetchFact(int input, final TextView set_tv, final Button showBtn , final ProgressBar progressBar) {

        if(isNetworkAvailable()){
            factsPoolApi.getNumbersFact(input).enqueue(new Callback<NumberFact>() {
                @Override
                public void onResponse(Call<NumberFact> call, Response<NumberFact> response) {

                    if(response.body() != null){
                        set_tv.setText(response.body().getText());
                        showBtnHideProgressBar(showBtn,progressBar);
                    }



                }

                @Override
                public void onFailure(Call<NumberFact> call, Throwable t) {

                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });
        }else {
            showBtnHideProgressBar(enterBtn,progressBar);
            fact_tv.setText("Please Check Your Internet Connection");

        }


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    //-------------------------------
    /*Click Listeners*/
    //-------------------------------

    View.OnClickListener incrementBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(TextUtils.isEmpty(num_et.getText().toString())){
                count = 0;
                num_et.setText(String.valueOf(count));
            }else {

                count = Integer.parseInt(num_et.getText().toString());

                count += 1;
                num_et.setText(String.valueOf(count));
            }
        }
    };
    View.OnClickListener decrementBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(TextUtils.isEmpty(num_et.getText().toString())){
                count = 0;
                num_et.setText(String.valueOf(count));
            }else {
                if(count > 0){

                    count = Integer.parseInt(num_et.getText().toString());

                    count -= 1;
                    num_et.setText(String.valueOf(count));
                }

            }
        }
    };
    View.OnClickListener confirmBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String input_number=num_et.getText().toString();

            int number=1;
            if (!input_number.equals("")){
                number=Integer.parseInt(input_number);
            }


            if(TextUtils.isEmpty(num_et.getText().toString())){
                Toast.makeText(getActivity(), "Please Select a Number", Toast.LENGTH_LONG).show();
            }else {

                hideBtnShowProgressBar(enterBtn,progressBar);

                fetchFact(number,fact_tv,enterBtn,progressBar);

            }
        }
    };

}
