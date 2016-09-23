package fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import build.social.com.social.ButlerActivity;
import build.social.com.social.DoorsActivity;
import build.social.com.social.PhoneActivity;
import build.social.com.social.R;
import build.social.com.social.SoundActivity;
import build.social.com.social.VedioActivity;

public class HomeFragment extends Fragment  implements View.OnClickListener {

    private LinearLayout lay_vedio,lay_phone,lay_voild;
    private TextView txt_common;
    private Button btn_admin,btn_open;
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        lay_vedio=(LinearLayout)view.findViewById(R.id.lay_vedio);
        lay_phone=(LinearLayout)view.findViewById(R.id.lay_phone);
        lay_voild=(LinearLayout)view.findViewById(R.id.lay_voild);
//        txt_common=(TextView)view.findViewById(R.id.txt_common);
        btn_admin=(Button)view.findViewById(R.id.btn_admin);
        btn_open=(Button)view.findViewById(R.id.btn_open);
        lay_vedio.setOnClickListener(this);
        lay_phone.setOnClickListener(this);
        lay_voild.setOnClickListener(this);
//        txt_common.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
        btn_open.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_voild:
                Intent sound_intent=new Intent(getActivity(),SoundActivity.class);
                startActivity(sound_intent);
                break;
            case R.id.lay_vedio:
                Intent vedio_intent=new Intent(getActivity(), VedioActivity.class);
                startActivity(vedio_intent);
                break;
            case R.id.lay_phone:
                Intent phone_intent=new Intent(getActivity(), PhoneActivity.class);
                startActivity(phone_intent);
                break;
//            case R.id.txt_common:
////                Intent phone_intent=new Intent(getActivity(), VedioActivity.class);
////                startActivity(phone_intent);
//                break;

            case R.id.btn_admin:
                Intent admin_intent=new Intent(getActivity(), ButlerActivity.class);
                startActivity(admin_intent);
                break;
            case R.id.btn_open:
                Intent door_intent=new Intent(getActivity(), DoorsActivity.class);
                startActivity(door_intent);
                break;
        }
    }

}
