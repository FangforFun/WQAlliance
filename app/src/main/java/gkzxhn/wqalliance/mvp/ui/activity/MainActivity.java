package gkzxhn.wqalliance.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import common.AppComponent;
import common.SuperActivity;
import common.SuperApplication;
import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.di.component.DaggerMainComponent;
import gkzxhn.wqalliance.di.module.MainModule;
import gkzxhn.wqalliance.mvp.contract.MainContract;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;
import gkzxhn.wqalliance.mvp.model.entities.VersionBean;
import gkzxhn.wqalliance.mvp.presenter.MainPresenter;
import gkzxhn.wqalliance.mvp.ui.fragment.HomeFragment;
import gkzxhn.wqalliance.mvp.ui.fragment.MessageFragment;
import gkzxhn.wqalliance.mvp.ui.fragment.MineFragment;
import gkzxhn.wqalliance.mvp.ui.fragment.ProtectionFragment;
import gkzxhn.wqalliance.mvp.ui.fragment.SaoMaFragment;
import gkzxhn.wqalliance.mvp.widget.UpdateDialog;
import gkzxhn.wqalliance.zxing.CaptureActivity;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by 方 on 2017/3/3.
 */

public class MainActivity extends SuperActivity<MainPresenter> implements MainContract.View {

    private static final int SCANNING_REQUEST_CODE = 1;
    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    private List<Fragment> mFragments;
    private long mExitTime = 0;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this)) //请将MainModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }

    @Override
    protected void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(ProtectionFragment.newInstance());
        mFragments.add(new SaoMaFragment());
        mFragments.add(new MessageFragment());
        mFragments.add(new MineFragment());
        checkVersion();
        setListener();
    }

    private void setListener() {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            mMainBottomeSwitcherContainer.getChildAt(i).setOnClickListener(onClickListener);
        }

        onClickListener.onClick(mMainBottomeSwitcherContainer.getChildAt(0));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = mMainBottomeSwitcherContainer.indexOfChild(view);
            if (2 == index) {
                //第三个为扫描二维码
                //检查权限
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                }else {
                    startSaoma();
                }
                return;
            }
            changeUi(index);
            changeFragment(index);
        }
    };

    /**
     * 开始扫码
     */
    private void startSaoma() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNING_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                Log.i(TAG, "onRequestPermissionsResult: camera--------");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //执行后续的操作
                    Toast.makeText(this, "相机已经授权成功了", Toast.LENGTH_SHORT).show();
                    // TODO: 2016/11/4
                    startSaoma();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNING_REQUEST_CODE:
                if (resultCode == RESULT_OK) {

                    final Bundle bundle = data.getExtras();
                    mPresenter.getGoodsInfo(bundle.getString("result"));
                }
                break;
            default:
                break;
        }
    }

    /**
     * 改变index对应的孩子的状态为选中,其他为不选中
     *
     * @param index
     */
    private void changeUi(int index) {
        int childCount = mMainBottomeSwitcherContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = mMainBottomeSwitcherContainer.getChildAt(i);
            setEnabled(view, i != index);
        }

    }

    /**
     * 递归的方法设置子控件的属性
     *
     * @param view
     * @param enabled
     */
    private void setEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEnabled(((ViewGroup) view).getChildAt(i), enabled);
            }
        }
    }

    /**
     * 切换fragment
     * @param index
     */
    private void changeFragment(int index) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, mFragments.get(index))
                .commit();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000){
            UiUtils.makeText(getString(R.string.press_again));
            mExitTime = System.currentTimeMillis();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragments = null;
        SuperApplication.getOrderEvidences().clear();
    }

    /**
     * 升级对话框
     */
    private UpdateDialog updateDialog;

    /**
     * 检查版本
     */
    private void checkVersion() {
        ApiWrap.versionUpdate(new SimpleObserver<VersionBean>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "onError: checkVersion  : " + e.getMessage());
            }

            @Override
            public void onNext(VersionBean versionBean) {
                super.onNext(versionBean);
                Log.i(TAG, "onNext: versionBean      " + versionBean);
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
                            boolean isForceUpdate = versionBean.data.forceFlag == 0 ? false : true;
                            updateDialog = new UpdateDialog(MainActivity.this);

                            updateDialog.setForceUpdate(isForceUpdate);
                            updateDialog.setDownloadInfor(versionName,downloadUrl);
                            updateDialog.setOnDownloadListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(updateDialog !=null&& updateDialog.isShowing()) updateDialog.dismiss();

                                }
                            });
                            updateDialog.show();
                        }else{
                            Log.i(TAG, "onNext: versionUpdate....已是最新版本.." + versionBean.data.versionName);
                        }
                    }catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    UiUtils.makeText(versionBean.msg);
                }
            }
        });
    }

    @Override
    public void showSaomaResultFragment(ScanningInfo scanningInfo) {
        changeUi(2);
        ((SaoMaFragment)mFragments.get(2)).setResult(scanningInfo);
        changeFragment(2);
    }
}
