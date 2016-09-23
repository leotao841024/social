package build.social.com.social;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FastOpenActivity extends Activity  implements View.OnClickListener{
    private TextView txt_doortitle;
    private LinearLayout covert_left_dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_fast_open);
        initView();
        initData();
    }
    void initView(){
        txt_doortitle=(TextView)findViewById(R.id. txt_doortitle);
        covert_left_dao=(LinearLayout)findViewById(R.id. covert_left_dao);
        covert_left_dao.setOnClickListener(this);
    }
    void initData(){
        String title= getIntent().getStringExtra("title");
        if(title!=null){
            txt_doortitle.setText(title);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.covert_left_dao:
                finish();
                break;
        }
    }
}
