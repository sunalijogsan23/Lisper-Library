package org.lintel.lisper;

import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallMediaInfo;
import org.pjsip.pjsua2.CallMediaInfoVector;
import org.pjsip.pjsua2.Media;
import org.pjsip.pjsua2.OnCallMediaStateParam;
import org.pjsip.pjsua2.OnCallStateParam;
import org.pjsip.pjsua2.pjmedia_type;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsua_call_media_status;

import static org.lintel.lisper.Lisper.activity_run;
import static org.lintel.lisper.Lisper.currentCall;

public class LisperCall extends Call {
    LisperCall(LisperAccount acc, int call_id) {
        super(acc, call_id);
    }

    @Override
    public void onCallState(OnCallStateParam prm) {
        //MyLisper.observer.notifyCallState(this);
        try {
            CallInfo ci = getInfo();
            if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_CONFIRMED) {
               // buttonHangup.setText("Hangup");//已接听
                activity_run.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LisperAccount.sPhoneCallback.callConnected();
                    }
                });

            }else if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
                this.delete();
                activity_run.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LisperAccount.sPhoneCallback.callEnd();
                    }
                });
            }
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm) {
        CallInfo ci;
        try {
            ci = getInfo();
        } catch (Exception e) {
            return;
        }

        CallMediaInfoVector cmiv = ci.getMedia();

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD))
            {
                // unfortunately, on Java too, the returned Media cannot be downcasted to AudioMedia
                Media m = getMedia(i);
                AudioMedia am = AudioMedia.typecastFromMedia(m);

                // connect ports
                try {
                    MyLisper.ep.audDevManager().getCaptureDevMedia().startTransmit(am);
                    am.startTransmit(MyLisper.ep.audDevManager().getPlaybackDevMedia());
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }
}
