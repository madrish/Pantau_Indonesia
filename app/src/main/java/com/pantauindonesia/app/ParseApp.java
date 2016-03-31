package com.pantauindonesia.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Nurfajariyawan on 11/18/2015.
 */
public class ParseApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Comment.class);
        Parse.initialize(this, "KMGJmVpTaRwA7uqhmU5S1CaUUQQydWHAE97ChY0U", "tcAFacPCuShWRoGhZ0TMJIHMsTpwMQcRWs3wPtJL");
    }
}
