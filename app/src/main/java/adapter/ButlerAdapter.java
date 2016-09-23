package adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bean.Order;
import build.social.com.social.R;

/**
 * Created by Jam on 2016/8/4.
 */
public class ButlerAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    public ButlerAdapter(Context context){
        inflater= LayoutInflater.from(context);
    }
    private List<Order> mlist;
    public void setOrders(List<Order> mlist){
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
            convertView=inflater.inflate(R.layout.content_door_list,null);
            view.avatar=(ImageView)convertView.findViewById(R.id.avatar);
            view.txt_intra=(TextView)convertView.findViewById(R.id.txt_intra);
            view.txt_price=(TextView)convertView.findViewById(R.id.txt_price);
            view.txt_comment=(TextView)convertView.findViewById(R.id.txt_comment);
            convertView.setTag(view);
        }else{
            view=(ViewHolder)convertView.getTag();
        }
        view.txt_comment.setText(mlist.get(position).getOpinion()+"");
        view.txt_intra.setText(mlist.get(position).getTitle());
        view.txt_price.setText(mlist.get(position).getPrice()+"");
        return convertView;
    }
    class ViewHolder
    {
        TextView txt_intra,txt_price,txt_comment;
        ImageView avatar;
    }
}
