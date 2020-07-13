package com.homie.psychq.main.ui.factspool;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.FactsPoolModule;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.factspool.FactsPoolApi;
import com.homie.psychq.main.adapters.FactsPoolAdapter;
import com.rd.PageIndicatorView;


public class FactsPoolActivity extends AppCompatActivity {
    private static final String TAG = "FactsPoolActivity";



    PsychComponent component;
    FactsPoolApi factsPoolApi;

    private ViewPager viewPager;
    private PageIndicatorView pageIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_pool_fresh);
        viewPager=findViewById(R.id.factsPool_ViewPager);
        pageIndicatorView=findViewById(R.id.pageIndicatorView_fpool);

        component= DaggerPsychComponent.builder()
                .factsPoolModule(new FactsPoolModule(this))
                .build();

        factsPoolApi=component.provideFactsApi();

        viewPager.setAdapter(new FactsPoolAdapter(getSupportFragmentManager()));
        pageIndicatorView.setViewPager(viewPager);



    }









    }

