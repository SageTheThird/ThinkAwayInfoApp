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
import com.homie.psychq.main.models.factspool.YearFact;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/*Fragment For Year Facts*/

public class YearFragment extends Fragment {

    private static final String TAG = "YearFragment";


    private EditText year_ed;
    private TextView facts_tv;
    private Button incrementBtn,decrementBtn;
    private int count;
    private Button enterBtn;
    private ProgressBar progressBar;
    PsychComponent component;
    FactsPoolApi factsPoolApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_year,container,false);

        initUiComponents(view);
        initComponent();

        //Default Request
        year_ed.setText("1950");
        fetchFact(1950,facts_tv,enterBtn,progressBar);


        enterBtn.setOnClickListener(confirmBtnCL);
        incrementBtn.setOnClickListener(incrementBtnCL);
        decrementBtn.setOnClickListener(decrementBtnCL);


        return view;
    }

    private void initUiComponents(View view) {
        facts_tv=view.findViewById(R.id.fact_tv_year);
        incrementBtn=view.findViewById(R.id.increment_btn_year);
        decrementBtn=view.findViewById(R.id.decrement_year);
        year_ed=view.findViewById(R.id.year_edt);
        enterBtn=view.findViewById(R.id.go_btn);
        progressBar=view.findViewById(R.id.progressBarYear);
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

    private void fetchFact(int input, final TextView set_tv, final Button showBtn , final ProgressBar progressBar) {


        if(isNetworkAvailable()){
            factsPoolApi.getYearsFact(input).enqueue(new Callback<YearFact>() {
                @Override
                public void onResponse(Call<YearFact> call, Response<YearFact> response) {

                    if(response.body() != null){
                        set_tv.setText(response.body().getText());
                        showBtnHideProgressBar(showBtn,progressBar);
                    }



                }

                @Override
                public void onFailure(Call<YearFact> call, Throwable t) {

                    Log.d(TAG, "onFailure: "+t.getMessage());
                }
            });
        }else {
            showBtnHideProgressBar(enterBtn,progressBar);
            facts_tv.setText("Please Check Your Internet Connection");

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
            if(TextUtils.isEmpty(year_ed.getText().toString())){
                count = 0;
                year_ed.setText(String.valueOf(count));
            }else {

                count = Integer.parseInt(year_ed.getText().toString());

                count += 1;
                year_ed.setText(String.valueOf(count));
            }
        }
    };
    View.OnClickListener decrementBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(TextUtils.isEmpty(year_ed.getText().toString())){
                count = 0;
                year_ed.setText(String.valueOf(count));
            }else {
                if(count > 0){

                    count = Integer.parseInt(year_ed.getText().toString());

                    count -= 1;
                    year_ed.setText(String.valueOf(count));
                }

            }
        }
    };
    View.OnClickListener confirmBtnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String input_number=year_ed.getText().toString();

            //Gives NumberFormatException For input string: ""
            int number=1;
            if (!input_number.equals("")){
                number=Integer.parseInt(input_number);
            }


            if(TextUtils.isEmpty(year_ed.getText().toString())){
                Toast.makeText(getActivity(), "Please Select a Year", Toast.LENGTH_LONG).show();
            }else {



                hideBtnShowProgressBar(enterBtn,progressBar);

                fetchFact(number,facts_tv,enterBtn,progressBar);

            }
        }
    };


}
