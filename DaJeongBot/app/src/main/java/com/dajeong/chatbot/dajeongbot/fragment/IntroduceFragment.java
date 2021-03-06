package com.dajeong.chatbot.dajeongbot.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dajeong.chatbot.dajeongbot.activity.SignupActivity;
import com.dajeong.chatbot.dajeongbot.R;

/**
 * Created by s2017 on 2018-08-03.
 */

public class IntroduceFragment extends Fragment {
    private Button btn_introduce_previous, btn_introduce_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.view_introduce_bot, container, false);
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.view_introduce_bot, container, false);

        btn_introduce_previous = rootView.findViewById(R.id.btn_introduce_previous);
        btn_introduce_next = rootView.findViewById(R.id.btn_introduce_next);


        btn_introduce_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupActivity)getActivity()).setCurrentItem(((SignupActivity)getActivity()).getCurrentItem()-1,true);
                new InputNameFragment();
            }
        });

        btn_introduce_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SignupActivity)getActivity()).setCurrentItem(((SignupActivity)getActivity()).getCurrentItem()+1,true);
                new SelectCharacterFragment();
            }
        });

        return rootView;
    }

//    private class ButtonEvent implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btn_introduce_previous:
//                    ((SignupActivity) getActivity()).setCurrentItem(0, true);
//                    new InputNameFragment();
//                case R.id.btn_introduce_next:
//                    ((SignupActivity) getActivity()).setCurrentItem(1, true);
//                    new SelectCharacterFragment();
//            }
//        }
//
//    }
}
