package build.social.com.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoorNavActivity extends Activity implements View.OnClickListener{
    private TextView txt_pwd,txt_press,txt_title,txt_shandow;
    private LinearLayout covert_left_dao;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_door_nav);
        initView();
        initData();
    }
    void initView(){
        txt_pwd=(TextView)findViewById(R.id.txt_pwd);
        txt_press=(TextView)findViewById(R.id.txt_press);
        txt_title=(TextView)findViewById(R.id.txt_title);
        txt_shandow=(TextView)findViewById(R.id.txt_shandow);
        txt_pwd.setOnClickListener(this);
        txt_press.setOnClickListener(this);
        txt_shandow.setOnClickListener(this);
        covert_left_dao=(LinearLayout)findViewById(R.id. covert_left_dao);
        covert_left_dao.setOnClickListener(this);
    }
    void initData(){
        title= getIntent().getStringExtra("title");
        if(title!=null){
            txt_title.setText(title);
        }
    }
    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()){
            case R.id.txt_pwd:
                intent=new Intent(DoorNavActivity.this,PwdOpenActivity.class);
                break;
            case R.id.txt_press:
                intent=new Intent(DoorNavActivity.this,FastOpenActivity.class);
                break;
            case R.id.covert_left_dao:
                finish();
                break;
            case R.id.txt_shandow:
                intent=new Intent(DoorNavActivity.this,ShandouActivity.class);
                break;
        }
        if(intent!=null){
            intent.putExtra("title",title);
            startActivity(intent);
        }
    }
}
