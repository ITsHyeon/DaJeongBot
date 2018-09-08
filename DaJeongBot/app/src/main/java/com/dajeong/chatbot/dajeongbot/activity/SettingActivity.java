package com.dajeong.chatbot.dajeongbot.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dajeong.chatbot.dajeongbot.control.CustomSharedPreference;
import com.dajeong.chatbot.dajeongbot.R;
import com.dajeong.chatbot.dajeongbot.model.request.RequestRegisterToken;
import com.dajeong.chatbot.dajeongbot.network.NetRetrofit;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// setting activity ( 사용자 정보 수정 / 챗봇 선택 / 데이터 초기화 .. )
public class SettingActivity extends AppCompatActivity {
    private final String TAG = "SettingActivity";
    // TODO : 로그아웃시 로그인 정보 삭제
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });




        findViewById(R.id.LiLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : test
                /*
                 *
                 * 서버에 저장된 fcm 토큰을 삭제합니다.
                 * */
                final String accountId = CustomSharedPreference.getInstance(getApplicationContext(), "user_info").getStringPreferences("id");
                final String token = CustomSharedPreference.getInstance(getApplicationContext(), "user_info").getStringPreferences("fcm_token");
                if(accountId!=null){
                    Call<JsonObject> res = NetRetrofit.getInstance(getApplicationContext()).getService().releaseFcmToken(Integer.parseInt(accountId), token);
                    res.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if(response.body().has("status")){
                                if(response.body().get("status").getAsString().equals("Success")){
                                    Log.i(TAG, "토큰 해제 성공하였습니다."+ accountId);
                                    releaseUserInfo();
                                }else{
                                    Log.e(TAG, "서버의 문제로 토큰 등록에 실패하였습니다.");
                                    Toast.makeText(getApplicationContext(), "네트워크에 문제가 발생하여 로그아웃에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            if(t!=null){
                                Toast.makeText(getApplicationContext(), "네트워크에 문제가 발생하여 로그아웃에 실패하였습니다.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }


    private void releaseUserInfo(){
        CustomSharedPreference.getInstance(getApplicationContext(), "user_info").removeAllPreferences();
        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.

        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewActionBar = inflater.inflate(R.layout.custom_back_actionbar, null);

        actionBar.setCustomView(viewActionBar);

        // 액션바 배경 색 바꾸기
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        TextView mCurrentScreen = (TextView) findViewById(R.id.tvCurrentScreen);
        mCurrentScreen.setText("환경설정");
        // 이벤트 달기
        viewActionBar.findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, com.dajeong.chatbot.dajeongbot.activity.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar)viewActionBar.getParent();
        parent.setContentInsetsAbsolute(0,0);

        return true;
    }
}