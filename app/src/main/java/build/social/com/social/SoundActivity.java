package build.social.com.social;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;

import java.util.HashMap;
import java.util.Map;

import cons.Cons;
import helper.BaseAsyncTask;
import helper.CommHelper;
import helper.Door;
import service.SoundService;

public class SoundActivity extends Activity  implements View.OnClickListener{
    private Button btn3,btn4,btn5,btn6;
    private LinearLayout covert_left_dao;
    private EditText call_one;
    private TextView txt_state;
    private String from="";
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sound);
        initView();
        initData();
        initEvent();
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
    void initData(){
        String lststate= getIntent().getStringExtra("state");
        from = getIntent().getStringExtra("from");
        if(lststate!=null){
            txt_state.setText(lststate);
        }
        intent=new Intent(this, SoundService.class);
        if(from!=null&&!from.equals("")){
            beginmMusic();
        }
        openVoid();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(from!=null&&!from.equals("")){
            stopMusic();
        }
        closeVoid();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn3:
                String name = call_one.getText().toString();
                try {
                    EMClient.getInstance().callManager().makeVoiceCall(name);
                } catch (EMServiceNotReadyException e) {
                    txt_state.setText("服务异常");
                }
                break;
            case R.id.btn4:
                try {
                    EMClient.getInstance().callManager().answerCall();
                    stopMusic();
                } catch (EMNoActiveCallException e) {
                    Toast.makeText(SoundActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn5:
                try {
                    EMClient.getInstance().callManager().endCall();
                    stopMusic();
                } catch (EMNoActiveCallException e) {
                    Toast.makeText(SoundActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn6:
                if (from != null && !from.equals("")){
                    btn6.setEnabled(false);
                    setLockStatus();
                }
//                    btn6.setText("已开");
//                    btn6.setEnabled(false);
//                    Door.open();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn6.setEnabled(true);
//                            btn6.setText("开锁");
//                            Door.close();
//                        }
//                    },3000);
                break;
            case R.id.covert_left_dao:
                this.finish();
                break;
        }
    }

    void listnerTalkingState(){
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
//                        Toast.makeText(SoundActivity.this, "正在连接对方", Toast.LENGTH_LONG).show();
                        setState("正在连接对方");
                        break;
                    case CONNECTED: // 双方已经建立连接
                        setState("双方已经建立连接");
                        break;
                    case ACCEPTED: // 电话接通成功
//                        Toast.makeText(SoundActivity.this, "电话接通成功", Toast.LENGTH_LONG).show();
                        setState("电话接通成功");

                        break;
                    case DISCONNNECTED: // 电话断了
//                        Toast.makeText(SoundActivity.this, "电话断了", Toast.LENGTH_LONG).show();
                        setState("电话断了");
                        break;
                    case NETWORK_UNSTABLE:
                        if(error == CallError.ERROR_NO_DATA){
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL:

                        break;
                    default:
                        break;
                }
            }
        });
    }

    void setLockStatus(){
        Map<String,Object> maps=new HashMap<String,Object>();
        maps.put("name", from);
        new BaseAsyncTask(Cons.RECEVER,maps, BaseAsyncTask.HttpType.Get,"",SoundActivity.this){
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
    void openVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommHelper.OpenSpeaker(SoundActivity.this);
                }catch (Exception ex){
                    Toast.makeText(SoundActivity.this,"open speaker failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void closeVoid(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    CommHelper.CloseSpeaker(SoundActivity.this);
                }catch (Exception ex){
                 Toast.makeText(SoundActivity.this,"colse speaker failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void setState(final String state){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txt_state.setText(state);
            }
        });
    }



    void initView(){
        btn3=(Button)findViewById(R.id.btn3);
        btn4=(Button)findViewById(R.id.btn4);
        btn5=(Button)findViewById(R.id.btn5);
        btn6=(Button)findViewById(R.id.btn6);
        txt_state=(TextView)findViewById(R.id.txt_state);
        covert_left_dao=(LinearLayout)findViewById(R.id.covert_left_dao);
        call_one=(EditText)findViewById(R.id.call_one);
    }
}
