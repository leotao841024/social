package build.social.com.social;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hurray.plugins.RootCmd;
import com.hurray.plugins.rkctrl;
import com.hurray.plugins.serial;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cons.Cons;
import fragments.HomeFragment;
import helper.BaseAsyncTask;
import helper.Door;
import helper.SPHelper;
import service.RemindService;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private HomeFragment homeFragment;
    private RadioGroup rg_home;
    private Timer timer;
   private RadioButton  main_tab_home,main_tab_mine,main_tab_talk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
//        listner();
        initDataEvent();
    }

    void initView(){
        rg_home = (RadioGroup) findViewById(R.id.rg_home);
        rg_home.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_home:
                        loadFragment(homeFragment, R.id.content_layout, false);
                        break;
                    case R.id.main_tab_mine:
//                        loadFragment(mineFragment, R.id.content_layout,false);
                        break;
                    case R.id.main_tab_talk:
//                        loadFragment(talkFragment, R.id.content_layout,false);
                        break;
                    default:
                        break;
                }
            }
        });

        main_tab_home = (RadioButton) findViewById(R.id.main_tab_home);
        main_tab_mine = (RadioButton) findViewById(R.id.main_tab_mine);
        main_tab_talk = (RadioButton) findViewById(R.id.main_tab_talk);
        homeFragment= new HomeFragment();

//        homeFragment = (HomeFragment) HomeFragment.newInstance("home");
//        mineFragment = (MineFragment) MineFragment.newInstance("mine");
//        mineFragment = new  MineFragment();
//        talkFragment = new TalkFramgment();

    }
    void initData(){
        timer=new Timer();
        timer.schedule(task,1000,1000);
        Intent intent=new Intent(this, RemindService.class);
        startService(intent);
        bindService(intent, myconn, Context.BIND_AUTO_CREATE);
    }
    
    ServiceConnection myconn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    void initEvent(){
        loadFragment(homeFragment, R.id.content_layout, false);

    }
    private FragmentManager manager = getSupportFragmentManager();
    public void loadFragment(Fragment fragment, int replaceView,boolean isfirst) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(replaceView, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.covert_right_dao:
//                Intent sound_intent = new Intent(MainActivity.this,SoundActivity.class);
//                startActivity(sound_intent);
//                break;
        }
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
            Toast.makeText(MainActivity.this, "来电啦"+type, Toast.LENGTH_LONG).show();
            Intent mintent=null;
            if(type.equals("voice")) {
                mintent = new Intent(MainActivity.this, SoundActivity.class);
            }else{
                mintent = new Intent(MainActivity.this, VedioActivity.class);
            }
            mintent.putExtra("state","来电啦....");
            mintent.putExtra("from",from);
            startActivity(mintent);
        }

    }
    private  long exitTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//              EMClient.getInstance().logout(true);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public	boolean status=false;

//    public rkctrl m_rkctrl = new rkctrl();
    public serial pSerialport = new serial();
    public String   arg = "/dev/ttyS1,9600,N,1,8";

    private Thread pthread =null;

    public MyHandler myHandler = new MyHandler();

    public RootCmd rootCmd = new RootCmd();


    int  iRead = 0;
    String strRfid = "";
    String strGpiostatus = "";

    void initDataEvent(){

        initSerial();
        initCd4051();
    }
    private void initCd4051(){
        String strCmd2 = "chmod 777 /dev/io_control_miscdev";
        rootCmd.execRootCmdSilent(strCmd2);
        runReadCd4051();
    }

    private void initSerial()
    {
        int iret=pSerialport.open(arg);
        if(iret>0){
            iRead=iret;
//            log(String.format("打开串口成功 (port = %s,fd=%d)", arg,iret));

            runReadSerial(iRead);
        }else{
//            log(String.format("打开串口失败 (fd=%d)", iret));
        }
    }

    public void runReadCd4051(){
        Runnable run=new Runnable() {
            public void run() {
                while(true){

                    //延迟读取
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for(int i=0; i<8 ;i++)
                    {
                        int r = Door.get_adc_status(i);
                        String msg = String.format("GPIO输入口%d ,获取值为%d", i, r);
                        Message msgpwd=new Message();
                        msgpwd.what=2;
                        Bundle data=new Bundle();
                        data.putString("data",msg);
                        msgpwd.setData(data);
                        myHandler.sendMessage(msgpwd);
                    }
                }
            }
        };
        pthread=new Thread(run);
        pthread.start();
    }



    public void runReadSerial(final int fd){
        Runnable run=new Runnable() {
            public void run() {
                while(true){
                    int r = pSerialport.select(fd, 0, 100);
                    if(r == 1)
                    {
                        //测试 普通读串口数据
                        byte[] buf = new byte[50];
                        buf = pSerialport.read(fd,20,1000);
                        String str = "";

                        if(buf == null) break;

                        if(buf.length <= 0) break;

                        str = byte2HexString(buf);

                        Message msgpwd=new Message();
                        msgpwd.what=1;
                        Bundle data=new Bundle();
                        data.putString("data",str);
                        msgpwd.setData(data);
                        myHandler.sendMessage(msgpwd);

                    }
                }
                onThreadEnd();
            }
        };
        pthread=new Thread(run);
        pthread.start();
    }

    public class MyHandler extends Handler {
        public MyHandler() {
        }

        public MyHandler(Looper L) {
            super(L);
        }

        // 子类必须重写此方法,接受数据
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String strData = "";
            // 此处可以更新UI
            switch (msg.what) {
                case 1:
                    strData = msg.getData().getString("data");
                    break;
                case 2:
                    strData = msg.getData().getString("data");
                    break;
            }
            if(strData.equals("02")) {
//                Toast.makeText(MainActivity.this, "收到刷卡信息", Toast.LENGTH_LONG).show();
//                Door.open();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Door.close();
//                    }
//                }, 3000);
            }
//            }else{
//                Toast.makeText(MainActivity.this,"收到刷卡信息:"+strData,Toast.LENGTH_LONG).show();
//            }
        }
    }


    public void onThreadEnd(){
        this.runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }

    /**
     * byte[]转换成字符串
     * @param b
     * @return
     */
    public static String byte2HexString(byte[] b)
    {
        StringBuffer sb = new StringBuffer();
        int length = b.length;
        for (int i = 0; i < b.length; i++) {
            String stmp = Integer.toHexString(b[i]&0xff);
            if(stmp.length() == 1)
                sb.append("0"+stmp);
            else
                sb.append(stmp);
        }
        return sb.toString();
    }

    TimerTask task=new TimerTask() {
        @Override
        public void run() {
            getLockStatus();
        }
    };

    void getLockStatus(){
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("name", SPHelper.getBaseMsg(MainActivity.this,"mid","0"));
        new BaseAsyncTask(Cons.CALLER,maps, BaseAsyncTask.HttpType.Get,"",MainActivity.this){
            @Override
            public void handler(String param) throws RuntimeException {
                if(param.equals("1")){
                    openDoor();
                }
            }
        }.execute("");
    }

    void openDoor(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"开锁",Toast.LENGTH_LONG).show();
                Door.open();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Door.close();
                    }
                },3000);
            }
        });
    }

    @Override
    protected void onDestroy() {
//        EMClient.getInstance().logout(true);
        super.onDestroy();
    }
}
