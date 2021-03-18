package org.pjsip.pjsua2.lisper;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AccountSipConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.CallSetting;
import org.pjsip.pjsua2.pjsip_status_code;

public class Lisper {
    public static MyApp app = null;
    public static MyCall currentCall = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;
    public static String lastRegStatus = "";


    public class MSG_TYPE {
        public final static int INCOMING_CALL = 1;
        public final static int CALL_STATE = 2;
        public final static int REG_STATE = 3;
        public final static int BUDDY_STATE = 4;
    }

    public static void Account_Regi(String acc_id,String registrar,String username, String password, Activity activity){
        System.loadLibrary("pjsua2");
        System.out.println("pjsip============================> Library loadedAccount");

        if (app == null) {
            app = new MyApp();
            /* Wait for GDB to init */
            if ((activity.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {}
            }

            app.init(activity.getFilesDir().getAbsolutePath());
        }

        app = new MyApp();
        accCfg = new AccountConfig();
        accCfg.setIdUri(acc_id);
        accCfg.getRegConfig().setRegistrarUri(registrar);
        accCfg.getNatConfig().setIceEnabled(true);


        AuthCredInfoVector creds = accCfg.getSipConfig().getAuthCreds();
        creds.clear();
        if (username.length() != 0) {
            creds.add(new AuthCredInfo("Digest", "*", username, 0, password));
        }
        account = app.addAcc(accCfg);
        /*try {
            account.create(accCfg);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*StringVector proxies = accCfg.getSipConfig().getProxies();
        proxies.clear();
        if (proxy.length() != 0) {
            proxies.add(proxy);
        }*/

        /* Finally */
        lastRegStatus = "";
        /*try {
            account.modify(accCfg);
        } catch (Exception e) {}*/
    }

    public static void MakeCall(String uri) {

        if (currentCall != null) {
            return;
        }

        String buddy_uri = uri;

        Log.e("TAG","buddy_uri---->" + buddy_uri);
        MyCall call = new MyCall(account, -1);
        CallOpParam prm = new CallOpParam();
        CallSetting opt = prm.getOpt();
        opt.setAudioCount(1);
        opt.setVideoCount(0);

        try {
            call.makeCall(buddy_uri, prm);
        } catch (Exception e) {
            call.delete();
            return;
        }

        currentCall = call;
    }

    public static void acceptCall() {
        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
        try {
            currentCall.answer(prm);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void hangupCall() {

        if (currentCall != null) {
            CallOpParam prm = new CallOpParam();
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
            try {
                currentCall.hangup(prm);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void getAccInfo(){
        String info = accCfg.getIdUri() + ", " + accCfg.getRegConfig().getRegistrarUri();
        Log.e("Info",info);
    }

    /*public void notifyIncomingCall(MyCall call) {
        Message m = Message.obtain(handler, MSG_TYPE.INCOMING_CALL, call);
        m.sendToTarget();
    }

    public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
        Log.e("tag","code---->" + code);
        String msg_str = "";
        if (expiration == 0)
            msg_str += "Unregistration";
        else
            msg_str += "Registration";

        if (code.swigValue()/100 == 2)
            msg_str += " successful";
        else
            msg_str += " failed: " + reason;

        Message m = Message.obtain(handler, MSG_TYPE.REG_STATE, msg_str);
        m.sendToTarget();
    }

    public void notifyCallState(MyCall call) {
        if (currentCall == null || call.getId() != currentCall.getId())
            return;

        CallInfo ci;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
            ci = null;
        }
        Message m = Message.obtain(handler, MSG_TYPE.CALL_STATE, ci);
        m.sendToTarget();

        if (ci != null &&
                ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
        {
            currentCall = null;
        }
    }*/

}
