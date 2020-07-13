package com.homie.psychq.main.ui.crashcourses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.airbnb.lottie.LottieAnimationView;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.adapters.CCFragmentAdapter;
import com.homie.psychq.main.models.crashcourses.CrashCourseUnit;
import com.homie.psychq.main.models.crashcourses.ResultsCrashCourses;
import com.homie.psychq.utils.CookiesHelper;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
* CrashCourses display fragment
* */
public class CrashCourseFragment extends Fragment implements CCFragmentAdapter.OnCCClickedListener {

    private static final String TAG = "CrashCourseFragment";


    PsychApi psychApi;
    PsychComponent component;
    private RecyclerView recyclerView;
    private CCFragmentAdapter adapter;
    private List<CrashCourseUnit> cc_list;
    private LinearLayoutManager layoutManager;
    private int pageNumber = 1;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;
    private LottieAnimationView lottieAnimationView;
    private CookiesHelper cookiesHelper;
    private int totalPages;
    private CompositeDisposable mDisposibles = new CompositeDisposable();
    private RelativeLayout errorLayout;
    private ImageView errorIv;
    private ProgressBar errorProgress;
    private RelativeLayout root_layout;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_crash_course,container,false);


        initUiComponents(view);
        initComponents();
        startAnimation();
        setupRv();
        loadData();


        return view;
    }

    private void initUiComponents(View view) {
        recyclerView=view.findViewById(R.id.cc_rv);
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        root_layout = view.findViewById(R.id.crashCourseFragmentRoot);
        errorLayout = view.findViewById(R.id.errroLayoutRel);
        errorIv = view.findViewById(R.id.error_iv);
        errorProgress = view.findViewById(R.id.error_progress);
    }


    /*Gets all the crash courses from API and sets the data to UI*/
    private void loadData() {

        psychApi.getAllCrashCourses(pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultsCrashCourses>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposibles.add(d);
                    }


                    @Override
                    public void onNext(ResultsCrashCourses resultsCrashCourses) {

                        if(resultsCrashCourses != null){
                            Log.d(TAG, "onNext: "+resultsCrashCourses.toString());
                            hideErrorUI();

                            totalPages = resultsCrashCourses.getCount() / 10;
                            if(resultsCrashCourses.getCount() % 10 > 0){

                                totalPages++;

                            }

                            stopAnimation();
                            //dummy item to replace header
                            cc_list.add(new CrashCourseUnit("s","s","s","s","s","s","s","s","s","s","s","s","s","s","s",true,"s"));
                            cc_list.addAll(resultsCrashCourses.getCrashCourses());
                            adapter.addPhotos(cc_list);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                        cookiesHelper.showCookie("Problem While Loading","Please check your internet and try refreshing",null,null);
                        showErrorUI();
                        hideErrorProgressbar();
                        errorIv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                showErrorProgressbar();
                                loadData();
                            }
                        });

                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }


    /*If we encounter any error while loading data, we show ErrorUI*/
    private void showErrorUI(){
        if(errorLayout.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }

    /*When the error (while loading data) is resolved, we hide the ErrorUI*/
    private void hideErrorUI(){
        if(errorLayout.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorLayout.setVisibility(View.GONE);
        }


    }

    private void showErrorProgressbar(){
        if(errorProgress.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorProgress.setVisibility(View.VISIBLE);
        }

    }

    private void hideErrorProgressbar(){
        if(errorProgress.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorProgress.setVisibility(View.GONE);
        }

    }

    /*Starts lottie animation while loading*/
    private void startAnimation() {

        lottieAnimationView.setVisibility(View.VISIBLE);
        String animationName="feeds_loading.json";
        lottieAnimationView.setAnimation(animationName);
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

    }

    /*Stops lottie animation when loading is finished*/
    private void stopAnimation(){
        if(lottieAnimationView.getVisibility() == View.VISIBLE){

            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
        }

    }

    /*Sets up RecyclerView and handles scrolling Logic*/
    private void setupRv() {

        cc_list=new ArrayList<>();

        adapter=new CCFragmentAdapter(getActivity(),this);
        layoutManager=new LinearLayoutManager(getActivity());

        final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
//                totalItemCount= staggeredGridLayoutManager.getItemCount();
//                lastVisibleItem= getLastVisibleItem(lastVisibleItemPositions);

                totalItemCount=layoutManager.getItemCount();
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();


                if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)) {

                    Log.d(TAG, "onScrolled: working");
                    loading = true;
                    pageNumber++;


                    if(pageNumber <= totalPages){


                        psychApi.getAllCrashCourses(pageNumber)
                                .toObservable()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ResultsCrashCourses>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                        mDisposibles.add(d);
                                    }

                                    @Override
                                    public void onNext(ResultsCrashCourses resultsCrashCourses) {

                                        if(resultsCrashCourses != null){
                                            Log.d(TAG, "onNext: "+resultsCrashCourses.toString());
                                            adapter.addPhotos(resultsCrashCourses.getCrashCourses());
                                        }

                                        loading=false;

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "onError: Error while fetching article Message : " +e.getMessage());
                                        Log.d(TAG, "onError: Error while fetching article Cause : " +e.getCause());

                                    }

                                    @Override
                                    public void onComplete() {


                                    }
                                });

                    }




                    //paginator.onNext(pageNumber);


                }
            }
        });



    }

    private void initComponents() {

        component= DaggerPsychComponent.builder()
                .build();

        psychApi=component.providePsychApi();
        cookiesHelper = new CookiesHelper(getActivity());
    }


    private void vibrate(){
        if(getActivity() !=null){
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(50);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposibles.clear();
    }


    /*Interface in CCFragmentAdapter for click listener*/
    @Override
    public void ccClicked(CrashCourseUnit ccUnit, ImageView topImageView, int position) {
        vibrate();
        Intent intent = new Intent(getActivity(),CrashCourseOnClick.class);
        intent.putExtra("courseName",ccUnit.getId());
        intent.putExtra("courseDescription",ccUnit.getDescription());
        intent.putExtra("author",ccUnit.getSource());
        intent.putExtra("thumb",ccUnit.getPic_top());
        intent.putExtra("title",ccUnit.getTitle());
        intent.putExtra("is_finished",ccUnit.isIs_finished());
        intent.putExtra("lastArticleOfCourse",ccUnit.getLastArticleUrl());
        startActivity(intent);
    }


}
