package build.social.com.social;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import adapter.ButlerAdapter;
import bean.Order;

public class ButlerActivity extends Activity implements View.OnClickListener {
    private List<Order> mlist=null;
    private ListView lst_butlers=null;
    private LinearLayout covert_left_dao=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_butler);
        initView();
        initData();
    }
    void initView(){
        lst_butlers=(ListView)findViewById(R.id. lst_butlers);
        covert_left_dao=(LinearLayout)findViewById(R.id. covert_left_dao);
        covert_left_dao.setOnClickListener(this);
    }
    void initData(){
        mlist=new ArrayList<>();
        for(int i=0;i<10;i++){
            Order order=new Order();
            order.setId(i);
            order.setImgnm("");
            order.setOpinion(i);
            order.setTitle("介绍" + (i + 1));
            order.setPrice(i);
            mlist.add(order);
        }
        ButlerAdapter adapter=new ButlerAdapter(this);
        adapter.setOrders(mlist);
        lst_butlers.setAdapter(adapter);
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
