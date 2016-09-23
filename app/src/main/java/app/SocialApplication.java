package app;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by Jam on 2016/7/29.
 */
public class SocialApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        loadLibs();
        init();
    }
    void init(){
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true);
        CrashHandler.getInstance().init(this);
        context=this;
    }
    void loadLibs(){
    }
    public static Context instance(){
        return context;
    }
}
