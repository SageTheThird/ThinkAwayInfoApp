package com.homie.psychq.auth.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.homie.psychq.BaseApplication;
import com.homie.psychq.subscription.SubscriptionActivity;
import com.homie.psychq.utils.SharedPreferences;
import com.homie.psychq.intro.IntroActivity;
import com.homie.psychq.main.ui.MainActivity;
import com.homie.psychq.R;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;



/*
* Responsible for all authentication logic and ui
* */
public class  AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";
    public static final int RC_SIGN_IN=3;


    @Inject
    GoogleSignInOptions gso;
    @Inject
    SharedPreferences sharedPreferences;


    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private Button google_sign_in_btn;
    private ProgressBar progressBar;
    private EditText input_email, input_password, input_confirm_pass;
    private TextView email_tv, password_tv, confirm_pass_tv;
    private Button signUp_btn, sign_inBtn;
    private TextView message_tv,termsAndConditions;
    private FirebaseUser user;
    private ConstraintLayout constraintLayout;
    private boolean signInTappedOnce,signUpTappedOnce =false;
    private ImageView background_iv;
    private String UserEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        initUiComponents();
        loadBackground();
        introScreenLogic();
        signInLogic();


    }

    /*If user is opening the app for the first time, show intro*/
    private void introScreenLogic() {
        if(sharedPreferences.getBooleanPref(getString(R.string.intro_screen_bool),true)){

            gotoIntroActivity();

            sharedPreferences.saveBooleanPref(getString(R.string.intro_screen_bool),false);

        }
    }

    /*If user is new, init signUp - if old user and subscribed, direct to main feeds
    * if old user and not subscribed, direct to subscription activity
    * */
    private void signInLogic() {
        if(sharedPreferences.getBooleanPref(getString(R.string.loginStatus),false)){

            /*if the user is subscribed, go directly to main feeds otherwise show subscription page*/
            if(sharedPreferences.getBooleanPref(getString(R.string.isSubscribed),false)){
                gotoMainActivity();
            }else {
                gotoSubscriptionActivity();
            }

        }else {

            sign_inBtn.setOnClickListener(SignInCL);
            signUp_btn.setOnClickListener(SignUpCL);
            // Configure Google Sign In
            mGoogleSignInClient=GoogleSignIn.getClient(this,gso);
            google_sign_in_btn.setOnClickListener(GoogleSignInCL);

        }
    }


    private void initUiComponents() {
        google_sign_in_btn=findViewById(R.id.google_signIn_btn);
        progressBar=findViewById(R.id.auth_progress_bar);
        input_email=findViewById(R.id.email);
        input_password=findViewById(R.id.password);
        input_confirm_pass=findViewById(R.id.confirm_pass);
        signUp_btn =findViewById(R.id.basic_signup);
        message_tv=findViewById(R.id.message_tv);
        sign_inBtn =findViewById(R.id.sign_in_tv);
        email_tv=findViewById(R.id.email_tv);
        password_tv=findViewById(R.id.password_tv);
        confirm_pass_tv=findViewById(R.id.confirm_pass_tv);
        constraintLayout=findViewById(R.id.constraintLayout_auth);
        background_iv=findViewById(R.id.main_iv_auth);
        termsAndConditions=findViewById(R.id.termsAndConditionsTv);

        termsAndConditions.setOnClickListener(TermsAndConditionsClickListener);

    }

    /*Loads image into background using Glide*/
    private void loadBackground() {
        Glide.with(this).load(R.drawable.auth_background).into(background_iv);
    }

    private void gotoSubscriptionActivity() {
        Intent intent = new Intent(AuthActivity.this, SubscriptionActivity.class);
        startActivity(intent);
    }

    /*Loads Privacy policy in the default browser*/
    private void directUserToTermsAndConditions() {

        Toast.makeText(AuthActivity.this, "Directing to terms and conditions", Toast.LENGTH_LONG).show();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://sneezy3d.com:25255/PrivacyPolicies/apps/PsychQ/blog.html")));

    }



    /*When account is selected, send it to firebase for verification and act accordingly*/
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            sharedPreferences.saveBooleanPref(getString(R.string.loginStatus),true);
                            gotoMainActivity();

                        } else {
                            // If sign in fails, display a message to the user.
                             Toast.makeText(BaseApplication.get(), "Login Failed : "+task.getException(), Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }


    /*Handles basic SignIn (Not Google)
    * Checks the account on firebase
    * Also stores user's email, username, profilePic, UUid in prefs for future use
    * */
    private void signInBasic(){

        //Here the code for sign in goes
        if(!TextUtils.isEmpty(input_email.getText().toString())
                && !TextUtils.isEmpty(input_password.getText().toString())
        ){

            String email = input_email.getText().toString();
            String password = input_password.getText().toString();



            user=FirebaseAuth.getInstance().getCurrentUser();

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(!task.isSuccessful()){

                                //Toast.makeText(AuthActivity.this, "Sign In Failed : "+task.getException(), Toast.LENGTH_LONG).show();
                                String message="Sign In Failed : "+task.getException();
                                message_tv.setText(message);
                            }else {
                                //user signed in

                                hideProgress();
                                if(FirebaseAuth.getInstance().getCurrentUser() != null && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    //Email verified Sign in Email And Password
                                    //here we have a logged in and verified user
                                    //Here we can get the data of the signed in user and use for different fields



                                    Map<String, String> userInfomap=new HashMap<>();

                                    if(FirebaseAuth.getInstance().getCurrentUser().getDisplayName() != null){

                                        userInfomap.put(getString(R.string.username),FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                                    }

                                    if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null){

                                        userInfomap.put(getString(R.string.profile_pic),FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
                                    }

                                    if(FirebaseAuth.getInstance().getCurrentUser().getEmail() != null){

                                        UserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                        userInfomap.put(getString(R.string.email),UserEmail );
                                    }

                                    userInfomap.put(getString(R.string.uid), FirebaseAuth.getInstance().getCurrentUser().getUid());

                                    if(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() != null){

                                        userInfomap.put(getString(R.string.phone_number), FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                                    }




                                    //Convert User info into string using gson

                                    //convert to string using gson

                                    String userInfoString = sharedPreferences.getStringFromMap(userInfomap);

                                    //save in shared prefs
                                    //and when we want to get the string we call getMapFromString( pass the map string here)
                                    sharedPreferences.saveStringPref(getString(R.string.user_info_map_pref),userInfoString);




                                    message_tv.setText("Cheers!!");
                                    sharedPreferences.saveBooleanPref(getString(R.string.loginStatus),true);
                                    gotoMainActivity();


                                }else {
                                    //User is null or Email Not Verified

                                    FirebaseAuth.getInstance().signOut();
                                    message_tv.setText("Please Verify Your Email");
                                    hideProgress();


                                }




                            }
                        }
                    });

            //Log.d(TAG, "onClick: "+user.getDisplayName());





        }else {
            //when edittexts are empty
            message_tv.setText("Provide Email And Password");
            hideProgress();

        }
    }

    private void gotoIntroActivity() {
        startActivity(new Intent(AuthActivity.this, IntroActivity.class));
    }

    /*Activates UI for Basic SignUp*/
    private void transitUIforSignUp() {

        TransitionManager.beginDelayedTransition(constraintLayout);
        email_tv.setVisibility(View.VISIBLE);
        input_email.setVisibility(View.VISIBLE);
        password_tv.setVisibility(View.VISIBLE);
        input_password.setVisibility(View.VISIBLE);
        confirm_pass_tv.setVisibility(View.VISIBLE);
        input_confirm_pass.setVisibility(View.VISIBLE);

        message_tv.setText("Sign Up Selected");

        signUpTappedOnce=true;
        if(signInTappedOnce){
            signInTappedOnce=false;
        }
    }

    /*WHen all the checks are passed, handles creation of account on firebase*/
    private void createNewUser() {

        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        String confirm_pass = input_confirm_pass.getText().toString();

        if(!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirm_pass))
        {
            //when the fields are not empty we then check for passwords to match
            if(!password.equals(confirm_pass)){
                message_tv.setText("Passwords need to match");
                hideProgress();
            }else {
                //sign up user here
                signUpUser(email,password);
            }

        }else {
            message_tv.setText("Please Fill The Form");
            hideProgress();
        }
    }

    /*Request Email and Password to Firebase for potential verification*/
    private void signUpUser(String email,String password) {

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){

                            String message= "Sign Up Failed : "+task.getException().getMessage();
                            message_tv.setText(message);
                            hideProgress();


                        }else {
                            //successfully created account
                            message_tv.setText("Account Created Successfully");
                            user = mAuth.getCurrentUser();

                            if(user !=  null){

                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    //Transit UI to login

                                                    hideProgress();

                                                    FirebaseAuth.getInstance().signOut();
                                                    transitUIforSignIn();
                                                    String message="Confirmation Mail has been sent to your email. Please Confirm email and try signing in";
                                                    message_tv.setText(message);


                                                }else {
                                                    hideProgress();
                                                    String message="Failed to send a confirmation mail";
                                                    message_tv.setText(message);
                                                    Log.d(TAG, "onComplete: Error Sending mail : "+task.getException());
                                                }
                                            }
                                        });
                            }


                        }
                    }
                });
    }

    private void hideProgress(){

        if(progressBar.getVisibility() == View.VISIBLE){
            TransitionManager.beginDelayedTransition(constraintLayout);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
    private void showProgress(){

        if(progressBar.getVisibility() == View.INVISIBLE){
            TransitionManager.beginDelayedTransition(constraintLayout);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    /*Activates UI for Basic SignIn*/
    private void transitUIforSignIn() {

        TransitionManager.beginDelayedTransition(constraintLayout);

        email_tv.setVisibility(View.VISIBLE);
        input_email.setVisibility(View.VISIBLE);
        password_tv.setVisibility(View.VISIBLE);
        input_password.setVisibility(View.VISIBLE);
        confirm_pass_tv.setVisibility(View.GONE);
        input_confirm_pass.setVisibility(View.GONE);

        message_tv.setText("Sign In Selected");

        //signUp_btn.setVisibility(View.GONE);

        signInTappedOnce=true;

        if(signUpTappedOnce){
            signUpTappedOnce=false;
        }
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    /*Launches activity where user selects a google account*/
    private void signInViaGoogle() {
        showProgress();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Results returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);


                /*
                * Saving user info in a map after fetched successfully
                * User info can be retrieved from sharedPreferences with user_info_map_pref and then call
                * function getMapFromString in sharedPreferences
                * */

                Map<String,String> userInfoMap = new HashMap<>();

                if(task.getResult().getDisplayName() != null ){
                    userInfoMap.put(getString(R.string.username), task.getResult().getDisplayName());
                }
                if(task.getResult().getId() != null ){
                    userInfoMap.put(getString(R.string.uid), task.getResult().getId());
                }
                if(task.getResult().getPhotoUrl() != null ){
                    userInfoMap.put(getString(R.string.profile_pic), task.getResult().getPhotoUrl().toString());
                }
                if(task.getResult().getEmail() != null ){
                    userInfoMap.put(getString(R.string.email), task.getResult().getEmail());
                }else {
                    //we make sure that email is stored in sharedPreferences for subscription Validation
                    // if not email, save Username
                    if(task.getResult().getDisplayName() != null){
                        userInfoMap.put(getString(R.string.email), task.getResult().getDisplayName());
                    }
                }


                String infoMapString = sharedPreferences.getStringFromMap(userInfoMap);
                sharedPreferences.saveStringPref(getString(R.string.user_info_map_pref),infoMapString);



                if(account != null ){
                    firebaseAuthWithGoogle(account);
                    hideProgress();
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                message_tv.setText("Google Sign In Failed : "+e.getMessage());
                hideProgress();
                // ...
            }
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }




    /*Buttons Click Listeners*/
    View.OnClickListener SignInCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!signInTappedOnce){
                //if not tapped once transit UI
                transitUIforSignIn();

            }else {
                //if tapped once, process sign in
                TransitionManager.beginDelayedTransition(constraintLayout);
                progressBar.setVisibility(View.VISIBLE);
                signInBasic();
            }
        }
    };
    View.OnClickListener SignUpCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!signUpTappedOnce){
                //if not tapped once transit UI
                transitUIforSignUp();

            }else {
                //if tapped once, process sign up
                TransitionManager.beginDelayedTransition(constraintLayout);
                progressBar.setVisibility(View.VISIBLE);
                createNewUser();

            }
        }
    };
    View.OnClickListener GoogleSignInCL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            signInViaGoogle();
        }
    };
    View.OnClickListener TermsAndConditionsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            directUserToTermsAndConditions();
        }
    };




}
