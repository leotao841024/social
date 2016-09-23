package build.social.com.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.chat.EMCallManager;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMLocalSurfaceView;
import com.hyphenate.media.EMOppositeSurfaceView;

import java.util.HashMap;
import java.util.Map;

import cons.Cons;
import helper.BaseAsyncTask;
import helper.CommHelper;
import helper.Door;
import helper.SPHelper;
import service.SoundService;

public class VedioActivity extends Activity  implements View.OnClickListener{
    private Button btn3,btn4,btn5,btn6;
    private LinearLayout covert_left_dao;
    private EditText call_one;
    private String from="";
    private com.hyphenate.media.EMLocalSurfaceView surfaceview01;
    private com.hyphenate.media.EMOppositeSurfaceView surfaceview02;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_vedio);
        initView();
        initEvent();
        initData();
        listnerTalkingState();
    }

    void beginmMusic(){
        intent.putExtra("playing", true);
        startService(intent);
    }

    void stopMusic(){
        intent.putExtra("playing", false);
        startService(intent);
    }

    void initEvent() {
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        covert_left_dao.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(from!=null&&!from.equals("")){
            stopMusic();
        }
        closeVoid();
    }

    void openVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommHelper.OpenSpeaker(VedioActivity.this);
                } catch (Exception ex) {
                    Toast.makeText(VedioActivity.this, "open speaker failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void closeVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommHelper.CloseSpeaker(VedioActivity.this);
                } catch (Exception ex) {
                    Toast.makeText(VedioActivity.this, "colse speaker failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void initData(){
        from = getIntent().getStringExtra("from");
        intent=new Intent(this, SoundService.class);
        if(from!=null&&!from.equals("")){
            beginmMusic();
        }
        openVoid();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn3:
              String name=call_one.getText().toString();
                try {
                    EMClient.getInstance().callManager().makeVideoCall(name);
                } catch (EMServiceNotReadyException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4:
                try {
                    EMClient.getInstance().callManager().answerCall();
                    stopMusic();
                } catch (EMNoActiveCallException e) {
                    Toast.makeText(VedioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn5:
                try {
                    EMClient.getInstance().callManager().endCall();
                    stopMusic();
                } catch (EMNoActiveCallException e) {
                    Toast.makeText(VedioActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn6:
//                btn6.setText("已开");
                btn6.setEnabled(false);
//                Door.open();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        btn6.setEnabled(true);
//                        btn6.setText("开锁");
//                        Door.close();
//                    }
//                },3000);
                setLockStatus();
                break;
            case R.id.covert_left_dao:
                this.finish();
                break;
        }
    }
    void setLockStatus(){
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("name", from);
        new BaseAsyncTask(Cons.RECEVER,maps, BaseAsyncTask.HttpType.Get,"",VedioActivity.this){
            @Override
            public void handler(String param) throws RuntimeException {
                if(param.equals("1")||param.equals("0")){
                    btn6.setText("已开");
                }
                btn6.setEnabled(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn6.setEnabled(true);
                        btn6.setText("开锁");
                    }
                }, 3000);
            }
        }.execute("");
    }

    void listnerTalkingState(){
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
//                        Toast.makeText(VedioActivity.this, "正在连接对方", Toast.LENGTH_LONG).show();
                        setState("正在连接对方");
                        break;
                    case CONNECTED: // 双方已经建立连接
//                        Toast.makeText(VedioActivity.this, "双方已经建立连接", Toast.LENGTH_LONG).show();
                        setState("双方已经建立连接");
                        break;
                    case ACCEPTED: // 电话接通成功
//                        Toast.makeText(VedioActivity.this, "视频接通成功", Toast.LENGTH_LONG).show();
                        setState("视频接通成功");
                        break;
                    case DISCONNNECTED: // 电话断了
//                        Toast.makeText(VedioActivity.this, "视频断了", Toast.LENGTH_LONG).show();
                        setState("视频断了");
                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
//                            Toast.makeText(VedioActivity.this, "网络不稳定", Toast.LENGTH_LONG).show();
                        }else{
                        }
//                        Toast.makeText(VedioActivity.this, "网络不稳定", Toast.LENGTH_LONG).show();
                        setState("网络不稳定");
                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        break;
                    default:
                        break;
                }
            }
        });
    }

    void setState(final String state){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(VedioActivity.this,state, Toast.LENGTH_LONG).show();
            }
        });
    }
    void initView(){
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.btn6);
        surfaceview01= (EMLocalSurfaceView)findViewById(R.id.surfaceview01);
        surfaceview02= (EMOppositeSurfaceView )findViewById(R.id.surfaceview02);
        covert_left_dao= (LinearLayout)findViewById(R.id.covert_left_dao);
        call_one=(EditText)findViewById(R.id.call_one);
        EMCallManager.EMVideoCallHelper callHelper = EMClient.getInstance().callManager().getVideoCallHelper();
//        callHelper.setResolution(1280, 720);
        callHelper.setVideoOrientation(EMCallManager.EMVideoCallHelper.EMVideoOrientation.EMLandscape);
        EMClient.getInstance().callManager().setSurfaceView(surfaceview01, surfaceview02);
    }

}
