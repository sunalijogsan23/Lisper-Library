package org.lintel.lisper;

import android.util.Log;

import org.pjsip.pjsua2.OnRegStateParam;

public interface HandleStatus {
    static void onRegState(OnRegStateParam prm) {
        Log.e("tag","onRegState_1  start");
        Log.e("prm_reg_1",prm.toString());
    }
}
