package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.Door;
import bean.Order;
import build.social.com.social.R;

/**
 * Created by Jam on 2016/8/7.
 */
public class DoorAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    public DoorAdapter(Context context){
        inflater= LayoutInflater.from(context);
    }
    private List<Door> mlist;
    public void setDoors(List<Door> mlist){
        this.mlist=mlist;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view=new ViewHolder();
        if(convertView==null){
            convertView=inflater.inflate(R.layout.door_item,null);
            view.txt_content=(TextView)convertView.findViewById(R.id.txt_content);
            convertView.setTag(view);
        }else{
            view=(ViewHolder)convertView.getTag();
        }
        view.txt_content.setText(mlist.get(position).getName());
        return convertView;
    }
    class ViewHolder
    {
        TextView txt_content;
    }
}
