package org.lintel.lisper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AccountSipConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.CallSetting;
import org.pjsip.pjsua2.StringVector;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

public class Lisper implements Handler.Callback {
    public static MyApp app = null;
    public static MyCall currentCall = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;
    public static String lastRegStatus = "";
    private final Handler handler = new Handler(this);

    public class MSG_TYPE {
        public final static int INCOMING_CALL = 1;
        public final static int CALL_STATE = 2;
        public final static int REG_STATE = 3;
        public final static int BUDDY_STATE = 4;
    }

    public static void Account_Regi(String acc_id,String registrar,String username, String password,String proxy){
        accCfg.setIdUri(acc_id);
        accCfg.getRegConfig().setRegistrarUri(registrar);

        accCfg = new AccountConfig();
        accCfg.setIdUri(acc_id);
        accCfg.getNatConfig().setIceEnabled(true);
        account = app.addAcc(accCfg);

        AuthCredInfoVector creds = accCfg.getSipConfig().getAuthCreds();
        creds.clear();
        if (username.length() != 0) {
            creds.add(new AuthCredInfo("Digest", "*", username, 0, password));
            new AccountSipConfig();
        }
        StringVector proxies = accCfg.getSipConfig().getProxies();
        proxies.clear();
        if (proxy.length() != 0) {
            proxies.add(proxy);
        }

        /* Finally */
        lastRegStatus = "";
        try {
            account.modify(accCfg);
        } catch (Exception e) {}
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

    public void notifyIncomingCall(MyCall call) {
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
    }

    @Override
    public boolean handleMessage(@NonNull Message m) {
        if (m.what == 0) {

            app.deinit();
            //finish();
            Runtime.getRuntime().gc();
            android.os.Process.killProcess(android.os.Process.myPid());

        }
        else if (m.what == MSG_TYPE.CALL_STATE) {

            CallInfo ci = (CallInfo) m.obj;

            /* Forward the message to CallActivity */
            if (handler != null) {
                Message m2 = Message.obtain(handler, MSG_TYPE.CALL_STATE, ci);
                m2.sendToTarget();
            }

        }
        else if (m.what == MSG_TYPE.BUDDY_STATE) {

            MyBuddy buddy = (MyBuddy) m.obj;
            int idx = account.buddyList.indexOf(buddy);

            /* Update buddy status text, if buddy is valid and
             * the buddy lists in account and UI are sync-ed.
             */
            /*if (idx >= 0 && account.buddyList.size() == buddyList.size()) {
                buddyList.get(idx).put("status", buddy.getStatusText());
                buddyListAdapter.notifyDataSetChanged();
                // TODO: selection color/mark is gone after this,
                //       dont know how to return it back.
                //buddyListView.setSelection(buddyListSelectedIdx);
                //buddyListView.performItemClick(buddyListView, buddyListSelectedIdx,
                //							   buddyListView.getItemIdAtPosition(buddyListSelectedIdx));

                *//* Return back Call activity *//*
                notifyCallState(currentCall);
            }*/

        }
        else if (m.what == MSG_TYPE.REG_STATE) {

            String msg_str = (String) m.obj;
            lastRegStatus = msg_str;

        }
        else if (m.what == MSG_TYPE.INCOMING_CALL) {

            /* Incoming call */
            final MyCall call = (MyCall) m.obj;
            CallOpParam prm = new CallOpParam();

            /* Only one call at anytime */
            if (currentCall != null) {
				/*
				prm.setStatusCode(pjsip_status_code.PJSIP_SC_BUSY_HERE);
				try {
					call.hangup(prm);
				} catch (Exception e) {}
				*/
                // TODO: set status code
                call.delete();
                return true;
            }

            /* Answer with ringing */
            prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
            try {
                call.answer(prm);
            } catch (Exception e) {}

            currentCall = call;

        }
        else {
            /* Message not handled */
            return false;
        }

        return true;
    }
}
