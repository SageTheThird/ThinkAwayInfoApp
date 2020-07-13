package com.homie.psychq;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;


/*About section of the app in drawer
* Contains social links, premium links, donate, remove ads, similar apps
* */

public class About extends AppCompatActivity {


    AnimationDrawable logoAnimation;
    private TextView mAbout_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       setContentView(R.layout.activity_about);
//       mAbout_tv=findViewById(R.id.about_tv);

        AboutBuilder builder = AboutBuilder.with(this)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setPhoto(R.mipmap.profile_picture)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Kurtz_sopn")
                .setSubTitle("Mobile Developer")
                .setLinksColumnsCount(4)
                .setBrief("Three words : Innovative, Steadfast, Meticulous") //innovative,steadfast,meticulous
                .addGooglePlayStoreLink("http://play.google.com/store/apps/details?id=com.homie.psychq")
                .addGitHubLink("Kurtz0420")
                .addBitbucketLink("")
                .addFacebookLink("user")
                .addTwitterLink("user")
                .addInstagramLink("psychqq")
                .addGooglePlusLink("")
                .addYoutubeChannelLink("https://youtu.be/vnpY2MEYkCE")
                .addDribbbleLink("user")
                .addLinkedInLink("arleu-cezar-vansuita-júnior-83769271")
                .addEmailLink("skgismos999@gmail.com")
                .addWhatsappLink("Jr", "+0000000000")
                .addSkypeLink("user")
                .addGoogleLink("www.gismostec.com")
                .addAndroidLink("www.gismostec.com")
                .addWebsiteLink("www.gismostec.com")
                .addFiveStarsAction()
                .addMoreFromMeAction("")
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addUpdateAction()
                .setActionsColumnsCount(2)
                .addFeedbackAction("skurtz00420.com")
                .addPrivacyPolicyAction("http://sneezy3d.com:25255/PrivacyPolicies/apps/PsychQ/blog.html")
                .addIntroduceAction((Intent) null)
                .addHelpAction((Intent) null)
                .addChangeLogAction((Intent) null)
                .addRemoveAdsAction(RemoveAdsClickListener) //add link of paid version of app
                 //ad link for donation
                .addDonateAction(DonateClickListener)
                .setWrapScrollView(true)
                .setShowAsCard(true);

        AboutView view = builder.build();

        ViewGroup.LayoutParams layoutParams=new ViewGroup.
                LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(view,layoutParams);


       //mAbout_tv.setText(getString(R.string.about_tv));




    }


    View.OnClickListener DonateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.patreon_url))));

            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.patreon_url))));
            }
        }
    };

    View.OnClickListener RemoveAdsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {

                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.homie.psychq")));

            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=com.homie.psychq")));
            }
        }
    };



}

