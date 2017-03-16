package gkzxhn.wqalliance.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gkzxhn.utils.SPUtil;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.SharedPreferenceConstants;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.Result;
import gkzxhn.wqalliance.mvp.model.entities.VersionBean;
import gkzxhn.wqalliance.mvp.widget.UpdateDialog;

/**
 * Created by 方 on 2017/3/9.
 */

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    private final String TAG = "SplashActivity";
    private UpdateDialog updateDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        String phone = (String) SPUtil.get(SplashActivity.this, SharedPreferenceConstants.PHONE, "");
        int userId = (int) SPUtil.get(SplashActivity.this, SharedPreferenceConstants.USERID, 0);
        if (!TextUtils.isEmpty(phone) && userId != 0) {
            UiUtils.makeText("自动登录...");
            ApiWrap.getUser(userId, new SimpleObserver<Result>(){
                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UiUtils.makeText("网络异常,自动登录失败...");
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    }).start();
                }

                @Override
                public void onNext(Result result) {
                    //表示可以成功连上服务器
                    super.onNext(result);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            });
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }).start();
        }
    }

    private void checkVersion() {
        ApiWrap.versionUpdate(new SimpleObserver<VersionBean>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "onError: checkVersion  : " + e.getMessage());
            }

            @Override
            public void onNext(VersionBean versionBean) {
                super.onNext(versionBean);
                if (versionBean.code == 0) {
                    int newVersion = versionBean.data.versionNo;
                    PackageManager pm = getPackageManager();
                    PackageInfo packageInfo = null;
                    try {
                        packageInfo = pm.getPackageInfo(getPackageName(),
                                PackageManager.GET_CONFIGURATIONS);
                        int currentVersion = packageInfo.versionCode;
                        if (newVersion > currentVersion) {
                            String versionName = versionBean.data.versionName;
                            String downloadUrl = versionBean.data.downloadUrl;
                            boolean isForceUpdate = true;
                            updateDialog = new UpdateDialog(SplashActivity.this);

                            updateDialog.setForceUpdate(isForceUpdate);
                            updateDialog.setDownloadInfor(versionName, downloadUrl);
                            updateDialog.setOnDownloadListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (updateDialog != null && updateDialog.isShowing())
                                        updateDialog.dismiss();

                                }
                            });
                            updateDialog.show();
                        } else {
                            Log.i(TAG, "onNext: versionUpdate....已是最新版本.." + versionBean.data.versionName);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    UiUtils.makeText(versionBean.msg);
                }
            }
        });
    }

    @OnClick(R.id.iv_splash)
    public void onClick() {

    }

//    public void update(JSONObject json) {
//        int newVersion = ConvertUtil.strToInt(JSONUtil.getJSONObjectStringValue(json, "version"));
//        PackageManager pm = getPackageManager();
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = pm.getPackageInfo(getPackageName(),
//                    PackageManager.GET_CONFIGURATIONS);
//            int currentVersion=packageInfo.versionCode;
//            if (newVersion > currentVersion) {
//                String versionName = JSONUtil.getJSONObjectStringValue(json, "versionName");
//                String downloadUrl = JSONUtil.getJSONObjectStringValue(json, "downloadUrl");
//                boolean isForceUpdate = true;
//                up
//                updateDialog.setForceUpdate(isForceUpdate);
//                updateDialog.setDownloadInfor(versionName,downloadUrl);
//                updateDialog.setOnDownloadListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(updateDialog!=null&&updateDialog.isShowing())updateDialog.dismiss();
//
//                    }
//                });
//                updateDialog.show();
//            }else{
//                tvUpdateHint.setText(R.string.has_last_version);
//            }
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

}
