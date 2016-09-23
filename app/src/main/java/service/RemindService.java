package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import build.social.com.social.SoundActivity;
import build.social.com.social.VedioActivity;

public class RemindService extends Service {
    public RemindService() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        listner();

    }

    void listner(){
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(new CallReceiver(), callFilter);
    }

    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String from = intent.getStringExtra("from");// 拨打方username
            String type = intent.getStringExtra("type");//跳转到通话页面
            Toast.makeText(getBaseContext(), "来电啦" + type, Toast.LENGTH_LONG).show();
            Intent mintent=null;
            if(type.equals("voice")) {
                mintent = new Intent(getBaseContext(), SoundActivity.class);
            }else{
                mintent = new Intent(getBaseContext(), VedioActivity.class);
            }
            mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            mintent.putExtra("state","来电啦....");
            mintent.putExtra("from", from);
            startActivity(mintent);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
