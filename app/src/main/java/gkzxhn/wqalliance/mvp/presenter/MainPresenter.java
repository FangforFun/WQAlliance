package gkzxhn.wqalliance.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Inject;

import gkzxhn.wqalliance.R;
import gkzxhn.wqalliance.mvp.contract.MainContract;
import gkzxhn.wqalliance.mvp.model.api.ApiWrap;
import gkzxhn.wqalliance.mvp.model.api.service.SimpleObserver;
import gkzxhn.wqalliance.mvp.model.entities.ScanningInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


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

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 扫码信息获取商品信息
     * @param result
     */
    public void getGoodsInfo(final String result) {
        mRootView.showLoading();
        ApiWrap.getGoodsByCode(result, new SimpleObserver<ScanningInfo>(){
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.i(TAG, "onError: getgoodsinfo ==== " + e.getMessage());
                mRootView.showMessage(UiUtils.getString(R.string.server_exeption));
                mRootView.showSaomaResultFragment(null);
            }

            @Override
            public void onNext(ScanningInfo scanningInfo) {
                super.onNext(scanningInfo);
                scanningInfo.scanningCode = result;
                mRootView.showSaomaResultFragment(scanningInfo);
                mRootView.hideLoading();
            }
        });
    }
}
