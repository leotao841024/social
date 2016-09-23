package build.social.com.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.ButlerAdapter;
import adapter.DoorAdapter;
import bean.Door;
import bean.Order;

public class DoorsActivity extends Activity implements View.OnClickListener {
    private  GridView gvw_doors;
    private List<Door> mlist=null;
    private LinearLayout covert_left_dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doors);
        initView();
        initData();
    }

    void initView(){
        gvw_doors=(GridView)findViewById(R.id.gvw_doors);
        covert_left_dao=(LinearLayout)findViewById(R.id. covert_left_dao);
        covert_left_dao.setOnClickListener(this);
    }
    void initData(){
        mlist=new ArrayList<>();
        for(int i=0;i<10;i++){
            Door door=new Door();
            door.setId(i);
            door.setName("小区西门"+i);
            mlist.add(door);
        }
        DoorAdapter adapter=new DoorAdapter(this);
        adapter.setDoors(mlist);
        gvw_doors.setAdapter(adapter);
        gvw_doors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(DoorsActivity.this,DoorNavActivity.class);
                intent.putExtra("title",mlist.get(position).getName());
                startActivity(intent);
            }
        });
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
