package org.lintel.lisper;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.pjsip.pjsua2.Account;
import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AccountInfo;
import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.BuddyConfig;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallMediaInfo;
import org.pjsip.pjsua2.CallMediaInfoVector;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.CallSetting;
import org.pjsip.pjsua2.ContainerNode;
import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.EpConfig;
import org.pjsip.pjsua2.JsonDocument;
import org.pjsip.pjsua2.LogConfig;
import org.pjsip.pjsua2.LogEntry;
import org.pjsip.pjsua2.LogWriter;
import org.pjsip.pjsua2.Media;
import org.pjsip.pjsua2.OnCallMediaStateParam;
import org.pjsip.pjsua2.OnCallStateParam;
import org.pjsip.pjsua2.OnIncomingCallParam;
import org.pjsip.pjsua2.OnInstantMessageParam;
import org.pjsip.pjsua2.OnRegStateParam;
import org.pjsip.pjsua2.StringVector;
import org.pjsip.pjsua2.TransportConfig;
import org.pjsip.pjsua2.UaConfig;
import org.pjsip.pjsua2.pj_log_decoration;
import org.pjsip.pjsua2.pjmedia_type;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;
import org.pjsip.pjsua2.pjsip_transport_type_e;
import org.pjsip.pjsua2.pjsua2JNI;
import org.pjsip.pjsua2.pjsua_call_media_status;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.lintel.lisper.Lisper.activity_run;
import static org.lintel.lisper.Lisper.currentCall;

interface LisperObserver {
    public void notifyRegState(pjsip_status_code code, String reason, int expiration);
    public void notifyIncomingCall(LisperCall call);
    public void notifyCallState(LisperCall call);
}

public class Lisper{
    public static MyLisper app = null;
    public static LisperCall currentCall = null;
    public static LisperAccount account = null;
    public static AccountConfig accCfg = null;
    public static String sip_port = "";
    public static Context context;
    public static Activity activity_run;
    public static boolean LISPER_SC_OK = false;

    public static void InitLisper(Activity activity,String port){
        System.loadLibrary("pjsua2");
        System.out.println("pjsip============================> Library loadedAccount");

        sip_port = port;
        if (app == null) {
            app = new MyLisper();
            /* Wait for GDB to init */
            if ((activity.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}
            }
            app.init(activity.getFilesDir().getAbsolutePath());
        }

    }

    public static void Account_Regi(String username, String password,String server_url,Activity activity){
        context = activity.getApplicationContext();
        activity_run= activity;

        RequestQueue mRequestQueue;
        StringRequest mStringRequest;

        mRequestQueue = Volley.newRequestQueue(context);
        mStringRequest = new StringRequest(Request.Method.POST,"https://lisper.lintel.in/auth_api/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response:",response);
                /*app = new MyLisper();
                accCfg = new AccountConfig();
                accCfg.setIdUri("sip:" + username + "@" + server_url);
                accCfg.getRegConfig().setRegistrarUri("sip:"+server_url);
                accCfg.getNatConfig().setIceEnabled(true);

                AuthCredInfoVector creds = accCfg.getSipConfig().getAuthCreds();
                creds.clear();
                if (username.length() != 0) {
                    creds.add(new AuthCredInfo("Digest", "*", username, 0, password));
                }
                account = new LisperAccount(accCfg);
                account = app.addAcc(accCfg);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error:",error.toString());
            }
        }){
            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                String str = "{\"username\":\""+username+"\",\"app-uuid\":\""+""+"\",\"server\":\""+"lintel.in"+"\",\"app_version\":\""+"1.2"+"\"}";
                return str.getBytes();
            };

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization","Token 099a9bd6391a48549029a3f27ad206b1b34dcbc3d505403d8737ff09d9ebef9cfd34d53c70544b29a89b9b7b622d9d0c");
                return params;
            }
        };
        mRequestQueue.add(mStringRequest);
    }

    public static void MakeCall(String uri) {
        //account = app.accList.get(0);
        Log.e("TAG","buddy_uri---->" + uri);
        Log.e("TAG","acc_list---->" + app.accList.get(0).toString());
        LisperCall call = new LisperCall(account, -1);
        CallOpParam prm = new CallOpParam();
        CallSetting opt = prm.getOpt();
        opt.setAudioCount(1);
        opt.setVideoCount(0);

        try {
            call.makeCall(uri, prm);
            activity_run.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LisperAccount.sPhoneCallback.outgoingInit();
                }
            });
        } catch (Exception e) {
            call.delete();
            return;
        }

        currentCall = call;

    }

    public static void acceptCall(LisperCall call) {
        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_OK);
        try {
            call.answer(prm);
            activity_run.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LisperAccount.sPhoneCallback.callConnected();
                }
            });
        } catch (Exception e) {
            System.out.println("answercall"+e);
        }
        currentCall = call;
    }

    public static void hangupCall(LisperCall call) {

        if(currentCall != null){
            Log.e("cureentcall", String.valueOf(currentCall));
        }else {
            Log.e("cureentcall", "null");
        }
        CallOpParam prm = new CallOpParam();
        prm.setStatusCode(pjsip_status_code.PJSIP_SC_DECLINE);
        try {
            call.hangup(prm);
            activity_run.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LisperAccount.sPhoneCallback.callEnd();
                }
            });
        } catch (Exception e) {
            System.out.println("hangupcall"+e);
        }
    }

    public static void getAccInfo(){
        String info = accCfg.getIdUri() + ", " + accCfg.getRegConfig().getRegistrarUri();
        Log.e("Info",info);
    }

    public static void dinit(){
        app.deinit();
        Runtime.getRuntime().gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public static void addCallback(RegistrationCallback registrationCallback,
                                   PhoneCallback phoneCallback) {
        LisperAccount.addRegistrationCallback(registrationCallback);
        LisperAccount.addPhoneCallback(phoneCallback);
    }
}

class LisperAccount extends Account {
    public AccountConfig cfg;
    public static PhoneCallback sPhoneCallback;
    public static RegistrationCallback sRegistrationCallback;

    public static void addRegistrationCallback(RegistrationCallback registrationCallback) {
        sRegistrationCallback = registrationCallback;
    }

    public static void addPhoneCallback(PhoneCallback phoneCallback) {
        sPhoneCallback = phoneCallback;
    }

    public static void removePhoneCallback() {
        if (sPhoneCallback != null) {
            sPhoneCallback = null;
        }
    }

    public static void removeRegistrationCallback() {
        if (sRegistrationCallback != null) {
            sRegistrationCallback = null;
        }
    }

    public void removeAllCallback() {
        removePhoneCallback();
        removeRegistrationCallback();
    }

    LisperAccount(AccountConfig config) {
        super();
        cfg = config;
    }

    @Override
    public void onRegState(OnRegStateParam prm) {
        Log.e("tag","onRegState  start");
        Log.e("prm_reg",prm.toString());
        //handleStatuss.onRegState(prm.getCode(), prm.getReason(), prm.getExpiration());
        MyLisper.observer.notifyRegState(prm.getCode(), prm.getReason(), prm.getExpiration());

        String state = prm.getCode().toString();
        if (state.equals("PJSIP_SC_OK")) {
            activity_run.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sRegistrationCallback.registrationOk();
                }
            });
        } else{
            activity_run.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sRegistrationCallback.registrationFailed();
                }
            });
        }
    }

    @Override
    public void onIncomingCall(OnIncomingCallParam prm) {
        System.out.println("======== Incoming call ======== ");
        LisperCall call = new LisperCall(this, prm.getCallId());
        try {
            CallSetting setting = call.getInfo().getSetting();
            Log.d(" Log APP ", "onIncomingCall: Audio " + setting.getAudioCount() + "  Video" + setting.getVideoCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        activity_run.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sPhoneCallback.incomingCall(call);
            }
        });
        MyLisper.observer.notifyIncomingCall(call);
    }

    @Override
    public void onInstantMessage(OnInstantMessageParam prm) {
        System.out.println("======== Incoming pager ======== ");
        System.out.println("From 		: " + prm.getFromUri());
        System.out.println("To			: " + prm.getToUri());
        System.out.println("Contact		: " + prm.getContactUri());
        System.out.println("Mimetype	: " + prm.getContentType());
        System.out.println("Body		: " + prm.getMsgBody());
    }

}

class MyLogWriter extends LogWriter {
    @Override
    public void write(LogEntry entry) {
        System.out.println(entry.getMsg());
    }
}

class MyLisper {
    static {
        System.loadLibrary("pjsua2");
        System.out.println("pjsip============================> Library loaded");
    }

    public static Endpoint ep = new Endpoint();
    public static LisperObserver observer;
    public ArrayList<LisperAccount> accList = new ArrayList<LisperAccount>();

    private ArrayList<LisperAccountConfig> accCfgs = new ArrayList<LisperAccountConfig>();
    private EpConfig epConfig = new EpConfig();
    private TransportConfig sipTpConfig = new TransportConfig();
    private String appDir;

    /* Maintain reference to log writer to avoid premature cleanup by GC */
    private MyLogWriter logWriter;

    private final String configName = "pjsua2.json";
    int SIP_PORT  = 5060;
    private final int LOG_LEVEL = 4;

    public void init(String app_dir) {
        init(app_dir, false);
    }

    public void init(String app_dir, boolean own_worker_thread) {
        ///observer = obs;
        appDir = app_dir;
        if(!Lisper.sip_port.equals("")){
            SIP_PORT = Integer.parseInt(Lisper.sip_port);
        }

        /* Create endpoint */
        try {
            ep.libCreate();
        } catch (Exception e) {
            return;
        }

        /* Load config */
        String configPath = appDir + "/" + configName;
        File f = new File(configPath);
        if (f.exists()) {
            loadConfig(configPath);
        } else {
            /* Set 'default' values */
            sipTpConfig.setPort(SIP_PORT);
        }

        /* Override log level setting */
        epConfig.getLogConfig().setLevel(LOG_LEVEL);
        epConfig.getLogConfig().setConsoleLevel(LOG_LEVEL);

        /* Set log config. */
        LogConfig log_cfg = epConfig.getLogConfig();
        logWriter = new MyLogWriter();
        log_cfg.setWriter(logWriter);
        log_cfg.setDecor(log_cfg.getDecor() &
                ~(pj_log_decoration.PJ_LOG_HAS_CR.swigValue() |
                        pj_log_decoration.PJ_LOG_HAS_NEWLINE.swigValue()));

        /* Set ua config. */
        UaConfig ua_cfg = epConfig.getUaConfig();
        ua_cfg.setUserAgent("Lisper Android " + ep.libVersion().getFull());
        StringVector stun_servers = new StringVector();
        stun_servers.add("stun.pjsip.org");
        ua_cfg.setStunServer(stun_servers);
        if (own_worker_thread) {
            ua_cfg.setThreadCnt(0);
            ua_cfg.setMainThreadOnly(true);
        }

        /* Init endpoint */
        try {
            ep.libInit(epConfig);
        } catch (Exception e) {
            return;
        }

        /* Create transports. */
        try {
            ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP, sipTpConfig);
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            ep.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_TCP, sipTpConfig);
        } catch (Exception e) {
            System.out.println(e);
        }

        observer = new LisperObserver() {
            @Override
            public void notifyRegState(pjsip_status_code code, String reason, int expiration) {
                Log.e("tag","code---->" + code);
                String msg_str = "";
                if (expiration == 0){
                    msg_str += "Unregistration";
                    Lisper.LISPER_SC_OK = false;
                }
                else{
                    msg_str += "Registration";
                    Lisper.LISPER_SC_OK = true;
                }

                if (code.swigValue()/100 == 2){
                    msg_str += " successful";
                    Lisper.LISPER_SC_OK = true;
                }
                else{
                    msg_str += " failed: " + reason;
                    Lisper.LISPER_SC_OK = false;
                }
            }

            @Override
            public void notifyIncomingCall(LisperCall call) {

            }

            @Override
            public void notifyCallState(LisperCall call) {

            }

        };
        /* Create accounts. */
		for (int i = 0; i < accCfgs.size(); i++) {
			LisperAccountConfig my_cfg = accCfgs.get(i);
			LisperAccount acc = addAcc(my_cfg.accCfg);
			if (acc == null)
				continue;

			 //Add Buddies
			/*for (int j = 0; j < my_cfg.buddyCfgs.size(); j++) {
				BuddyConfig bud_cfg = my_cfg.buddyCfgs.get(j);
				acc.addBuddy(bud_cfg);
			}*/
		}

        /* Start. */
        try {
            ep.libStart();
        } catch (Exception e) {
            return;
        }
    }

    public LisperAccount addAcc(AccountConfig cfg) {
        LisperAccount acc = new LisperAccount(cfg);
        try {
            acc.create(cfg);
        } catch (Exception e) {
            acc = null;
            return null;
        }

        accList.add(acc);
        String configPath = appDir + "/" + configName;
        saveConfig(configPath);
        return acc;
    }

    public void delAcc(LisperAccount acc) {
        accList.remove(acc);
    }

    private void loadConfig(String filename) {
        JsonDocument json = new JsonDocument();

        try {
            /* Load file */
            json.loadFile(filename);
            ContainerNode root = json.getRootContainer();

            /* Read endpoint config */
            epConfig.readObject(root);

            /* Read transport config */
            ContainerNode tp_node = root.readContainer("SipTransport");
            sipTpConfig.readObject(tp_node);

            /* Read account configs */
            accCfgs.clear();
            ContainerNode accs_node = root.readArray("accounts");
            while (accs_node.hasUnread()) {
                LisperAccountConfig acc_cfg = new LisperAccountConfig();
                acc_cfg.readObject(accs_node);
                accCfgs.add(acc_cfg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        /* Force delete json now, as I found that Java somehow destroys it
         * after lib has been destroyed and from non-registered thread.
         */
        json.delete();
    }

    private void saveConfig(String filename) {
        JsonDocument json = new JsonDocument();

        try {
            /* Write endpoint config */
            json.writeObject(epConfig);

            /* Write transport config */
            ContainerNode tp_node = json.writeNewContainer("SipTransport");
            sipTpConfig.writeObject(tp_node);

            /* Write account configs */
          //  buildAccConfigs();
            ContainerNode accs_node = json.writeNewArray("accounts");
            for (int i = 0; i < accCfgs.size(); i++) {
                accCfgs.get(i).writeObject(accs_node);
            }

            /* Save file */
            json.saveFile(filename);
        } catch (Exception e) {}

        /* Force delete json now, as I found that Java somehow destroys it
         * after lib has been destroyed and from non-registered thread.
         */
        json.delete();
    }

    public void deinit() {
        String configPath = appDir + "/" + configName;
        saveConfig(configPath);

        /* Try force GC to avoid late destroy of PJ objects as they should be
         * deleted before lib is destroyed.
         */
        Runtime.getRuntime().gc();

        /* Shutdown pjsua. Note that Endpoint destructor will also invoke
         * libDestroy(), so this will be a test of double libDestroy().
         */
        try {
            ep.libDestroy();
        } catch (Exception e) {}

        /* Force delete Endpoint here, to avoid deletion from a non-
         * registered thread (by GC?).
         */
        ep.delete();
        ep = null;
    }
}

class LisperAccountConfig {
    public AccountConfig accCfg = new AccountConfig();
    public ArrayList<BuddyConfig> buddyCfgs = new ArrayList<BuddyConfig>();

    public void readObject(ContainerNode node) {
        try {
            ContainerNode acc_node = node.readContainer("Account");
            accCfg.readObject(acc_node);
            ContainerNode buddies_node = acc_node.readArray("buddies");
            buddyCfgs.clear();
            while (buddies_node.hasUnread()) {
                BuddyConfig bud_cfg = new BuddyConfig();
                bud_cfg.readObject(buddies_node);
                buddyCfgs.add(bud_cfg);
            }
        } catch (Exception e) {}
    }

    public void writeObject(ContainerNode node) {
        try {
            ContainerNode acc_node = node.writeNewContainer("Account");
            accCfg.writeObject(acc_node);
            ContainerNode buddies_node = acc_node.writeNewArray("buddies");
            for (int j = 0; j < buddyCfgs.size(); j++) {
                buddyCfgs.get(j).writeObject(buddies_node);
            }
        } catch (Exception e) {}
    }
}
