package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gkzxhn.wqalliance.R;

/**
 * Created by 方 on 2017/3/9.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(1500);
//                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                finish();
//            }
//        }).start();
    }

    @OnClick(R.id.iv_splash)
    public void onClick() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();
    }
}
