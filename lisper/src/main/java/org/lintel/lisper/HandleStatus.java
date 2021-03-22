package org.lintel.lisper;

import android.util.Log;

import org.pjsip.pjsua2.OnRegStateParam;
import org.pjsip.pjsua2.pjsip_status_code;

public interface HandleStatus {
    void onRegState(pjsip_status_code code, String reason, int expiration);
}
