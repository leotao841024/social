package service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import build.social.com.social.R;

public class SoundService extends Service {
    public SoundService() {
    }
    private MediaPlayer mp;

    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.test);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean playing = intent.getBooleanExtra("playing", false);
        if (playing) {
            mp.start();
        } else {
            mp.pause();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
