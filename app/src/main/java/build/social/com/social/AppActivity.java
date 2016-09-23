package build.social.com.social;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.VideoRatio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bean.YDevice;
import cons.Cons;
import helper.BaseAsyncTask;
import helper.SPHelper;
import karics.library.zxing.android.CaptureActivity;
import service.RemindService;
import service.SoundService;

public class AppActivity extends Activity implements OnClickListener {
	private Button bt_login;
	private EditText et_mid, et_pwd;
	private static final int REQUEST_CODE_SCAN = 0x0000;
	private  boolean isvalid=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_app);
		init();
		autoLogin();
	}

	void autoLogin(){
		String mid = SPHelper.getBaseMsg(AppActivity.this,"mid","");
		String pwd = SPHelper.getBaseMsg(AppActivity.this,"pwd","");
		if(!mid.equals("")&&!pwd.equals("")){
			login();
		}
	}
	
	@SuppressLint({ "InflateParams", "ResourceAsColor" })
	private void init() {
		et_mid = (EditText) findViewById(R.id.et_mid);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_login:
//			final String uid = et_mid.getText().toString();
//			final String pwd = et_pwd.getText().toString();
//			if (uid.equals("") || pwd.equals("")) {
//				Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
//				return;
//			}
//			logion(uid,pwd);
			loadMembers();
			break;
		default:
			break;
		}
	}

	void loadMembers(){
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("userName","18910715571");
		maps.put("pwd","88888888");
		new BaseAsyncTask(Cons.GET_LINK_MEMS,maps, BaseAsyncTask.HttpType.Get,"",AppActivity.this){
			@Override
			public void handler(String param) throws RuntimeException {
				if(param!=null&&param.contains("status")){
					try {
						JSONObject json=new JSONObject(param);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}.execute("");
	}

	Handler hanlder=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.arg1==0){
				login();
			}else{
				Toast.makeText(AppActivity.this, "失败", Toast.LENGTH_LONG).show();
			}
		}
	};
	void login(){
		Intent intent=new Intent(AppActivity.this,MainActivity.class);
		startActivity(intent);
		finish();
	}
	void  logion(final String name,final String pwd){
		final Message msg=new Message();
		EMClient.getInstance().login(name, pwd, new EMCallBack() {//回调
			@Override
			public void onSuccess() {
				SPHelper.setBaseMsg(AppActivity.this,"mid",name);
				SPHelper.setBaseMsg(AppActivity.this,"pwd",pwd);
				msg.arg1=0;
				hanlder.sendMessage(msg);
			}
			@Override
			public void onProgress(int progress, String status) {

			}

			@Override
			public void onError(int code, String message) {
				msg.arg1=1;
				hanlder.sendMessage(msg);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
