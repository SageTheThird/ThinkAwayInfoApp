package com.homie.psychq.main.ui.categories;

import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.homie.psychq.R;
import com.homie.psychq.di.psych.DaggerPsychComponent;
import com.homie.psychq.di.psych.PsychComponent;
import com.homie.psychq.main.api.main.PsychApi;
import com.homie.psychq.main.models.categories.CategoryFeatured;
import com.homie.psychq.main.models.categories.ResultsCategories;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CategoriesFragmentPsych extends Fragment implements CategoriesAdapterPsych.OnPhotoClickedListener {
    private static final String TAG = "CategoriesFragmentPsych";
    private RecyclerView recyclerView;
    private CategoriesAdapterPsych adapter;
    private ProgressDialog dialog;
    private List<CategoryFeatured> category_list;

    PsychApi psychApi;
    PsychComponent component;

    private ShimmerFrameLayout shimmerFrameLayout;

    //Pagination

    private int pageNumber = 1;
    private boolean loading = false;
    private final int VISIBLE_THRESHOLD = 1;
    private int lastVisibleItem;
    private int totalItemCount;


    LinearLayoutManager layoutManager;
    private RelativeLayout root_layout;


    //Error Layout Setup
    private RelativeLayout errorLayout;
    private ImageView errorIv;
    private ProgressBar errorProgress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_categories,container,false);

        recyclerView =view.findViewById(R.id.list_view);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_categoires_frameLayout);
        errorLayout = view.findViewById(R.id.errroLayoutRel);
        errorIv = view.findViewById(R.id.error_iv);
        errorProgress = view.findViewById(R.id.error_progress);
        root_layout = view.findViewById(R.id.categoriesRootRelative);

        recyclerView.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();

        //viewModel= ViewModelProviders.of(getActivity()).get(ViewModel.class);


           // mRepo=new Repository(walls_list_full,walls_list_thumbs,walls_keys_full_list,getActivity());

        initComponent();
        setupRecyclerView();
        loadData();







        return view;
    }


    private void showErrorUI(){
        if(errorLayout.getVisibility() == View.GONE){
            TransitionManager.beginDelayedTransition(root_layout);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }
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

    private void initComponent() {

        component= DaggerPsychComponent.builder()
                .build();

        psychApi=component.providePsychApi();
    }
    private void setupRecyclerView(){
        category_list=new ArrayList<>();
        adapter=new CategoriesAdapterPsych(getActivity(),this);
        layoutManager=new LinearLayoutManager(getActivity());
        final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);





    }

    private void loadData() {

        //Fetch the categories list from api and add it to recyclerview

        psychApi.getAllCategories(pageNumber)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultsCategories>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultsCategories resultsCategories) {

                        if(resultsCategories != null){
                            hideErrorUI();
                            Log.d(TAG, "onNext: "+resultsCategories.getCount());
                            Log.d(TAG, "onNext: list size : "+category_list.size());
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);

                            //we add the following for a header
                            category_list.add(new CategoryFeatured("","","","","","","","","","",""));
                            category_list.addAll(resultsCategories.getCategories());
                            adapter.addPhotos(category_list);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

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




                    //loadingProgress.setVisibility(View.VISIBLE);

                    psychApi.getAllCategories(pageNumber).toObservable().
                            subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResultsCategories>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(ResultsCategories results) {


                                    if(results != null){
                                        adapter.addPhotos(results.getCategories());
                                    }

                                    loading=false;

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });




                    //paginator.onNext(pageNumber);


                }
            }
        });


    }

    public int getRandomInt(){
        int min = 1267;
        int max = 30000;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        return i1;
    }


    private void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }



    private void showProgressDialog(Context context){
        dialog=new ProgressDialog(context,R.style.MyAlertDialogStyle);
        dialog.setTitle("Loading..");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void photoClicked(CategoryFeatured categoryUnit, ImageView topImageView, int position) {


        Intent intent=new Intent(getActivity(),CategoryOnClick.class);


        intent.putExtra("comingFrom","categories");
        intent.putExtra("title",categoryUnit.getTitle());
        intent.putExtra("postsCount",categoryUnit.getTotal_posts_count());
        intent.putExtra("description",categoryUnit.getDescription());
        startActivity(intent);

    }



    @Override
    public void onStart() {
        super.onStart();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
}
