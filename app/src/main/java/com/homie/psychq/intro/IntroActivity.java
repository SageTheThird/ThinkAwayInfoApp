package com.homie.psychq.intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.homie.psychq.R;
import com.homie.psychq.auth.ui.AuthActivity;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;


/*Introduction of the app activity*/
public class IntroActivity extends AppCompatActivity {

    private static final String TAG = "IntroActivity";
    
    private ViewPager viewPager;
    private List<IntroModel> items_list;
    private IntroPagerAdapter adapter;
    private Button nextBtn,prevBtn;
    private PageIndicatorView indicatorView;
    private ImageView background_iv;

    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initUiComponents();

        /*Load background, build list of data, and feed it to viewpager*/
        loadBackground();
        generateData();
        initViewPager();


        nextBtn.setOnClickListener(NextBTnCL);
        prevBtn.setOnClickListener(PrevBTnCL);

    }

    private void initUiComponents() {
        viewPager=findViewById(R.id.intro_viewpager);
        nextBtn=findViewById(R.id.next_intro);
        prevBtn=findViewById(R.id.prev_intro);
        indicatorView=findViewById(R.id.pageIndicatorView_intro);
        background_iv=findViewById(R.id.backgorund_intro);
    }

    private void initViewPager() {
        adapter=new IntroPagerAdapter(this,items_list);

        viewPager.setAdapter(adapter);
        indicatorView.setViewPager(viewPager);
        indicatorView.setCount(items_list.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == items_list.size() -1){
                    nextBtn.setText(getString(R.string.finish_intro));
                }else {
                    nextBtn.setText("NEXT");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadBackground() {
        Glide.with(this).load(R.drawable.auth_background).into(background_iv);

    }

    private void gotoAuthActivity() {
        startActivity(new Intent(IntroActivity.this, AuthActivity.class));
        finish();
    }

    private void generateData() {

        //since all the assets is inside package, we form it into data and feed it to viewpager
        items_list=new ArrayList<>();
        items_list.add(new IntroModel(R.drawable.vcetor_page_3,getString(R.string.titlePageOne),getString(R.string.pageOneText))); // PsychQ
        items_list.add(new IntroModel(R.drawable.vector_page_1,getString(R.string.titlePageTwo),getString(R.string.pageTwoText))); // Self-Improvement
        items_list.add(new IntroModel(R.drawable.vector_page_2,getString(R.string.titlePageThree),getString(R.string.pageThreeText))); //Emotional Intelligence

    }


    /*Click Listeners*/
    View.OnClickListener NextBTnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(nextBtn.getText().toString().equals(getString(R.string.finish_intro))){
                gotoAuthActivity();
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        }
    };
    View.OnClickListener PrevBTnCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

        }
    };
}
