package build.social.com.social;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.VideoRatio;

import java.io.PrintWriter;

import helper.CommHelper;
import helper.Door;

public class PhoneActivity extends Activity implements View.OnClickListener {
    private LinearLayout covert_left_dao;
    private EditText call_one;
    private Button btn_call,btn_stop;
    private TextView txt_state;
    private String mCurrentCallId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_phone);
        initView();
        initEvent();
        openVoid();
    }

    void openVoid(){
         try {
             CommHelper.OpenSpeaker(PhoneActivity.this);
         } catch (Exception ex) {
             Toast.makeText(PhoneActivity.this, "open speaker failed", Toast.LENGTH_SHORT).show();
         }
    }

    void closeVoid(){
        try {
            CommHelper.CloseSpeaker(PhoneActivity.this);
        } catch (Exception ex) {
            Toast.makeText(PhoneActivity.this, "colse speaker failed", Toast.LENGTH_SHORT).show();
        }
    }

    void initView(){
        covert_left_dao =(LinearLayout) findViewById(R.id.covert_left_dao);
        call_one =(EditText) findViewById(R.id.call_one);
        btn_call =(Button) findViewById(R.id.btn_call);
        btn_stop =(Button) findViewById(R.id.btn_stop);
        txt_state=(TextView)findViewById(R.id.txt_state);
    }

    void initEvent(){
        covert_left_dao.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_call.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        if(!ECDevice.isInitialized()) {
            ECDevice.initial(this, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {
                    loadPhoneInitMsg();
                }
                @Override
                public void onError(Exception exception) {
                }
            });
        }
        super.onResume();
    }

    void loadPhoneInitMsg(){
        ECInitParams params = ECInitParams.createParams();
        params.setUserid("13621279065");
        params.setAppKey("8a216da85635b77e015646b66e34091f");
        params.setToken("1366f1fc7c9ab4ef142f6994144472ca");
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
        ECVoIPCallManager callInterface = ECDevice.getECVoIPCallManager();
        if(callInterface != null) {
            callInterface.setOnVoIPCallListener(new ECVoIPCallManager.OnVoIPListener() {
                @Override
                public void onVideoRatioChanged(VideoRatio videoRatio) {

                }
                @Override
                public void onSwitchCallMediaTypeRequest(String s, ECVoIPCallManager.CallType callType) {

                }

                @Override
                public void onSwitchCallMediaTypeResponse(String s, ECVoIPCallManager.CallType callType) {

                }
                @Override
                public void onDtmfReceived(String s, char c) {
                    setState(s+"||"+c);
                    final String str=s;
                    final char cyr=c;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             Toast.makeText(PhoneActivity.this,"成功:"+str + "||" + cyr, Toast.LENGTH_LONG).show();
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
                public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
                    if(voipCall == null) {
                        Log.e("SDKCoreHelper", "handle call event error , voipCall null");
                        return ;
                    }
                    ECVoIPCallManager.ECCallState callState = voipCall.callState;
                    switch (callState) {
                        case ECCALL_PROCEEDING:
                            setState("正在连接服务器");
                            break;
                        case ECCALL_ALERTING:
                            setState("对方正在振铃");
                            break;
                        case ECCALL_ANSWERED:
                            setState("对方接听本次呼叫");
                            break;
                        case ECCALL_FAILED:
                            setState("本次呼叫失败"+voipCall.reason);
                            break;
                        case ECCALL_RELEASED:
                            finished();
                            break;
                        default:
                            Log.e("SDKCoreHelper", "handle call event error , callState " + callState);
                            break;
                    }
                }
            });
        }
        if(params.validate()) {
            // 判断注册参数是否正确
            ECDevice.login(params);
        }
    }
    void finished(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PhoneActivity.this,"通话结束",Toast.LENGTH_LONG).show();
                finish();
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
    void getCardMsg(){
        // 下面是开锁卡值得输入输出流,根据输入输出流就可以获取卡的信息了
//        mFd = open(device.getAbsolutePath(), baudrate, flags);
//        if (mFd == null) {
//
//            PrintWriter writer;
//            try {
//                writer = new PrintWriter(SeriaUtils.errLog3);
//                writer.write("串口初始化失败2");
//                writer.close();
//            } catch (FileNotFoundException e1) {
//                e1.printStackTrace();
//            }
//            throw new IOException();
//        }
//        mFileInputStream = new FileInputStream(mFd);
//        mFileOutputStream = new FileOutputStream(mFd);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.covert_left_dao:
               finish();
                break;
            case R.id.btn_call:
                String phone = call_one.getText().toString();
                if(phone.length()==11) {
                    mCurrentCallId = ECDevice.getECVoIPCallManager().makeCall(ECVoIPCallManager.CallType.DIRECT,phone);
                }else{
                    Toast.makeText(this,"手机号码不正确",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_stop:
                if(!mCurrentCallId.equals("")){
                    ECDevice.getECVoIPCallManager().releaseCall(mCurrentCallId);
                    Toast.makeText(this, "已停止", Toast.LENGTH_LONG).show();
                    finish();

                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeVoid();
    }

}
