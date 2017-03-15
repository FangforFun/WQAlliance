package gkzxhn.wqalliance.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netease.nim.uikit.NimUIKit;

import butterknife.ButterKnife;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/3.
 */
public class MessageFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

//    @OnClick(R.id.tv_title)
//    public void onclick(View view){
//        NimUIKit.startP2PSession(getActivity(), "gkzxhn002");
//    }
}
