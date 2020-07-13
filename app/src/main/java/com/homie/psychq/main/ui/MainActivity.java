package com.homie.psychq.main.ui;


import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.homie.psychq.About;
import com.homie.psychq.main.favourites.FavouritesActivity;
import com.homie.psychq.subscription.SubscriptionActivity;

import com.homie.psychq.utils.SharedPreferences;

import com.homie.psychq.main.settings.SettingsActivity;
import com.homie.psychq.main.ui.factspool.FactsPoolActivity;
import com.homie.psychq.R;
import com.homie.psychq.auth.ui.AuthActivity;
import com.homie.psychq.room2.DatabaseTransactions;
import com.stepstone.apprating.listener.RatingDialogListener;
import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;





public class MainActivity extends DaggerAppCompatActivity implements RatingDialogListener {
    private static final String TAG = "MainActivity";


    private DrawerLayout myDrawerMain;
    private ActionBarDrawerToggle myToggle;
    private NavigationView navView;
    private BottomNavigationView bottomNav;
    private Toolbar toolbar;
    @Inject
    SharedPreferences sharedPreferences;
    DatabaseTransactions databaseTransactions;
    NavController navController;
    TextView app_title;
    private AppBarLayout appBarLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        appBarLayout = findViewById(R.id.app_bar_layout);
        app_title=findViewById(R.id.app_title_tv);


        subscriptionBasedLogin();

    }

    /*
     * isSubscribed is for unlock Content
     * isOnTrial is for accessing MainActivity with Locked Content
     * isSignedIn is for login
     * */
    private void subscriptionBasedLogin() {
        if(sharedPreferences.getBooleanPref(getString(R.string.loginStatus),false)){

            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)
                    || sharedPreferences.getBooleanPref(getString(R.string.isOnTrial),false)){

                initWidgets();
                drawerSetup();
                navigationSetup();
                initNav();
                initRoom();

            }else {
                //if he hasn't clicked subscribeLater, he cant access the MainActivity
                //So we direct him to Subscription
                gotoSubscriptionActivity();

            }



        }else {
            //uncomment this when google sign in works
            gotoAuthActivity();
        }
    }

    private void gotoSubscriptionActivity() {
        Intent intent = new Intent(MainActivity.this, SubscriptionActivity.class);
        startActivity(intent);
    }

    private void initRoom() {

        databaseTransactions=new DatabaseTransactions(this);

    }

    private void gotoAuthActivity() {
        startActivity(new Intent (MainActivity.this,AuthActivity.class));
        finish();
    }

    /*Bottom Navigation Setup*/
    private void initNav() {
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this,navController);
        NavigationUI.setupWithNavController(bottomNav,navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {

                //changes toolbar title when going through fragments
                if(destination.getId() == R.id.featuredPsychFragment){
                    app_title.setText("Feeds");
                    appBarLayout.setExpanded(false,true);
                }

//                if(destination.getId() == R.id.pixaBay){
//                    app_title.setText("Gallery");
//                }
                if(destination.getId() == R.id.categoriesFragment){
                    app_title.setText("Categories");
//                    appBarLayout.setExpanded(false,true);

                }
                if(destination.getId() == R.id.crashCourseFragment){
                    app_title.setText("Crash Courses");
//                    appBarLayout.setExpanded(false,true);
                }
                if(destination.getId() == R.id.productsFragment){
                    app_title.setText("Products");
//                    appBarLayout.setExpanded(false,true);
                }

            }
        });

        //navigationView.setNavigationItemSelectedListener(this);

    }

    private void drawerSetup() {
        setSupportActionBar(toolbar);
        myToggle = new ActionBarDrawerToggle(this, myDrawerMain, toolbar,
                R.string.open, R.string.close);
        myDrawerMain.addDrawerListener(myToggle);

    }

    private void navigationSetup() {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.menu_home:

                        myDrawerMain.closeDrawers();

                        break;

                    case R.id.menu_facts_pool:

                        Intent intent=new Intent(MainActivity.this, FactsPoolActivity.class);
                        startActivity(intent);

                        break;

                    case R.id.menu_favourites:

                        Intent intentFav=new Intent(MainActivity.this, FavouritesActivity.class);
                        startActivity(intentFav);

                        break;

                    case R.id.settings:

                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                        break;

                    case R.id.menu_about:

                        Intent about_intent=new Intent(MainActivity.this, About.class);
                        startActivity(about_intent);

                        break;
                    case R.id.menu_send:

                        //opens app chooser to share the app
                        Intent myIntent=new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String shareSub="Hey, Try out this app that have tons of interesting content to go through : ";
                        String shareBody="http://play.google.com/store/apps/details?id=" + getPackageName();
                        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(myIntent,"Share App Link via "));


                        break;

                    case R.id.rate_menu:



                        directToGooglePlay();


                        break;

                    case R.id.patreon_menu:

                        try {

                            //replace chrome with getPackageName() later
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(getString(R.string.patreon_url))));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(getString(R.string.patreon_url))));
                        }

                        break;


                }

                return true;
            }
        });
    }

    private void directToGooglePlay() {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(myToggle != null){
            myToggle.syncState();
        }

    }


    private void initWidgets() {
        myDrawerMain = findViewById(R.id.myDrawer);

        navView = findViewById(R.id.navigationView);
        bottomNav = findViewById(R.id.bottom_navigation);
        toolbar = findViewById(R.id.main_toolbar);


    }
    @Override
    public void onBackPressed() {
        if (myDrawerMain.isDrawerOpen(GravityCompat.START)) {
            myDrawerMain.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNegativeButtonClicked() {


        sharedPreferences.saveBooleanPref(getString(R.string.userAlreadySubmittedReview),true);

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int i, @NotNull String s) {

        //When user hits submit, userAlreadySubmittedReview to true
        //and then we can wont show the review again to the same user
        sharedPreferences.saveBooleanPref(getString(R.string.userAlreadySubmittedReview),true);
        directToGooglePlay();
    }
}