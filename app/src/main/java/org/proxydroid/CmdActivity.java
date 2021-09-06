package org.proxydroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import org.proxydroid.utils.Utils;

public class CmdActivity extends AppCompatActivity {

    private static final String TAG = "SocksDroidCmd:";

    Profile profile = new Profile();

    private String getParam(Intent intent, String key) {
        String value = intent.getStringExtra(key);
        return value != null ? value : "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String info = "";
        Intent intent = this.getIntent();

        String act = getParam(intent, "act");
        if (act.equals("start")) {
            String ip = getParam(intent, "ip");
            int port = Integer.parseInt(getParam(intent, "port"));
            int vpntype = Integer.parseInt(getParam(intent, "vpntype"));
            String uname = getParam(intent, "un");
            String pwd = getParam(intent, "pwd");

            profile.setHost(ip);
            profile.setPort(port);
            if (vpntype == 1) { // 911
                profile.setProxyType("socks5");
                profile.setAuth(false);
                profile.setUser("");
                profile.setPassword("");
            } else if (vpntype == 2) {
                profile.setProxyType("http");
                profile.setAuth(false);
                profile.setUser("");
                profile.setPassword("");
            } else if (vpntype == 3) {
                profile.setProxyType("http");
                profile.setAuth(true);
                profile.setUser(uname);
                profile.setPassword(pwd);
            }
            profile.setBypassAddrs("127.0.0.1|10.0.0.0/8|192.168.0.0/16|172.16.0.0/12");
            profile.setAutoSetProxy(true);
        }
        Intent intent1 = new Intent(this, ProxyDroid.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Profile", profile);
        bundle.putString("action", act);
        intent1.putExtras(bundle);
        startActivity(intent1);
        finish();
    }

}
