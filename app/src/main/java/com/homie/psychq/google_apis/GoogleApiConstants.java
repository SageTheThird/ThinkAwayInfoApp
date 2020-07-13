package com.homie.psychq.google_apis;

import android.app.Application;

import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;

public class GoogleApiConstants {

    Application app = BaseApplication.get();

    public final String CONSTANT_URL_ACCESS_TOKEN = app.getString(R.string.CONSTANT_URL_ACCESS_TOKEN);
    public final String CONSTANT_URL_DEVELOPER_API = app.getString(R.string.CONSTANT_URL_DEVELOPER_API);
    public final String CLIENT_ID = app.getString(R.string.CLIENT_ID);
    public final String CLIENT_SECRET = app.getString(R.string.CLIENT_SECRET);
    public final String GRANT_TYPE_ACCESS_TOKEN = app.getString(R.string.GRANT_TYPE_ACCESS_TOKEN);
    public final String REFRESH_TOKEN = app.getString(R.string.REFRESH_TOKEN);

    /*Might be used in future*/
    public final String GRANT_TYPE_REFRESH_TOKEN = "authorization_code";
    public final String REDIRECT_URI = "http://sneezy3d.com";
    public final String CODE = "4/ugFtBCNif_v8WUiYk1Q8dJqFzitZ7ChGvtQHq6VFGVDCZNNGONkOL3ZvupYxs46mecV22mfnQzvp8nxXlRs71kA";

}
