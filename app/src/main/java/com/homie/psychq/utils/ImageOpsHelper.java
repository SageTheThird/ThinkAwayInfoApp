package com.homie.psychq.utils;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.ImageLoader;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.GlideImageViewFactory;
import com.homie.psychq.BaseApplication;
import com.victor.loading.rotate.RotateLoading;

import org.aviran.cookiebar2.OnActionClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import needle.Needle;
import xyz.schwaab.avvylib.AvatarView;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;


/*Helper For Downloading, Setting, Set as background Image*/

public class ImageOpsHelper {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final String TAG = "ImageOpsHelper";

    private Context context;
    
    public ImageOpsHelper(Context context) {
        this.context=context;
    }


    public void initBigImageViewer() {
        //CHANGE CONTEXT to APP WHen PostClickActivity is solved

        //here if api is Oreo or greater then init glideimageloader with

        BigImageViewer.initialize(GlideImageLoader.with(context));

    }
    
    public void setImage(BigImageView bigImageView, String thumbnail,
                         String url, LottieAnimationView lottieProgressAnimation, ProgressBar progressBar){

        if(thumbnail != null){
            bigImageView.showImage(Uri.parse(thumbnail),Uri.parse(url));
        }else {
            bigImageView.showImage(Uri.parse(url));
        }



        if(progressBar != null){
            if(progressBar.getVisibility() == View.GONE || progressBar.getVisibility() == View.INVISIBLE){
                progressBar.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.VISIBLE);
        }


        bigImageView.setImageViewFactory(new GlideImageViewFactory());
        //bigImageView.setProgressIndicator(new ProgressPieIndicator());
        bigImageView.setImageLoaderCallback(new ImageLoader.Callback() {
            @Override
            public void onCacheHit(int imageType, File image) {

                //this method is hit after the onProgress and before onSuccess
                if(progressBar != null && progressBar.getVisibility() == View.VISIBLE){
                    progressBar.setVisibility(View.GONE);
                }

                Log.d(TAG, "onCacheHit: ");
            }

            @Override
            public void onCacheMiss(int imageType, File image) {

                Log.d(TAG, "onCacheMiss: ");



            }

            @Override
            public void onStart() {

                Log.d(TAG, "setImage: LottieView visibility : "+lottieProgressAnimation.getVisibility());
                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onProgress(int progress) {

                Log.d(TAG, "onProgress: "+progress);
                if(progressBar != null && progress == 5 ){
                    Log.d(TAG, "onProgress: In if/else : "+progress);
                    progressBar.setProgress(progress);

                }

                if(progressBar != null && progress % 20 == 0 ){
                    Log.d(TAG, "onProgress: In if/else : "+progress);
                    progressBar.setProgress(progress);

                }

                if(progressBar != null && progress == 99){

                    progress++;
                    progressBar.setProgress(progress);

                }



            }

            @Override
            public void onFinish() {
//
                Log.d(TAG, "onFinish: ");
//                if(lottieProgressAnimation != null ){
//                    lottieProgressAnimation.cancelAnimation();
//                    lottieProgressAnimation.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onSuccess(File image) {

                Log.d(TAG, "onSuccess: ");

                if(lottieProgressAnimation != null){
                    lottieProgressAnimation.cancelAnimation();
                    lottieProgressAnimation.setVisibility(View.GONE);
                }
                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFail(Exception error) {
                if(lottieProgressAnimation != null){
                    lottieProgressAnimation.cancelAnimation();
                    lottieProgressAnimation.setVisibility(View.GONE);
                }
                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
                Log.d(TAG, "onFail: "+error.getMessage());
            }
        });


    }

    public void shareIntent(String ImageUrl, Activity activity,String description,ProgressBar progress_loading) {

        String url=ImageUrl.replaceAll("[\\s|\\u00A0]+", "");
        Log.d(TAG, "DownloadImage: "+ url);


        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            showToast("Need Permissions");
        } else {

            if(progress_loading != null){
                progress_loading.setVisibility(View.VISIBLE);
            }
            //Asynctask to create a thread to downlaod image in the background
            DownloadsImage downloadsImage = new DownloadsImage(context,description,progress_loading);
            downloadsImage.execute(url);

        }
    }

    private void showToast(String msg) {
        Toast.makeText(context, ""+msg, Toast.LENGTH_LONG).show();
    }

    public void setImageAsBackground(final String imageUrl,String message) {

        //String modifiedUrl=imageUrl.replaceAll("[\\s|\\u00A0]+", "");
        Needle.onBackgroundThread().execute(new Runnable() {
            @Override
            public void run() {
                Bitmap myBitmap = null;
                final WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(context);
                try {

                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    myBitmap = BitmapFactory.decodeStream(input);
                    myWallpaperManager.setBitmap(myBitmap);
                    Needle.onMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            CookiesHelper cookiesHelper=new CookiesHelper(context);
                            cookiesHelper.showCookie(message+" Quality Image Has Been Set As Background",
                                    "Note : Image is set according to the dimensions displayed on the current screen",
                                    "",
                                    null);
                        }
                    });
                    //Toast.makeText(context, "Image Set As Background : Done", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        });



    }

    public void showErrorDownloadingCookie(){


        CookiesHelper cookiesHelper=new CookiesHelper(context);
        cookiesHelper.showCookie("Error While Downloading Image",
                "Please Check that you are connected to the internet",
                null,null);

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public void downloadImage(String url, String quality,
                               CoordinatorLayout root_layout_psych,
                               RelativeLayout root_layout_unsplash, RelativeLayout card_layout,
                               AvatarView profile_pic, ImageView cancelBtn, TextView progress_tv, ProgressBar downloading_progress,Activity activity) {

        /*
        * Root layout of PixaBay FullScreen and PsychPost are different
        * */

        //For API 23+ you need to request the read/write permissions even if they are already in your manifest.

        verifyStoragePermissions(activity);

        if(root_layout_psych != null){
            TransitionManager.beginDelayedTransition(root_layout_psych);
            card_layout.setVisibility(View.VISIBLE);
        }
        if(root_layout_unsplash != null){
            TransitionManager.beginDelayedTransition(root_layout_unsplash);
            card_layout.setVisibility(View.VISIBLE);
        }


        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/PsychQ").toString();
        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";


        int downloadId = PRDownloader.download(url, path, fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        if(profile_pic != null){
                            profile_pic.setAnimating(true);
                        }
                        showImageDownloadCookie(quality);
                        Log.d(TAG, "onStartOrResume: ");
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                        Log.d(TAG, "onPause: ");
                        Toast.makeText(context, "Download Paused", Toast.LENGTH_LONG).show();
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                        Log.d(TAG, "onCancel: ");
                        Toast.makeText(context, "Download Canceled", Toast.LENGTH_LONG).show();

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        int total = (int) progress.totalBytes;
                        downloading_progress.setMax(total);
                        Log.d(TAG, "onProgress: Progress : "+progress);
                        String progress_ = humanReadableByteCount(progress.currentBytes,true) + " / "+ humanReadableByteCount(progress.totalBytes,true);
                        progress_tv.setText(progress_);
                        int progress_int = (int) progress.currentBytes;
                        downloading_progress.setProgress(progress_int);

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        File imageFile = new File(path, fileName); // Imagename.png

                        MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                //scans the downloaded image into gallery
                                // Log.i("ExternalStorage", "Scanned " + path + ":");
                                //    Log.i("ExternalStorage", "-> uri=" + uri);
                            }
                        });

                        if(profile_pic != null){
                            profile_pic.setAnimating(false);

                        }

                        if(root_layout_psych != null){
                            TransitionManager.beginDelayedTransition(root_layout_psych);
                            card_layout.setVisibility(View.GONE);
                        }
                        if(root_layout_unsplash != null){
                            TransitionManager.beginDelayedTransition(root_layout_unsplash);
                            card_layout.setVisibility(View.GONE);
                        }

                        showDownloadCompleteCookie();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.d(TAG, "onError: Url : "+url);
                        Log.d(TAG, "onError: Error.toString() : "+error.toString());
                        Log.d(TAG, "onError: Sever Error Message : "+error.getServerErrorMessage());
                        Log.d(TAG, "onError: Connection Exception : "+error.getConnectionException());
                        Log.d(TAG, "onError: Response Code : "+error.getResponseCode());
                        Log.d(TAG, "onError: isServer Error : "+error.isServerError());
                        showErrorDownloadingCookie();
                    }

                });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Status.RUNNING == PRDownloader.getStatus(downloadId) || Status.PAUSED == PRDownloader.getStatus(downloadId) ||
                        Status.FAILED == PRDownloader.getStatus(downloadId) ){
                    PRDownloader.cancel(downloadId);

                }
            }
        });
//        dismissSheet();

    }

    private void showImageDownloadCookie(String quality){

        String title = "Saving The " + quality + " Quality Image";
        String message = "This may take a while!!";

        CookiesHelper cookiesHelper=new CookiesHelper(context);
        cookiesHelper.showCookie(title,
                message,
                null,
                null);

    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public void showDownloadCompleteCookie(){


        CookiesHelper cookiesHelper=new CookiesHelper(context);
        cookiesHelper.showCookie("Image Downloaded Successfully",
                "Your Image Was Saved In Gallery : /PsychQ",
                "Open Gallery",
                OpenGalleryActionListener);

    }

    OnActionClickListener OpenGalleryActionListener = new OnActionClickListener() {
        @Override
        public void onClick() {

            Toast.makeText(context, "Opening Gallery", Toast.LENGTH_LONG).show();

        }
    };
}


class DownloadsImage extends AsyncTask<String, String, String> {
    private static final String TAG = "DownloadsImage";

    @Override
    protected void onProgressUpdate(String... values) {




    }

    private final WeakReference<ProgressBar> progressBarWeakReference;
    private final WeakReference<Context> contextWeakReference;


    private String description;


    public DownloadsImage(Context context, String description, ProgressBar progress_bar) {
        this.description =description;
        this.progressBarWeakReference = new WeakReference<>(progress_bar);
        this.contextWeakReference = new WeakReference<>(context);

    }

    @Override
    protected String doInBackground(String... strings) {

        Context context = contextWeakReference.get();

        Log.d(TAG, "doInBackground: url "+strings[0]);
        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bm = null;
        try {
            if (url != null) {
                    bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Create Path to save Image
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/PsychQ"); //Creates app specific folder

        if (!path.exists()) {
            path.mkdirs();
        }

        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(path,fileName ); // Imagename.png
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {


            bm.compress(Bitmap.CompressFormat.PNG, 80, out); // Compress Image
            out.flush();
            out.close();

            String path_ = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    bm, fileName, null);
            Uri imageUri =  Uri.parse(path_);
            Intent share = new Intent(Intent.ACTION_SEND);

            Needle.onMainThread().execute(new Runnable() {
                @Override
                public void run() {
                    share.setType("image/jpeg");
                    share.putExtra(Intent.EXTRA_STREAM, imageUri);
                    share.putExtra(Intent.EXTRA_TEXT, description);
//                    share.putExtra(Intent.EXTRA_TEXT, appLink);
                    context.startActivity(Intent.createChooser(share, "Select"));
                }
            });

            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
//            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//                public void onScanCompleted(String path, Uri uri) {
//                    // Log.i("ExternalStorage", "Scanned " + path + ":");
//                    //    Log.i("ExternalStorage", "-> uri=" + uri);
//                }
//            });
        } catch (Exception e) {
        }
        return null;


    }

    @Override
    protected void onPostExecute(String imageFile) {
        super.onPostExecute(imageFile);

        ProgressBar progress_bar= progressBarWeakReference.get();
        if(progress_bar != null){
            progress_bar.setVisibility(View.GONE);
        }


    }





    }
