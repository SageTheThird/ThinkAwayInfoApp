package com.homie.psychq.main.api.main;

import com.homie.psychq.BaseApplication;
import com.homie.psychq.R;

/*Server Info*/
public class Constants {

    public  final String BASE_URL= BaseApplication.get().getString(R.string.server_location);
    public final String AUTHORIZATION = BaseApplication.get().getString(R.string.server_creds);


    //    public  final String BASE_URL="http://sneezy3d.com:25256/";

}
