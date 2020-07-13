package com.homie.psychq.main.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.homie.psychq.R;

import com.homie.psychq.utils.SharedPreferences;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import javax.inject.Inject;

public class ProductsFragment extends Fragment {

    private static final String TAG = "CategoriesFragmentPsych";

    @Inject
    ImageLoaderConfiguration imageLoaderConfiguration;
    WebView webView;
    ProgressBar progressBar;

    private ShimmerFrameLayout shimmerFrameLayout;

    private SharedPreferences sharedPreferences;

    private TextView noProductsTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_products2,container,false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView=view.findViewById(R.id.webView);
        progressBar=view.findViewById(R.id.progressBar);
        noProductsTv = view.findViewById(R.id.noProductsTv);
        sharedPreferences = new SharedPreferences(getActivity());

        webView.setWebViewClient(new WebViewClient(){
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }

                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }

        });
//        webView.setWebViewClient(new WebViewClient());

        if(sharedPreferences.getString(getString(R.string.StoreStatus),"offline").equals("online")){
            //show the store

            String url = sharedPreferences.getString(getString(R.string.StoreUrl),"https://t-subtle.myshopify.com/");
            webView.loadUrl(url);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setBuiltInZoomControls(true);


        }else {
            //offline
            webView.setVisibility(View.GONE);
            noProductsTv.setVisibility(View.VISIBLE);
        }


    //        webSettings.setDefaultFixedFontSize(15);



    }


}
