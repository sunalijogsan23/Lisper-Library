package org.pjsip.pjsua2.lisper;

import android.app.Application;

public class App extends Application {
    static {
        System.loadLibrary("pjsua2");
        System.out.println("pjsip============================> Library loadedApp");
    }
}
