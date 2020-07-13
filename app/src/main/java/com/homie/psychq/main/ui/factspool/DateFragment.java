package com.homie.psychq.main.ui.factspool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.homie.psychq.R;
import com.homie.psychq.main.adapters.SpinnerCustomAdapter;

import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.factspool.FactsPoolApi;
import com.homie.psychq.main.models.factspool.DateFact;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Fragment for date facts*/

public class DateFragment extends Fragment {

    private static final String TAG = "DateFragment";



    private Spinner monthSpinner;
    private SpinnerCustomAdapter monthsSpinnerAdapter;
    private List<Integer> months_list;
    private Spinner daySpinner;
    private List<Integer> days_list;
    private SpinnerCustomAdapter daysSpinnerAdapter;
    private TextView fact_tv;
    private Button enterBtn;
    private ProgressBar progressBar;
    PsychComponent component;
    FactsPoolApi factsPoolApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_date,container,false);

        initUiComponents(view);

        initDataComponent();

        monthSpinnerSetup();
        daySpinnerSetup();


        monthSpinner.setSelection(0);
        daySpinner.setSelection(2);

        //request for month 1 day 3
        fetchDateFact(1,3,fact_tv,enterBtn,progressBar);

        enterBtn.setOnClickListener(confirmBtnCL);


        return view;
    }

    private void initUiComponents(View view) {
        monthSpinner=view.findViewById(R.id.month_spinner);
        daySpinner=view.findViewById(R.id.day_spinner);
        fact_tv=view.findViewById(R.id.fact_tv_date);
        enterBtn=view.findViewById(R.id.go_btn);
        progressBar=view.findViewById(R.id.progressBar);
    }


    /*Sets up spinner for date (input 1)*/
    private void daySpinnerSetup() {

        initDaysList();

        daysSpinnerAdapter=new SpinnerCustomAdapter(getActivity(),days_list);
        daySpinner.setAdapter(daysSpinnerAdapter);



        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    /*Sets up spinner for date (input 2)*/
    private void monthSpinnerSetup() {

        initMonthsList();

        monthsSpinnerAdapter =new SpinnerCustomAdapter(getActivity(),months_list);
        monthSpinner.setAdapter(monthsSpinnerAdapter);


        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


    /*Creates months list of 12 entries to choose from*/
    private void initMonthsList() {
        months_list=new ArrayList<>();
        for(int i=1;i<=12;i++){
            months_list.add(i);
        }
    }

    /*Creates days list of 31 entries to choose from*/
    private void initDaysList() {

        days_list=new ArrayList<>();

        for(int i=1;i<=31;i++){
            days_list.add(i);
        }

    }

    private void initDataComponent() {

        component= DaggerPsychComponent.builder()
                .build();

        factsPoolApi=component.provideFactsApi();
    }


    /* Once input is confirmed, requests fact from API, and sets data on UI*/
    private void fetchDateFact(int month, int day, final TextView set_tv, final Button showBtn , final ProgressBar progressBar) {

        Log.d(TAG, "onClick: month :"+month + " : day "+day);
        if(isNetworkAvailable()){
            factsPoolApi.getDateFact(month,day).enqueue(new Callback<DateFact>() {
                @Override
                public void onResponse(Call<DateFact> call, Response<DateFact> response) {


                    if(response.body() != null){
                        set_tv.setText(response.body().getText());
                        showBtnHideProgressBar(showBtn,progressBar);
                    }

                }

                @Override
                public void onFailure(Call<DateFact> call, Throwable t) {

                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });
        }else {
            showBtnHideProgressBar(enterBtn,progressBar);
            fact_tv.setText("Please Check Your Internet Connection");
        }





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


    /*Helper for checking internet Connection*/
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




    //-------------------------
    /*Click Listeners*/
    //-------------------------

    View.OnClickListener confirmBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int month = months_list.get(monthSpinner.getSelectedItemPosition());
            int day = days_list.get(daySpinner.getSelectedItemPosition());

            Log.d(TAG, "onClick: month :"+month + " : day "+day);

            if(monthSpinner.getSelectedItem() == null || daySpinner.getSelectedItem() == null){
                Toast.makeText(getActivity(), "Please Select a Number", Toast.LENGTH_LONG).show();
            }else {

                hideBtnShowProgressBar(enterBtn,progressBar);

                fetchDateFact(month,day,fact_tv,enterBtn,progressBar);

            }
        }
    };

}
